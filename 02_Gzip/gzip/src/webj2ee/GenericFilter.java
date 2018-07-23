/* Copyright 2005 Tacit Knowledge LLC
 * 
 * Licensed under the Tacit Knowledge Open License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.tacitknowledge.com/licenses-1.0.
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package webj2ee;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A filter that all our filters can extend. Provides some utility methods, as
 * well as default implementations of the Filter interface
 * 
 * @author Mike Hardy <mhardy@tacitknowledge.com/>
 * @version $Id: GenericFilter.java,v 1.14 2005/03/12 01:24:51 mike Exp $
 */
public abstract class GenericFilter implements Filter {
	/** The name of the config file the filters use */
	private static final String CONFIG_FILE = "tk-filters.properties";

	/** The Properties object holding our configuration */
	private Properties config = new Properties();

	/** The filter configuration provided by the container */
	private FilterConfig filterConfig;

	/**
	 * Creates a new <code>GenericFilter</code>.
	 */
	public GenericFilter() {
	}

	/** {@inheritDoc} */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		doFilterInternal((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	/**
	 * Performs the actual filtering actions
	 *
	 * @param request  the request to work with
	 * @param response the response to work with
	 * @param chain    the filter chain to work with
	 * @exception IOException      if there is a problem performing IO
	 * @exception ServletException general servlet exceptions
	 */
	public abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException;

	/**
	 * Print a configuration banner including your CVS Version, as well as any
	 * configuration information you have, to the given context's log
	 * 
	 * @param context ServletContext object to use for logging
	 */
	public abstract void printBanner(ServletContext context);

	/**
	 * Returns the <code>FilterConfig</code> for this filter's web.xml registration.
	 * 
	 * @return the <code>FilterConfig</code> for this filter's web.xml registration
	 */
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	/**
	 * Sets the <code>FilterConfig</code> for this filter's web.xml registration.
	 * 
	 * @param val the <code>FilterConfig</code> for this filter's web.xml
	 *            registration. This is required by WebLogic 6.1's filter
	 *            implementation.
	 */
	public void setFilterConfig(FilterConfig val) {
		init(val);
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(FilterConfig val) {
		filterConfig = val;
		printBanner(val.getServletContext());
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		filterConfig = null;
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
	protected boolean headerContains(HttpServletRequest request, String header, String value) {
		Enumeration accepted = request.getHeaders(header);
		while (accepted.hasMoreElements()) {
			String headerValue = (String) accepted.nextElement();
			if (headerValue.indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}
}
