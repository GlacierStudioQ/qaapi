package com.qaapi.memorydb;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qaapi.lucenequery.DocumentsLoader;

/**
 * 调用DBLoader加载全部数据，
 * 之后调用LoadDocument把数据全部输入Lucene中
 * 
 * @author IceAsh
 *
 */
@Service("dataLoaderService")
public class DataLoaderService {
	
	@Autowired
	DataBaseLoader dataBaseLoader;
	
	
	/**
	 * 读取全部数据，并通过LoadAllDocuments全部加载到lucene中
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void loadAll() throws ParseException, IOException{
		dataBaseLoader.loadAll();
		// lucene
		DocumentsLoader.loadAll();
		
		// lcs
		// lcs与lucene共用数据结构，不需要再加载
		
		// kei
		
	}
	
	/**
	 * 根据id读取特定库中的数据，并重新加载Lucene
	 * @param Map(schemaName,List(faqEntriesIds))
	 */
	public void loadLocality(Map<String,List<Long>> ids){
		
	}

	//============setter&getter============
	
	
	public DataBaseLoader getDataBaseLoader() {
		return dataBaseLoader;
	}

	public void setDataBaseLoader(DataBaseLoader dataBaseLoader) {
		this.dataBaseLoader = dataBaseLoader;
	}
	
}
