package can.tools.dbc2xml;

import java.util.ArrayList;

public class Nodes {
	String name; //节点名称
	/* 一个节点可以有多条报文 */
	ArrayList<Messages> messages; //节点消息
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Messages> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Messages> messages) {
		this.messages = messages;
	}
	
	public Nodes(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Nodes [name=" + name + ", messages=" + messages + "]";
	}
}
