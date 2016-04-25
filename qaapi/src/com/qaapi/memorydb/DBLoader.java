package com.qaapi.memorydb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qaapi.bean.Authority;
import com.qaapi.bean.Schema;
import com.qaapi.dao.AuthDao;
import com.qaapi.dao.FaqDao;
import com.qaapi.dao.SchemaDao;

import static com.qaapi.memorydb.DataHolder.*;

/**
 * 从DB中读取数据之后放入DataHolder中
 * 读取全部或按照id读取部分数据
 * 
 * @author IceAsh
 *
 */
@Service("dbLoader")
public class DBLoader {
	
	SchemaDao schemaDao;
	AuthDao authDao;
	FaqDao faqDao;
	
	/**
	 * 读取全部数据
	 */
	public void loadAll(){
		//scheams
		List<Schema> allSchemas = schemaDao.loadAll();
		SCHEMAS = allSchemas;
		
		//authorities
		List<Authority> allAuths = authDao.loadAll();
		for (Authority auth : allAuths) {
			if(!AUTHORITIES.containsKey(auth.getDomain())){
				AUTHORITIES.put(auth.getDomain(), new ArrayList<String>());
			}
			AUTHORITIES.get(auth.getDomain()).add(auth.getSchemasName());
		}
		
		//
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
