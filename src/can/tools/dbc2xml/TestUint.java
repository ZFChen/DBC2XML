package can.tools.dbc2xml;

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
		
//		String str = "  SG_  CCP_Resp : ";
//		String[] sb = str.split("\\s+");
//		for (String string : sb) {
//			System.out.println(string);
//		}
	}

}
