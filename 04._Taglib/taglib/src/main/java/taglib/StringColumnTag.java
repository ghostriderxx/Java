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
		// 记录 StringColumn 标签属性到 StringColumnBean
		StringColumn column = new StringColumn();
		column.setDataKey(dataKey);
		column.setHead(head);
		
		// 通知 RookieTable，出现了一个新的 Column
		((RookieTableTag)getParent()).addColumn(column);
		
		// 重置 StringColumn 标签状态，为下一次标签解析做准备
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
