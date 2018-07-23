package webj2ee;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "GZIPFilter", urlPatterns = { "*.jsp" }, initParams = {
		@WebInitParam(name = "Enabled", value = "true"), @WebInitParam(name = "LogStats", value = "true") })
public class GZIPFilter implements Filter {
	
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	
	public static final String CONTENT_ENCODING = "Content-Encoding";
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig val) {
		filterConfig = val;
		printBanner();
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void printBanner() {
		System.out.println("===========================================================");
		System.out.println("===============       GZIPFilter       ====================");
		System.out.println("== GZIPFilter.Enabled: " + isEnabled());
		System.out.println("== GZIPFilter.LogStats: " + isLogStats());
		System.out.println("===========================================================");
	}

	private boolean isEnabled() {
		return filterConfig.getInitParameter("Enabled").equals("true");
	}

	private boolean isLogStats() {
		return filterConfig.getInitParameter("LogStats").equals("true");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Filters the response by wrapping the output stream, by checking to see if the
	 * browser will accept gzip encodings, and if they can, temporarily storing all
	 * of the response data and gzipping it before finally sending it to the client
	 *
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (isEnabled()) {
			// Make sure that proxies know that content will be returned
			// differently based on the Accept-Encoding request header; this
			// prevents them from caching the gzip'd response and returning it
			// to non-gzip-aware clients
			response.addHeader("Vary", ACCEPT_ENCODING);

			// Create a response wrapper that captures servlet output
			// instead of sending it to the client
			GenericResponseWrapper wrapper = new GenericResponseWrapper(response);

			// Let the server perform any work that it must perform,
			// then get the output back
			chain.doFilter(request, wrapper);
			OutputStream out = response.getOutputStream();

			// If the content isn't cached, its not an internal forward, and the
			// browser accepts gzip, compress the thing and send it out
			if (!isCached(wrapper) && !isIncluded(request) && acceptsGzip(request)) {
				byte[] compressedData = compressData(wrapper.getData());
				response.setHeader(CONTENT_ENCODING, "gzip");
				response.setContentLength(compressedData.length);
				out.write(compressedData);
			} else {
				// we can't compress this data, just write it straight through
				out.write(wrapper.getData());
			}

			// make sure all the data made it to the client
			out.flush();
			out.close();
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * Utility method to examine the given request, find the given header and see if
	 * the header contains the provided value
	 * 
	 * @param request the request to examine
	 * @param header  the specific header to check the value on
	 * @param value   the value to look for in the header
	 * @return boolean true if the header contains the given value
	 */
	private boolean headerContains(HttpServletRequest request, String header, String value) {
		Enumeration accepted = request.getHeaders(header);
		while (accepted.hasMoreElements()) {
			String headerValue = (String) accepted.nextElement();
			if (headerValue.indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}



	/**
	 * Takes a byte array, compresses it, and writes it to the given output stream
	 *
	 * @param data the data to compress
	 * @return byte[] containing the compressed data
	 * @exception IOException if there was a problem writing to the stream
	 */
	protected byte[] compressData(byte[] data) throws IOException {
		// do the compression
		ByteArrayOutputStream compressed = new ByteArrayOutputStream();
		GZIPOutputStream gzout = new GZIPOutputStream(compressed);
		gzout.write(data);
		gzout.flush();
		gzout.close();

		// If statistics are desired, compute them and log them out
		if (isLogStats()) {
			StringBuffer stats = new StringBuffer("" + data.length);
			stats.append(" / " + compressed.size());
			stats.append(" / " + (data.length - compressed.size()));

			if (data.length > 0) {
				double doubleRatio = (double) compressed.size() / (double) data.length;
				int ratio = (int) (doubleRatio * 100);
				stats.append(" / " + ratio + "%");
			} else {
				stats.append(" / NaN");
			}

			filterConfig.getServletContext().log("GZIPFilter: Original / GZip / Saved / Ratio: " + stats);
		}

		return compressed.toByteArray();
	}

	/**
	 * Make sure that the request isn't actually an internal redirect, gzipping
	 * content as it bashed around inside the server isn't a good idea
	 * 
	 * @param request the request to check for internal referencing
	 * @return true if the request is included
	 */
	protected boolean isIncluded(ServletRequest request) {
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null) {
			return false;
		}

		return true;
	}

	/**
	 * If the request content is empty, it makes no sense to gzip
	 * 
	 * @param wrapper the response wrapper
	 * @return boolean true if the response has no data in it
	 */
	protected boolean isCached(GenericResponseWrapper wrapper) {
		if (wrapper.getData().length > 0) {
			return false;
		}

		return true;
	}

	/**
	 * Examine the request to make sure that the browser can accept gzip encodings
	 * 
	 * @param request the request to examine for gzip acceptance
	 * @return boolean true if the request can accept gzip
	 */
	protected boolean acceptsGzip(HttpServletRequest request) {
		return headerContains(request, ACCEPT_ENCODING, "gzip");
	}
}
