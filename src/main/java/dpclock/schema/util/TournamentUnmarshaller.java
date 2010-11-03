/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.schema.util;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import dpclock.schema.Tournament;

@Component
public class TournamentUnmarshaller extends AbstractFactoryBean<Tournament> {

	
	public static final String DEFAULT_FILE_NAME = "tournament.xml";
	public static final String DEFAULT_SCHEMA_FILE = "tournament.xsd";

	private String fileName = DEFAULT_FILE_NAME;
	private Unmarshaller unmarshaller;
	
	public final void setFileName(final String fileName) {
		this.fileName = fileName;
	}
	public final void setUnmarshaller(final Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	@Override
	public final Class<?> getObjectType() {
		return Tournament.class;
	}

	@Override
	protected final Tournament createInstance() throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(fileName);
            return (Tournament) this.unmarshaller.unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
	}

}
