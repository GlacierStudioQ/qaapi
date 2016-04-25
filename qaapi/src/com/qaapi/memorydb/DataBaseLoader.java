package com.qaapi.memorydb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qaapi.bean.Authority;
import com.qaapi.bean.FaqEntry;
import com.qaapi.bean.Schema;
import com.qaapi.dao.AuthDao;
import com.qaapi.dao.FaqDao;
import com.qaapi.dao.SchemaDao;
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
	 */
	public void loadAll(){
		
		NowSchemaHolder.set(DFT_DB);
		
		//scheams
		List<Schema> allSchemas = schemaDao.loadAll();
		SCHEMAS = new ArrayList<Schema>(allSchemas);
		
		//authorities
		AUTHORITIES = new HashMap<String, List<String>>();
		List<Authority> allAuths = authDao.loadAll();
		for (Authority auth : allAuths) {
			if(!AUTHORITIES.containsKey(auth.getDomain())){
				AUTHORITIES.put(auth.getDomain(), new ArrayList<String>());
			}
			AUTHORITIES.get(auth.getDomain()).add(auth.getSchemasName());
		}
		
		//FaqEntries
		FAQ_ENTRIES = new HashMap<String, Map<Long,FaqEntry>>();
		for (Schema schema : SCHEMAS) {
			NowSchemaHolder.set(schema.getName());
			List<FaqEntry> allEntries = faqDao.loadAll();
			
			Map<Long, FaqEntry> entriesInASchema = new HashMap<Long, FaqEntry>();
			FAQ_ENTRIES.put(schema.getName(), entriesInASchema);
			for (FaqEntry faqEntry : allEntries) {
				entriesInASchema.put(faqEntry.getId(), faqEntry);
			}
		}
		
		NowSchemaHolder.set(DFT_DB);
	}
	
	/**
	 * 根据id读取特定库中的数据
	 * @param Map(schemaName,List(faqEntriesIds))
	 */
	public void loadLocality(Map<String,List<Long>> ids){
		
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
