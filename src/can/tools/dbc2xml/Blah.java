package can.tools.dbc2xml;

import java.util.HashMap;
import java.util.Map;


public enum Blah {
	NETWORK_NODE("BU_:"),
	MESSAGE("BO_"),
	SIGNAL("SG_"),
	ATTRIBUTE("BA_"),
	COMMENT("CM_"),
	VALUE_TABLE("VAL_");
    
    private String text;
    
    Blah(String text) {
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    // Implementing a fromString method on an enum type
    private static final Map<String, Blah> stringToEnum = new HashMap<String, Blah>();
    
    static {
        // Initialize map from constant name to enum constant
        for(Blah blah : values()) {
            stringToEnum.put(blah.toString(), blah);  //Key-Value: String-Blsh
        }
    }
    
    // Returns Blah for string, or null if string is invalid
    public static Blah fromString(String symbol) {
        return stringToEnum.get(symbol);
    }

    @Override
    public String toString() {
        return text;
    }
}
