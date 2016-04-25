package com.qaapi.multisource;

public class NowSchemaHolder {
	
	private static ThreadLocal<String> NOW_SCHEMA = new ThreadLocal<String>();
	public static final String DFT_DB = "default";
	
	public static String get() {
		return NOW_SCHEMA.get();
	}

	public static void set(String name) {
		NOW_SCHEMA.set(name);
	}

	public static void remove(){
		NOW_SCHEMA.remove();
	}
}
