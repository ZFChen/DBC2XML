package can.tools.dbc2xml;

import java.util.ArrayList;

public class Messages {
	/* 一条报文(message)中包含多个信号 */
	ArrayList<Signal> signals;
    String name;
    int length;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Signal> getSignals() {
		return signals;
	}

	public void setSignals(ArrayList<Signal> signals) {
		this.signals = signals;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Messages(String name) {
		super();
		this.name = name;
	}

	public Messages(String name, int length) {
		super();
		this.name = name;
		this.length = length;
	}
	
}
