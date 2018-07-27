package webj2ee;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Wraps the HttpServletResponseWrapper
 */
public class GenericResponseWrapper extends HttpServletResponseWrapper {
	/** The ByteArrayOutputStream to use for output */
	private ByteArrayOutputStream output;

	/** The length of the content to write */
	private int contentLength;

	/** The type of the content to write */
	private String contentType;

	/**
	 * Wrap a normal servlet response so that we can transform the data before it
	 * gets sent to the underlying stream
	 *
	 * @param response the normal servlet response to wrap
	 */
	public GenericResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new ByteArrayOutputStream();
	}

	/**
	 * Get the data from the temporary storage stream
	 *
	 * @return byte[] with the data in it
	 */
	public byte[] getData() {
		return output.toByteArray();
	}

	/**
	 * Get the output stream for this response
	 *
	 * @return a FilterServletOutputStream wrapping the temporary data
	 */
	public ServletOutputStream getOutputStream() {
		return new FilterServletOutputStream(output);
	}

	/**
	 * Set the content length for this data
	 *
	 * @param val the length to set
	 */
	public void setContentLength(int val) {
		contentLength = val;
		super.setContentLength(val);
	}

	/**
	 * Get the content length
	 *
	 * @return int representing the current content length
	 */
	public int getContentLength() {
		return contentLength;
	}

	/**
	 * Set the content type
	 *
	 * @param type the content type to set
	 */
	public void setContentType(String type) {
		contentType = type;
		super.setContentType(type);
	}

	/**
	 * Get the content type
	 *
	 * @return String with the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Get a Writer version of the OutputStream
	 *
	 * @return PrintWriter wrapping a FilterServletOutputStream
	 */
	public PrintWriter getWriter() {
		return new PrintWriter(getOutputStream(), true);
	}
}
