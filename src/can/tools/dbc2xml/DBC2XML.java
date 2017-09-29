package can.tools.dbc2xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class DBC2XML {
	
	/* 枚举类型  */
	public enum DataType {
		NETWORK_NODE("BU_:"),
		MESSAGE("BO_"),
		SIGNAL("SG_"),
		ATTRIBUTE("BA_"),
		COMMENT("CM_"),
		VALUE_TABLE("VAL_");
	    
	    private String text;
	    
	    DataType(String text) {
	        this.text = text;
	    }
	    
	    public String getText() {
	        return this.text;
	    }
	    
	    // Implementing a fromString method on an enum type
	    private static final Map<String, DataType> stringToEnum = new HashMap<String, DataType>();
	    
	    static {
	        // Initialize map from constant name to enum constant
	        for(DataType blah : values()) {
	            stringToEnum.put(blah.toString(), blah);  //Key-Value: String-Blsh
	        }
	    }
	    
	    // Returns Blah for string, or null if string is invalid
	    public static DataType fromString(String symbol) {
	        return stringToEnum.get(symbol);
	    }

	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	String path_source;
	ArrayList<String> dataType;
	ArrayList<Nodes> network_nodes; 
	
	public DBC2XML(String path_source) {
		super();
		this.path_source = path_source;
		this.dataType = new ArrayList<>();
		
	}

	public ArrayList<Nodes> read(){
		FileReader fr = null;
		DataType dataType  = null;
		ArrayList<Nodes> nodes = new ArrayList<>();
		
		try{
			fr = new FileReader(this.path_source);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("file don't exist, pleae checck the path is correct or not?");
		}
		
		Scanner sr = new Scanner(fr);
		String str = null;
		int message_index = 0;
		while(sr.hasNextLine()){
			str = sr.nextLine();
			String[] sb = str.split("\\s+"); //用一个或多个空格符对字符串进行分割
			
			if(sb[0].equals("") && (sb.length <= 1))//匹配到空行，则跳过
				continue;
			
//			for (String s : sb) {
//				System.out.print(s+"|"); 
//			}
//			System.out.println();
//			System.out.println("raw "+sb[0]);
			if(!sb[0].equals("")) { //以非空格开头的行
				dataType = DataType.fromString(sb[0]);
//				System.out.println("non "+sb[0]);
//				System.out.println(dataType); //signal
				if(null != dataType) {
					switch(dataType) {
						case NETWORK_NODE:
							for(int i=1; i<sb.length; i++){
								nodes.add(new Nodes(sb[i])); //设置节点名称
//								System.out.println("network " + sb[i]); //NETWORK_NODE
							}
							break;
							
						case MESSAGE:
							//System.out.println("message name: " + sb[(sb.length-1)]); 
							for(message_index=0; message_index<nodes.size(); message_index++){
								//判断该message属于哪个CAN节点
								if(nodes.get(message_index).getName().equals(sb[(sb.length-1)])){
									nodes.get(message_index).setId(Integer.parseInt(sb[1])); //设置节点ID
									nodes.get(message_index).setMessages(new Messages(sb[2].substring(0, sb[2].length()-1), Integer.parseInt(sb[3]))); //设置message的名称 和长度(需要去掉后面的":"号)
//									System.out.println("message name: " + sb[2].substring(0, sb[2].length()-1) + " length: " + Integer.parseInt(sb[3])); //message
									break;
								}
							}
							break;
							
						default :
							break;
					}
				} 
			} else { //以空格开头的行
//				System.out.println("space" +sb[1]);
				dataType = DataType.fromString(sb[1]);
				if(null != dataType) {
					switch(dataType) {
					case SIGNAL:
						Signal signal = new Signal(sb[2]); //signal_name
						System.out.println("message name: " + nodes.get(message_index).getMessages().getName() + " signal name: " + sb[2]);
						signal.setStart_bitAndLengthfromString(sb[4]); //start_bit, length
						signal.setFactorandOffset(str); //factor, offset
						signal.setUnitfromString(str);
						ArrayList<Signal> al = new ArrayList<>();
						nodes.get(message_index).getMessages().setSignals(al);
						nodes.get(message_index).getMessages().getSignals().add(signal);
						break;
						
					case ATTRIBUTE:
						//System.out.println("attribute "); //signal
						break;
					
					case VALUE_TABLE:
						//System.out.println("value table "); //signal
						break;
						
					default :
						break;
					}
				} 
			}
		}
		sr.close();
		return nodes;
	}
	
	public void generateXML(ArrayList<Nodes> nodes_list){
		
		try {
			SAXTransformerFactory factory =  (SAXTransformerFactory)SAXTransformerFactory.newInstance();
			TransformerHandler handler = null;
			handler = factory.newTransformerHandler();
			Transformer info = handler.getTransformer();
			info.setOutputProperty(OutputKeys.INDENT, "yes");// 是否自动添加额外的空白
			info.setOutputProperty(OutputKeys.ENCODING, "utf-8");// 设置字符编码
			info.setOutputProperty(OutputKeys.VERSION, "1.0");
			// 保存创建的can_db.xml
			StreamResult result = new StreamResult(new FileOutputStream(new File("src/can/tools/dbc2xml/can_db.xml")));
			handler.setResult(result);
			
			handler.startDocument();// 开始xml
			AttributesImpl impl = new AttributesImpl();
			impl.clear();
			handler.startElement("", "", "node", impl);  //元素标签开始 node
			for(int i=0; i<nodes_list.size(); i++){	//遍历节点
				Nodes node = nodes_list.get(i);
				//生成<node name="xx">
				impl.clear(); //清空属性
	            impl.addAttribute("", "", "name", "", node.getName());//为node元素添加name属性
	            handler.startElement("", "", "message", impl); 
	            
	            //生成<message name="xx">xx</message>元素
	            impl.addAttribute("", "", "name", "", node.getMessages().getName());//为message元素name属性          
	            ArrayList<Signal> signals = node.getMessages().getSignals();
	            
	            //生成<signal>xx</signal>元素
	            for(int j=0; j<signals.size(); j++){
	            	impl.clear(); 
	            	handler.startElement("", "", "signal", impl);
	            	
	            	handler.endElement("", "", "name"); 
	            	
	            	handler.startElement("", "", "name", impl); 
	            	
	            	handler.startElement("", "", "start_bit", impl); 
	            	handler.endElement("", "", "start_bit"); 
	            	
	            	handler.startElement("", "", "length", impl); 
	            	handler.endElement("", "", "length"); 

	 	            handler.endElement("", "", "signal"); 
	            }
	            impl.clear(); 
	            //生成</message>
	            handler.endElement("", "", "message");
	            
			}
			//生成</bookstore>
			handler.endElement("", "", "node");
			handler.endDocument(); // 结束xml
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
