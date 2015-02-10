package com.c17.yyh.db.entities.adventure;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="start_bonus")
public class XmlStartBonus {
	private int tool_id;
	private int count;
	public int getTool_id() {
		return tool_id;
	}
	public void setTool_id(int tool_id) {
		this.tool_id = tool_id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
