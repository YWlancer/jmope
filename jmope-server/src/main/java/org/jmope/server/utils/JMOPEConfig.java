package org.jmope.server.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Load the configurations as a single instance.
 * 
 * @author sithum
 *
 */

public class JMOPEConfig {
	
	private static final String CONFIG = "jmope-config.properties";
	
	private static JMOPEConfig instance = null;
	private Properties config = new Properties();
	
	private JMOPEConfig() {
		this.load();
	}
	
	public static JMOPEConfig getInstance() {
		synchronized (JMOPEConfig.class) {
			if (instance == null) {
				instance = new JMOPEConfig();
			}
			return instance;
		}
	}
	
	private void load(){
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getConfiguration(String key) {
		return this.config.getProperty(key);
	}

}
