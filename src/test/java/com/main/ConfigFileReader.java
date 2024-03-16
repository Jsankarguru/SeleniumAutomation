package com.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigFileReader {
	
	// Read the locator properties file
	public static String getLocator(String Key) {
		
		Properties log = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("./Configuration/locators.properties");
			log.load(input);			
		} catch (IOException lo) {
			lo.printStackTrace();
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return log.getProperty(Key);
	}
	

	// Read the config properties file
	public static String getProperty(String Key)

	{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			// read the properties file.
			input = new FileInputStream("./Configuration/configFile.properties");
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return prop.getProperty(Key);

	}

}
