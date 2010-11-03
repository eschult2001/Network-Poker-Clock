package settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import dpclock.schema.LevelBean;
import dpclock.schema.Tournament;

public class Application {
    private static final String FILE_NAME = "settings.xml";
    private Serializable settings;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void setSettings(Serializable settings) {
		this.settings = settings;
	}
    
    public void saveSettings() throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(FILE_NAME);
            this.marshaller.marshal(settings, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public void loadSettings() throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(FILE_NAME);
            this.settings = (Serializable) this.unmarshaller.unmarshal(new StreamSource(is));
            Tournament tournament = (Tournament) this.settings;
            System.out.println("Loaded: "+this.settings);
            System.out.println("         Name: " + tournament.getTournamentName());
            System.out.println("Current Level: " + tournament.getCurrentLevel());
            System.out.println("Levels:");
            for(LevelBean level : tournament.getLevels()) {
            	System.out.println(level.toString());
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext appContext =
            new ClassPathXmlApplicationContext( new String[]{
            		//"/dpclock/conf/app-data.xml",
            		"/settings/applicationContext.xml"});
        Application application = (Application) appContext.getBean("application");
        application.saveSettings();
        application.loadSettings();
    }
}