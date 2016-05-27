package com.qaapi.memorydb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qaapi.bean.Authority;
import com.qaapi.bean.FaqEntry;
import com.qaapi.bean.Schema;
import com.qaapi.dao.AuthDao;
import com.qaapi.dao.FaqDao;
import com.qaapi.dao.SchemaDao;
import com.qaapi.lucenequery.ParseKeyword;
import com.qaapi.multisource.NowSchemaHolder;

import static com.qaapi.memorydb.DataHolder.*;
import static com.qaapi.multisource.NowSchemaHolder.DFT_DB;

/**
 * 从DB中读取数据之后放入DataHolder中
 * 读取全部或按照id读取部分数据
 * 
 * @author IceAsh
 *
 */
@Service("dataBaseLoader")
public class DataBaseLoader {
	
	@Autowired
	SchemaDao schemaDao;
	
	@Autowired
	AuthDao authDao;
	
	@Autowired
	FaqDao faqDao;
	
	/**
	 * 读取全部数据
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void loadAll() throws ParseException, IOException{
		
		NowSchemaHolder.set(DFT_DB);
		
		//scheams
		List<Schema> allSchemas = schemaDao.loadAll();
		SCHEMAS = new ArrayList<Schema>(allSchemas);
		DataHolder.makeSchemasNameIndex();;// 初始化schema名与schema映射的map
		
		//authorities
		AUTHORITIES = new HashMap<String, List<String>>();
		List<Authority> allAuths = authDao.loadAll();
		for (Authority auth : allAuths) {
			if(!AUTHORITIES.containsKey(auth.getDomain())){
				AUTHORITIES.put(auth.getDomain(), new ArrayList<String>());
			}
			AUTHORITIES.get(auth.getDomain()).add(auth.getSchemasName());
		}
		DOMAINS_NAME = new HashSet<String>(AUTHORITIES.keySet());// 初始化domain名的set
		
		//FaqEntries
		FAQ_ENTRIES = new HashMap<String, Map<Long,FaqEntry>>();
		for (Schema schema : SCHEMAS) {
			NowSchemaHolder.set(schema.getName());
			List<FaqEntry> allEntries = faqDao.loadAll();
			
			Map<Long, FaqEntry> entriesInASchema = new HashMap<Long, FaqEntry>();
			FAQ_ENTRIES.put(schema.getName(), entriesInASchema);
			for (FaqEntry faqEntry : allEntries) {
				
				// 剪掉问题两端的空白符
				faqEntry.setQuestion(StringUtils.trimToEmpty(faqEntry.getQuestion()));
				
				entriesInASchema.put(faqEntry.getId(), faqEntry);
			}
		}
		NowSchemaHolder.set(DFT_DB);
		
		// kei数据结构
		ParseKeyword parseKeyword = new ParseKeyword();
		FAQ_ENTRIES_PARSED = new HashMap<String, Map<Long,List<String>>>();
		for (Schema schema : SCHEMAS) {
			NowSchemaHolder.set(schema.getName());
			List<FaqEntry> allEntries = faqDao.loadAll();
			
			Map<Long, List<String>> wordElementsInASchema = new HashMap<Long, List<String>>();
			FAQ_ENTRIES_PARSED.put(schema.getName(), wordElementsInASchema);
			for (FaqEntry faqEntry : allEntries) {
				
				// 剪掉问题两端的空白符
				faqEntry.setQuestion(StringUtils.trimToEmpty(faqEntry.getQuestion()));
				
				wordElementsInASchema.put(faqEntry.getId(), parseKeyword.getWordElements(faqEntry.getQuestion()));
			}
		}
		NowSchemaHolder.set(DFT_DB);
	}
	
	/**
	 * 根据id读取特定库中的数据
	 * @param Map(schemaName,List(faqEntriesIds))
	 */
	public void loadLocality(Map<String,List<Long>> ids){
		// TODO:还未实现
	}

	
	//============setter&getter============
	public SchemaDao getSchemaDao() {
		return schemaDao;
	}

	public void setSchemaDao(SchemaDao schemaDao) {
		this.schemaDao = schemaDao;
	}

	public AuthDao getAuthDao() {
		return authDao;
	}

	public void setAuthDao(AuthDao authDao) {
		this.authDao = authDao;
	}

	public FaqDao getFaqDao() {
		return faqDao;
	}

	public void setFaqDao(FaqDao faqDao) {
		this.faqDao = faqDao;
	}
	
}
