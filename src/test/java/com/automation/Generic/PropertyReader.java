package com.automation.Generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertyReader {

	static Properties props = new Properties();

	public static void initProperty() {
		try {
			props.load(new FileInputStream("./src/test/resources/config/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key, "none");
	}

	public static Map<String, Object> getProperties(String k) {
		Map<String, Object> map = new HashMap<>();
		Set<Object> keys = props.keySet();
		for (Object obj : keys) {
			String key = obj.toString();
			if (key.contains(k)) {
				map.put(key, getProperty(key));
			}
		}
		return map;
	}
	public static void updateProperty(String key,String value) {
		props.setProperty(key, value);
		try {
			props.store(new FileOutputStream("./src/test/resources/config/config.properties"),null);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void resetProperty() {
		try {
			FileReader fin = new FileReader("./src/test/resources/config/config.default.properties");
			FileWriter fout = new FileWriter("./src/test/resources/config/config.properties");
			int c;  
			  while ((c = fin.read()) != -1) {  
			   fout.write(c);  
			  }  
			  fin.close();  
			  fout.close(); 
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
