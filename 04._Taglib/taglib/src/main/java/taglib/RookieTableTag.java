package taglib;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class RookieTableTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	private String name;
	private int width;
	private int height;
	private RookieTable rtbean;
	
	public RookieTableTag(){
		name = null;
		width = -1;
		height = -1;
		rtbean = null;
	}
	
	@Override
	public int doStartTag() throws JspException {
		rtbean = new RookieTable();
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			// �洢 RookieTable �ı�ǩ����
			rtbean.setDomid(UUID.randomUUID().toString()); // Ϊÿ�� RookieTable �� HTML �ṹ����Ψһ�� DOMID
			rtbean.setName(name);
			rtbean.setWidth(width);
			rtbean.setHeight(height);
			
			// ��� RookieTable �� HTML �ṹ
			pageContext.getOut().write(rtbean.genHTML());
			
			// ��� RookieTable �� JS ���ƴ���
			pageContext.getOut().write("<script type=\"text/javascript\">");
			pageContext.getOut().write(rtbean.genJS());
			pageContext.getOut().write("</script>");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		release();
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		name = null;
		width = -1;
		height = -1;
		rtbean = null;
	}
	
	/**
	 * �� StringColumnTag ͨ�� getParent() ���ã�
	 * ���ڼ�¼ <RookieTable> �µ� <StringColumn> ��ǩ��Ϣ
	 * 
	 */
	public void addColumn(StringColumn column){
		rtbean.addColumn(column);
	}

	/**
	 * Tag Attribute Getters/Setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
