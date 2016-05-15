package com.qaapi.memorydb;

public class SchemaNotExistException extends Exception {
	
	String schemaName;
	
	public SchemaNotExistException(String schemaName) {
		this.schemaName = schemaName;
	}

	@Override
	public void printStackTrace() {
		System.err.println("此schema不存在：" + schemaName);
		super.printStackTrace();
	}
	
}
