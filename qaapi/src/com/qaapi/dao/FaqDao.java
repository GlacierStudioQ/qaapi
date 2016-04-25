package com.qaapi.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qaapi.bean.FaqEntry;

@Repository("faqDao")
public class FaqDao extends BaseDao {
	
	static final String ENTITY_NAME= "FaqEntry";
	
	public void delete(Serializable id){
		super.delete(ENTITY_NAME, id);
	}
	
	public FaqEntry getById(Serializable id){
		return (FaqEntry) super.getById(ENTITY_NAME, id);
	}
	
	public List<FaqEntry> findByProperty(String paramName,Object paramValue){
		return (List<FaqEntry>) super.findByProperty(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<FaqEntry> findByProperty(String paramName,
			Object paramValue, int firstResult, int maxResults) {
		return (List<FaqEntry>) super.findByProperty(ENTITY_NAME, paramName, paramValue, firstResult, maxResults);
	}
	
	public List<FaqEntry> findByPropertyFuzzy(String paramName,Object paramValue){
		return (List<FaqEntry>) super.findByPropertyFuzzy(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<FaqEntry> loadAll() {
		return (List<FaqEntry>) super.loadAll(ENTITY_NAME);
	}

	public List<FaqEntry> loadAll(String paramName, int firstResult, int maxResults) {
		return (List<FaqEntry>) super.loadAll(ENTITY_NAME, paramName, firstResult, maxResults);
	}
	
	
}
