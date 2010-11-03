package dpclock.awt;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class FontFactory extends AbstractFactoryBean<Font> implements DisposableBean {
	
	public static final int BOLDITALIC = Font.BOLD | Font.ITALIC;
	private static final Map<String,Integer> types = new HashMap<String,Integer>();
	static {
		types.put("plain", Font.PLAIN);
		types.put("italic", Font.ITALIC);
		types.put("bold", Font.BOLD);
		types.put("bolditalic", Font.BOLD | Font.ITALIC);
	}
	
    private String fontSpec;
    private boolean registerFont = true;
    
    public Class<Font> getObjectType() {
        return Font.class;
    }

    protected Font createInstance() throws Exception {
 
    	String args[] = fontSpec.split(",",3);
    	String name = args[0];
    	int style = Font.PLAIN;
    	int size = 12;
    	
    	if (args.length==3) {
    		style = types.containsKey(args[1])? types.get(args[1]).intValue() : Font.PLAIN;
    		size = Integer.parseInt(args[2]);
    	} else if (args.length ==2) {
    		size = Integer.parseInt(args[1]);
    	}
    
    	if (name.startsWith("/")) {
	        InputStream is = FontFactory.class.getResourceAsStream(name);
	        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
	        if (registerFont) {
	        	GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
	        }
	        return font.deriveFont(style, size);
    	}
    	return new Font(name,style,size);
    }

    protected void destroyInstance(Font instance) throws Exception {
        super.destroyInstance(instance);
        if (instance instanceof Font) {
        	//? What to do?
        }
    }

    public void setRegisterFont(boolean registerFont) {
		this.registerFont = registerFont;
	}
    
    public void setFontSpec(String fontSpec) {
		this.fontSpec = fontSpec;
	}
}
