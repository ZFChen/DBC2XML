package can.tools.dbc2xml;

import java.util.HashMap;

public class Signal {
	String name; //信号名称
	int start_bit;	//起始位
	int length; //长度
	
	double factor;
	double offset;
	String regex_1 = "\\|";
	String regex_2 = "@";
	
	//使用key-value来存储信号数值与物理含义的对应关系, 信号数值作为key，物理含义作为value
	HashMap<Integer, String> val_table = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStart_bit() {
		return start_bit;
	}
	public void setStart_bit(char start_bit) {
		this.start_bit = start_bit;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public HashMap<Integer, String> getVal_table() {
		return val_table;
	}
	public void setVal_table(HashMap<Integer, String> val_table) {
		this.val_table = val_table;
	}
	public Signal(String name, char start_bit, int length,
			HashMap<Integer, String> val_table) {
		super();
		this.name = name;
		this.start_bit = start_bit;
		this.length = length;
		this.val_table = val_table;
	}
	
	public Signal(String name) {
		super();
		this.name = name;
	}
	public void setStart_bitAndLengthfromString(String str){
		String[] str_sig_format_1 = str.split(regex_1);
		this.start_bit = Integer.parseInt(str_sig_format_1[0]);
		String[] str_sig_format_2 = str_sig_format_1[str_sig_format_1.length-1].split(regex_2);
		this.length = Integer.parseInt(str_sig_format_2[0]);
	}
}
