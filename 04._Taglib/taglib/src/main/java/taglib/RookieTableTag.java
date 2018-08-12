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
			// 存储 RookieTable 的标签属性
			rtbean.setDomid(UUID.randomUUID().toString()); // 为每个 RookieTable 的 HTML 结构生成唯一的 DOMID
			rtbean.setName(name);
			rtbean.setWidth(width);
			rtbean.setHeight(height);
			
			// 输出 RookieTable 的 HTML 结构
			pageContext.getOut().write(rtbean.genHTML());
			
			// 输出 RookieTable 的 JS 控制代码
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
	 * 供 StringColumnTag 通过 getParent() 调用，
	 * 用于记录 <RookieTable> 下的 <StringColumn> 标签信息
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
