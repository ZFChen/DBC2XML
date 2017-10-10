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
		int node_index = 0;
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
							for(int i=1; i<sb.length; i++) {
								nodes.add(new Nodes(sb[i])); //设置节点名称
//								System.out.println("network " + sb[i]); //NETWORK_NODE
							}
							break;
							
						case MESSAGE:
							//System.out.println("message name: " + sb[(sb.length-1)]); 
							for(node_index=0; node_index<nodes.size(); node_index++){
								//判断该message属于哪个CAN节点
								if(nodes.get(node_index).getName().equals(sb[(sb.length-1)])){
									Messages message = new Messages(sb[2].substring(0, sb[2].length()-1), 
											Integer.parseInt(sb[3]), Integer.parseInt(sb[1]));  //创建message 名称,长度和ID号(需要去掉后面的":"号)
									if(null == nodes.get(node_index).getMessages())
									{
										ArrayList<Messages> al = new ArrayList<>();
										al.add(message);
										nodes.get(node_index).setMessages(al);
										message_index = 0;
									}
									else
									{
										nodes.get(node_index).getMessages().add(message);
										message_index = nodes.get(node_index).getMessages().size()-1;
									}
//									System.out.println("message name: " + sb[2].substring(0, sb[2].length()-1) + " length: " + Integer.parseInt(sb[3])); //message
									break;
								}
							}
							break;
							
						case ATTRIBUTE:
							//System.out.println("attribute "); //signal
							break;
						
						case VALUE_TABLE:
							//System.out.println("value table "); //signal
							Signal sig = new Signal(" ");
							sig.setVauleTable(str, nodes);
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
						//int ms_index;
						ArrayList<Signal> al;
						Signal signal = new Signal(sb[2]); //signal_name
						//System.out.println("signal name: "+sb[2]);
						//System.out.println("message size: "+nodes.get(node_index).getMessages().size());
						/*
						for(ms_index=0; ms_index<nodes.get(node_index).getMessages().size(); ms_index++)
						{
							System.out.println(nodes.get(node_index).getMessages().get(ms_index).getName());
							if(nodes.get(node_index).getMessages().get(ms_index).getName().equals(signal.getName()))
							{
								System.out.println("message name: " + nodes.get(node_index).getMessages().get(ms_index).getName() + " signal name: " + sb[2]);
								signal.setStart_bitAndLengthfromString(sb[4]); //start_bit, length
								signal.setFactorandOffset(str); //factor, offset
								signal.setUnitfromString(str);
								ArrayList<Signal> al = new ArrayList<>();
								nodes.get(node_index).getMessages().get(ms_index).setSignals(al);
								nodes.get(node_index).getMessages().get(ms_index).getSignals().add(signal);
								break;
							}
						}*/
						//System.out.println("message name: " + nodes.get(node_index).getMessages().get(message_index).getName() + " signal name: " + sb[2]);
						signal.setStart_bitAndLengthfromString(sb[4]); //start_bit, length
						signal.setFactorandOffset(str); //factor, offset
						signal.setUnitfromString(str);
						al = nodes.get(node_index).getMessages().get(message_index).getSignals();
						if(null == al) {
						    al = new ArrayList<>();
						}
						nodes.get(node_index).getMessages().get(message_index).setSignals(al);
						nodes.get(node_index).getMessages().get(message_index).getSignals().add(signal);
						break;
						
					default :
						break;
					}
				} 
			}
		}
		sr.close();
		for (Nodes nd : nodes) {
			System.out.println(nd);
		}
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
			handler.startElement("", "", "network", impl);  //元素标签开始 network
			for(int i=0; i<nodes_list.size(); i++) {	//遍历节点
				Nodes node = nodes_list.get(i);
				
				//生成<node></node>
				impl.clear(); //清空属性
	            handler.startElement("", "", "node", impl); 
	            
	            //生成<node_name></node_name>
	            impl.clear();   
                handler.startElement("", "", "node_name", impl); 
                String node_name = node.getName();  
                handler.characters(node_name.toCharArray(), 0, node_name.length());   
                handler.endElement("", "", "node_name"); 
                
                if(null != node.getMessages())
                {
                	Messages message = null; 
                	for(int j=0; j<node.getMessages().size(); j++) {
                		message = node.getMessages().get(j);
                		
                		//生成<message>xx</message>元素
        	            impl.clear(); 
                        handler.startElement("", "", "message", impl); 
                        
                		impl.clear(); 
    	            	handler.startElement("", "", "message_name", impl);
    	            	String message_name = message.getName();  
    	                handler.characters(message_name.toCharArray(), 0, message_name.length());   
    	                handler.endElement("", "", "message_name"); 
    	                
    	                impl.clear(); 
    	            	handler.startElement("", "", "length", impl);
    	            	String length = Integer.toString(message.getLength());
    	                handler.characters(length.toCharArray(), 0, length.length());   
    	                handler.endElement("", "", "length"); 
    	                
    	                impl.clear(); 
    	            	handler.startElement("", "", "id", impl);
    	            	String id = Integer.toString(message.getId());
    	                handler.characters(id.toCharArray(), 0, id.length());   
    	                handler.endElement("", "", "id"); 
    	                
    	                if(null != message.getSignals())
    	                {
    	                	Signal signal = null;
    	                	for(int k=0; k<message.getSignals().size(); k++) {
    	                		signal = message.getSignals().get(k);
    	                		
    	                		impl.clear();
    	                		handler.startElement("", "", "signal", impl);
    	                		
    	    	            	handler.startElement("", "", "signal_name", impl);
    	    	            	String signal_name = signal.getName();  
    	    	                handler.characters(signal_name.toCharArray(), 0, signal_name.length());   
    	    	                handler.endElement("", "", "signal_name"); 
    	    	                
    	    	                impl.clear(); 
    	    	            	handler.startElement("", "", "start_bit", impl);
    	    	            	String start_bit = Integer.toString(signal.getStart_bit());
    	    	                handler.characters(start_bit.toCharArray(), 0, start_bit.length());   
    	    	                handler.endElement("", "", "start_bit");
    	    	                
    	    	                impl.clear(); 
    	    	            	handler.startElement("", "", "length", impl);
    	    	            	String signal_length = Integer.toString(signal.getLength());
    	    	                handler.characters(signal_length.toCharArray(), 0, signal_length.length());   
    	    	                handler.endElement("", "", "length");
    	    	                
    	    	                impl.clear(); 
    	    	            	handler.startElement("", "", "unit", impl);
    	    	            	String unit = signal.getUnit();
    	    	            	if(null != unit)
    	    	                    handler.characters(unit.toCharArray(), 0, unit.length());   
    	    	                handler.endElement("", "", "unit");
    	    	                
    	    	                impl.clear(); 
    	    	            	handler.startElement("", "", "factor", impl);
    	    	            	String factor = Double.toString(signal.getFactor());
    	    	                handler.characters(factor.toCharArray(), 0, factor.length());   
    	    	                handler.endElement("", "", "factor");
    	    	                
    	    	                impl.clear(); 
    	    	            	handler.startElement("", "", "offset", impl);
    	    	            	String offset = Double.toString(signal.getOffset());
    	    	                handler.characters(offset.toCharArray(), 0, offset.length());   
    	    	                handler.endElement("", "", "offset");
    	    	                
    	    	                impl.clear(); 
    	    	            	handler.startElement("", "", "val_table", impl); /* 如果key-value为空，则该元素为空元素，表示为 <val_table/> */
    	    	            	/* 遍历key-value */
    	    	            	if(null != signal.getVal_table()) {
    	    	            		for(Integer key : signal.getVal_table().keySet()) {
    	    	            			impl.clear(); 
        	    	            		handler.startElement("", "", "key", impl);
        	    	            	    String key_name = Integer.toString(key);
        	    	            	    handler.characters(key_name.toCharArray(), 0, key_name.length());
        	    	                    handler.endElement("", "", "key");
        	    	                    
        	    	                    impl.clear(); 
        	    	                    handler.startElement("", "", "value", impl);
        	    	                    String value = signal.getVal_table().get(key);
        	    	                    handler.characters(value.toCharArray(), 0, value.length());
            	    	                handler.endElement("", "", "value");
        	    	            	}
    	    	            	}
    	    	                handler.endElement("", "", "val_table");
    	    	                handler.endElement("", "", "signal");
    	                	}
    	                }
    	                //生成</message>
    	                impl.clear(); 
    		            handler.endElement("", "", "message");
                	}
                }
                impl.clear();
                handler.endElement("", "", "node"); 
			}
			//生成</bookstore>
			handler.endElement("", "", "network");
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
