package com.qaapi.memorydb;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		initMemoryDB(config.getServletContext());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		initMemoryDB(req.getServletContext());
	}

	public void initMemoryDB(ServletContext application){
		// 在init方法中需要手动获取bean
		ApplicationContext ctx = (ApplicationContext) application.getAttribute(XmlWebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		dataLoaderService = (DataLoaderService)ctx.getBean("dataLoaderService");
		
		System.out.println("sys => start init lucene...");

		long startTime = System.nanoTime();

		try {
			dataLoaderService.loadAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		long endTime = System.nanoTime();
		DecimalFormat df = new DecimalFormat(",###.00");

		System.out.println("sys => end init lucene , total time : "
				+ df.format(endTime - startTime) + "ns");
	}
	
}
