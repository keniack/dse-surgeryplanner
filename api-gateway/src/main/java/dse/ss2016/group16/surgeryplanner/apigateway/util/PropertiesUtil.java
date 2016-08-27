package dse.ss2016.group16.surgeryplanner.apigateway.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesUtil {

		static String fileName = "application.properties";
		
		public static Properties load() {
			Properties prop = new Properties();
			try {
				InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return prop;
		}
		
		public static String getValue(String valueType){
			Properties prop = load();
			return prop.getProperty(valueType);
		}
		
	}
