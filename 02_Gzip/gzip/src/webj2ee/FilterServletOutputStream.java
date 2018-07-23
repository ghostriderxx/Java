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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * A servlet output stream implementation that can be used to store output
 * 
 * @author Mike Hardy <mhardy@tacitknowledge.com/>
 * @version $Id: FilterServletOutputStream.java,v 1.6 2005/03/10 01:41:31 mike
 *          Exp $
 */
public class FilterServletOutputStream extends ServletOutputStream {
	/** The DataOutputStream for the servlet */
	private DataOutputStream stream;

	/**
	 * Wraps the given OutputStream in a DataOutputStream
	 *
	 * @param output the OutputStream for the servlet
	 */
	public FilterServletOutputStream(OutputStream output) {
		stream = new DataOutputStream(output);
	}

	/**
	 * Write the given int into the stream
	 *
	 * @param b the int to write
	 * @exception IOException if the underlying stream write fails
	 */
	public void write(int b) throws IOException {
		stream.write(b);
	}

	/**
	 * Write the given byte array to the stream
	 *
	 * @param b the byte array to write
	 * @exception IOException if the underlying stream write fails
	 */
	public void write(byte[] b) throws IOException {
		stream.write(b);
	}

	/**
	 * Write the bytes in the given array, delimited by the given offset and length,
	 * to the stream
	 *
	 * @param b   the byte array to write from
	 * @param off the offset to begin writing from
	 * @param len the number of bytes to write
	 * @exception IOException if the underlying stream write fails
	 */
	public void write(byte[] b, int off, int len) throws IOException {
		stream.write(b, off, len);
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub

	}
}
