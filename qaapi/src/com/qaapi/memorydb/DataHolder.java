package com.qaapi.memorydb;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qaapi.bean.FaqEntry;
import com.qaapi.bean.Schema;

/**
 * 内含三个静态容器类，用于存放从DB中获取的全部信息
 * 
 * @author IceAsh
 *
 */
public class DataHolder {
	/*
	 * List<schemaName>
	 * 存放系统一共包含几个库
	 */
	public static List<Schema> SCHEMAS;
	
	/*
	 * List<schemaName>
	 * 存放系统一共包含的库的名字
	 */
	public static Set<String> SCHEMAS_NAME;
	
	/**
	 * 把schema列表转化为schemaName列表
	 */
	public static void extractSchemasName(){
		if(SCHEMAS == null){
			return;
		}
		SCHEMAS_NAME = new HashSet<String>();
		for (Schema schema : SCHEMAS) {
			SCHEMAS_NAME.add(schema.getName());
		}
	}
	
	/*
	 * Map<domainName ,List<schemaName>>
	 * 存放每个用户对库的访问权限（谁可以访问哪些库）
	 */
	public static Map<String ,List<String>> AUTHORITIES;
	
	/*
	 * Map<schemaName ,Map<id, FaqEntry>>
	 * 存放每个库中的所有问答
	 */
	public static Map<String ,Map<Long, FaqEntry>> FAQ_ENTRIES;
	
	/*
	 * Map<schemaName ,Map<id, FaqEntry>>
	 * 存放每个库中的所有问答
	 * 这个存放的是已经分词的结果
	 */
	public static Map<String ,Map<Long, List<String>>> FAQ_ENTRIES_PARSED;
}
