package can.tools.dbc2xml;

public class Nodes {
	String name; //节点名称
	int id; //节点ID号
	Messages messages; //节点消息
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Messages getMessages() {
		return messages;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public Nodes(String name) {
		super();
		this.name = name;
	}
	
}
