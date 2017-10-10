package can.tools.dbc2xml;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Blah bl = Blah.fromString("BU_:");
//		System.out.println(bl.toString()); //BU_:
//		System.out.println(bl.name()); //NETWORK_NODE
		
		
		DBC2XML d2x = new DBC2XML("./src/Geely_VF11_V1.7.dbc");
		d2x.generateXML(d2x.read());
		
		//String content = "src: local('Open Sans Light'), local('OpenSans-Light'), url(http://fonts.gstatic.com/s/opensans/v13/DXI1ORHCpsQm3Vp6mXoaTa-j2U0lmluP9RWlSytm3ho.woff2) format('woff2')";
//		String content = "SG_ EMS_EngineCoolantTemperature : 7|8@0+ (0.75,-36.8) [-36.8|137.2]  AC";
//		// 从内容上截取路径数组
//		Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");  
//		Matcher matcher = pattern.matcher(content);
//		if(matcher.find()){
//			String s[] = matcher.group().split(",");
//			if(s.length >= 2){
//				System.out.println("factor: "+Float.parseFloat(s[0]));
//				System.out.println("offset "+Float.parseFloat(s[1]));
//			}
//		}
		
//		String content = "SG_ EMS_EngineCoolantTemperature : 7|8@0+ (0.75,-36.8) [-36.8|137.2] \"bit\" AC";
//		// 从内容上截取路径数组
//		Pattern pattern = Pattern.compile("(?=\")[\"\\S]+");  
//		Matcher matcher = pattern.matcher(content);
//		if(matcher.find()){
//			System.out.println("raw: " + matcher.group());
//			String s[] = matcher.group().split("\"");
//			System.out.println(s.length);
//			for (String string : s) {
//				System.out.println(string);
//			}			
//			if(matcher.group().length() > 2)
//				System.out.println(matcher.group());
//			else
//				System.out.println("no match");
//		}
		
//		String str = "  SG_  CCP_Resp : ";
//		String[] sb = str.split("\\s+");
//		for (String string : sb) {
//			System.out.println(string);
//		}
		
/*		FileReader fr = null;
		try{
			fr = new FileReader("./src/Geely_VF11_V1.7.dbc");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("file don't exist, pleae checck the path is correct or not?");
		}
		Scanner sr = new Scanner(fr);
		String str = null;
		while(sr.hasNextLine()){
			str = sr.nextLine();
			String[] sb = str.split("\\s");
			System.out.println(sb.length);
			for (String s : sb) {
				System.out.print(s+"|"); 
			}
			System.out.println();
//			System.out.println(str);
//			
//			switch(str){
//			case "BU_:":
//				System.out.println("network");
//				break;
//				
//			default :
//				break;
//			}
		}
		sr.close();*/
//		ArrayList<String> al = new ArrayList<>();
//        String regx = "((?<=\\s\").*?(?=\"))|(\\w+)";
//		String strs = "say \"hello world\"";
//		String str = "VAL_ 753 AC_ACCompReq 1 \"AC Compress Req ON\" 0 \"AC Compress Req OFF\" ;";
//		Pattern pattern = Pattern.compile(regx);  
//		Matcher matcher = pattern.matcher(str);
//		while(matcher.find()){
//			String s = matcher.group();
//			System.out.println(s);
//			al.add(s);
//		}
//		System.out.println("signal name: " + al.get(2));
//		System.out.println("signal length: " + al.size());
//		int a = (al.size()-3)/2;
//		for(int i=0; i<a; i++){
//			System.out.println( al.get(2*i+3) +": "+al.get(2*i+4));
//		}
	}

}
