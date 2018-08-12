package taglib;

import java.util.ArrayList;

public class RookieTable {
	// tag attributes
	private String name;
	private int width;
	private int height;

	// ...
	private ArrayList<StringColumn> columns;
	private String domid;

	public RookieTable() {
		name = null;
		width = -1;
		height = -1;

		columns = new ArrayList<StringColumn>();
		domid = null;
	}

	public String genHTML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table class=\"rookietable\" id=\"" + domid + "\" name=\"" + name + "\">");
		sb.append("<thead>");
		sb.append("<tr>");
		for (int i = 0; i < columns.size(); i++) {
			StringColumn column = columns.get(i);
			sb.append("<th>");
			sb.append(column.getHead());
			sb.append("</th>");
		}
		sb.append("</tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		for(int i=0; i<5; i++){
			sb.append("<tr>");
			for (int j = 0; j < columns.size(); j++) {
				StringColumn column = columns.get(j);
				sb.append("<td>");
				sb.append(column.getHead()+i);
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}

	public String genJS() {
		StringBuffer sb = new StringBuffer();
		sb.append("RookieTable.set(" 
					+ "\"" + name + "\", " 
					+ "new RookieTable({" 
						+ "\"name\":\"" + name + "\","
						+ "\"width\":" + width + "," 
						+ "\"height\":" + height + "," 
						+ "\"domid\":\"" + domid + "\","
					+ "})" + ")");
		return sb.toString();
	}

	public void addColumn(StringColumn column) {
		columns.add(column);
	}

	/**
	 * Tag Attributes Getters/Setters
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

	public String getDomid() {
		return domid;
	}

	public void setDomid(String domid) {
		this.domid = domid;
	}
}
