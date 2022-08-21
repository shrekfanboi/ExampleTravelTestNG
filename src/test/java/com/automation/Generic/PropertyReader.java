package com.automation.Generic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader{

	static Properties props = new Properties();

	public static void initProperty() {
		try {
			props.load(new FileInputStream("./config/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static  String getProperty(String key) {
		return props.getProperty(key, "none");
	}
	
}
