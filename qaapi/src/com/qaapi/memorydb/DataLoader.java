package com.qaapi.memorydb;

import java.util.List;
import java.util.Map;

/**
 * 调用DBLoader加载全部数据，
 * 之后调用LoadDocument把数据全部输入Lucene中
 * 
 * @author IceAsh
 *
 */
public class DataLoader {
	/**
	 * 读取全部数据
	 */
	public void loadAll(){
		
	}
	
	/**
	 * 根据id读取特定库中的数据
	 * @param Map(schemaName,List(faqEntriesIds))
	 */
	public void loadLocality(Map<String,List<Long>> ids){
		
	}
}
