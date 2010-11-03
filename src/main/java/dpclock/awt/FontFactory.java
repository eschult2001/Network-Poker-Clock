/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.awt;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class FontFactory extends AbstractFactoryBean<Font> {
	
	/** Max # o pieces in a font spec  (Name,Style,Size) */
	public static final int SPEC_MAX_PIECES = 3;
	/** Char(s) that split a fontSpec */
	private static final String SPEC_SEPARATOR = ",";
	/** Default font style */
	public static final int DEFAULT_STYLE = Font.PLAIN;
	/** Default font size */
	public static final int DEFAULT_SIZE = 12;
	/** Holder for style->flags mapping */
	private static final Map<String,Integer> TYPES = new HashMap<String,Integer>();
	static {
		TYPES.put("plain", Font.PLAIN);
		TYPES.put("italic", Font.ITALIC);
		TYPES.put("bold", Font.BOLD);
		TYPES.put("bolditalic", Font.BOLD | Font.ITALIC);
	}

	/** The font we are creating */
    private String fontSpec;
    /** True if the font should be registered with the GraphicsEnvironment */
    private boolean registerFont = true;
    
    public final Class<Font> getObjectType() {
        return Font.class;
    }

    protected final Font createInstance() throws IOException, FontFormatException {
 
    	final String args[] = fontSpec.split(SPEC_SEPARATOR,SPEC_MAX_PIECES);
    	final String name = args[0];
    	int style = DEFAULT_STYLE;
    	int size = DEFAULT_SIZE;
    	
    	if (args.length==SPEC_MAX_PIECES) {
    		style = TYPES.containsKey(args[1])? TYPES.get(args[1]).intValue() : Font.PLAIN;
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

    public final void setRegisterFont(boolean registerFont) {
		this.registerFont = registerFont;
	}
    
    public final void setFontSpec(String fontSpec) {
		this.fontSpec = fontSpec;
	}
}
