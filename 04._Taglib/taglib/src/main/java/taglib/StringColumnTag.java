package taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class StringColumnTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String dataKey;
	private String head;
	
	public StringColumnTag(){
		dataKey = null;
		head = null;
	}

	@Override
	public int doEndTag() throws JspException {
		// ��¼ StringColumn ��ǩ���Ե� StringColumnBean
		StringColumn column = new StringColumn();
		column.setDataKey(dataKey);
		column.setHead(head);
		
		// ֪ͨ RookieTable��������һ���µ� Column
		((RookieTableTag)getParent()).addColumn(column);
		
		// ���� StringColumn ��ǩ״̬��Ϊ��һ�α�ǩ������׼��
		release();
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		dataKey = null;
		head = null;
	}

	/**
	 * Tag Attributes Getters/Setters
	 */
	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
}
