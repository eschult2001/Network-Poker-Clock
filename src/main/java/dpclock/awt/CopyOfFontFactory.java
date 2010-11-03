package dpclock.awt;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CopyOfFontFactory extends AbstractFactoryBean<Font> implements DisposableBean {
	
	public static final int BOLDITALIC = Font.BOLD | Font.ITALIC;
	
    private String fontResourceName;
    private int fontResourceType = Font.TRUETYPE_FONT;
    private int style = Font.PLAIN;
    private int size = 24;
    private boolean registerFont = true;
    
    public Class<Font> getObjectType() {
        return Font.class;
    }

    protected Font createInstance() throws Exception {
        InputStream is = CopyOfFontFactory.class.getResourceAsStream(fontResourceName);
        Font font = Font.createFont(fontResourceType, is);
        if (registerFont) {
        	GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        }
        return font.deriveFont(style, size);
    }

    protected void destroyInstance(Font instance) throws Exception {
        super.destroyInstance(instance);
        if (instance instanceof Font) {
        	//? What to do?
        }
    }

    public void destroy() throws Exception {
        super.destroy();    
    }
    
    public void setFontResourceName(String fontResourceName) {
		this.fontResourceName = fontResourceName;
	}
    public void setFontResourceType(int fontResourceType) {
		this.fontResourceType = fontResourceType;
	}
    public void setStyle(int style) {
		this.style = style;
	}
    public void setSize(int size) {
		this.size = size;
	}
    public void setRegisterFont(boolean registerFont) {
		this.registerFont = registerFont;
	}
}
