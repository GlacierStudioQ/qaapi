package com.qaapi.memorydb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 应用启动时调用的servlet，
 * 调用DataLoader完成Lucene的初始化
 * 
 * @author IceAsh
 *
 */
public class LoadAllDataServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		
		System.out.println("sys => start init lucene...");
		
	}
}
