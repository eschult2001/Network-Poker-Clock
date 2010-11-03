package dpclock.schema.util;

import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Component;

import dpclock.schema.BreakLevelBean;
import dpclock.schema.LevelBean;
import dpclock.schema.Tournament;
import dpclock.schema.TournamentLevelBean;

@Component
public class TournamentUnmarshaller extends AbstractFactoryBean<Tournament> {

	
	public static final String DEFAULT_FILE_NAME = "tournament.xml";
	public static final String DEFAULT_SCHEMA_FILE = "tournament.xsd";

	private String fileName = DEFAULT_FILE_NAME;
	private Unmarshaller unmarshaller;
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	@Override
	public Class<?> getObjectType() {
		return Tournament.class;
	}

	@Override
	protected Tournament createInstance() throws Exception {
        FileInputStream is = null;
        try {
            is = new FileInputStream(fileName);
            Tournament settings = (Tournament) this.unmarshaller.unmarshal(new StreamSource(is));
            return settings;
        } finally {
            if (is != null) {
                is.close();
            }
        }
	}

}
