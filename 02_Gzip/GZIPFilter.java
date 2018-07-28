package webj2ee;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class GenericResponseWrapper extends HttpServletResponseWrapper {
	private CharArrayWriter charArrayWriter = new CharArrayWriter();

	public GenericResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(charArrayWriter);
	}

	public byte[] getData() throws UnsupportedEncodingException {
		return charArrayWriter.toString().getBytes("UTF-8");
	}
}

@WebFilter(filterName = "GZIPFilter", urlPatterns = { "*.jsp" })
public class GZIPFilter implements Filter {

	public static final String ACCEPT_ENCODING = "Accept-Encoding";

	public static final String CONTENT_ENCODING = "Content-Encoding";

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void init(FilterConfig val) {
	}

	@Override
	public void destroy() {
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	private byte[] compressData(byte[] data) throws IOException {
		if (data.length == 0) {
			return data;
		}

		ByteArrayOutputStream compressed = new ByteArrayOutputStream();
		GZIPOutputStream gzout = new GZIPOutputStream(compressed);
		gzout.write(data);
		gzout.flush();
		gzout.close();
		return compressed.toByteArray();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		GenericResponseWrapper wrapper = new GenericResponseWrapper(response);

		chain.doFilter(request, wrapper);

		byte[] originalData = wrapper.getData();
		byte[] compressedData = compressData(originalData);

		// 打印 gzip 压缩统计信息
		StringBuffer stats = new StringBuffer("" + originalData.length);
		stats.append(" / " + compressedData.length);
		double doubleRatio = (double) (originalData.length - compressedData.length) / (double) originalData.length;
		int ratio = (int) (doubleRatio * 100);
		stats.append(" / " + ratio + "%");
		System.out.println("GZIPFilter: Original / GZip / Saved: " + stats);

		// 反馈响应
		response.setHeader(CONTENT_ENCODING, "gzip");
		response.setContentLength(compressedData.length);
		response.setCharacterEncoding("UTF-8");
		OutputStream out = response.getOutputStream();
		out.write(compressedData);
		out.flush();
	}
}
