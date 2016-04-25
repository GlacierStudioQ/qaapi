package com.qaapi.memorydb;

import java.text.DecimalFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 应用启动时调用的servlet， 调用DataLoader完成Lucene的初始化
 * 
 * @author IceAsh
 *
 */
public class LoadAllDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DataLoaderService dataLoaderService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		// 在init方法中需要手动获取bean
		ServletContext application = config.getServletContext();
		ApplicationContext ctx = (ApplicationContext) application.getAttribute(XmlWebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		dataLoaderService = (DataLoaderService)ctx.getBean("dataLoaderService");
		
		System.out.println("sys => start init lucene...");

		long startTime = System.nanoTime();

		dataLoaderService.loadAll();

		long endTime = System.nanoTime();
		DecimalFormat df = new DecimalFormat(",###.00");

		System.out.println("sys => end init lucene , total time : "
				+ df.format(endTime - startTime) + "ns");
	}

}
