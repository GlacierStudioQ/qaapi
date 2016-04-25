package com.qaapi;

import java.io.IOException;
import java.util.Properties;

public class DBInfo {
	
	public static Properties appConf = null;

	public static String get(String key){
		if(appConf == null){
			Properties ps = new Properties();
			try {
				ps.load(DBInfo.class.getResourceAsStream("/jdbc.properties"));
				appConf=ps;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return appConf.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(get("jdbc.url"));
	}
}
