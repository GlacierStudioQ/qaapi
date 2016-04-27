package com.qaapi.memorydb;

public class SchemaNotExistException extends Exception {
	@Override
	public void printStackTrace() {
		System.err.println("此schema不存在");
		super.printStackTrace();
	}
}
