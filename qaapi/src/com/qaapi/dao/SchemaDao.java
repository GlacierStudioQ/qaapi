package com.qaapi.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qaapi.bean.Schema;

@Repository("schemaDao")
public class SchemaDao extends BaseDao {
	
	static final String ENTITY_NAME= "Schema";
	
	public void delete(Serializable id){
		super.delete(ENTITY_NAME, id);
	}
	
	public Schema getById(Serializable id){
		return (Schema) super.getById(ENTITY_NAME, id);
	}
	
	public List<Schema> findByProperty(String paramName,Object paramValue){
		return (List<Schema>) super.findByProperty(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<Schema> findByProperty(String paramName,
			Object paramValue, int firstResult, int maxResults) {
		return (List<Schema>) super.findByProperty(ENTITY_NAME, paramName, paramValue, firstResult, maxResults);
	}
	
	public List<Schema> findByPropertyFuzzy(String paramName,Object paramValue){
		return (List<Schema>) super.findByPropertyFuzzy(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<Schema> loadAll() {
		return (List<Schema>) super.loadAll(ENTITY_NAME);
	}

	public List<Schema> loadAll(String paramName, int firstResult, int maxResults) {
		return (List<Schema>) super.loadAll(ENTITY_NAME, firstResult, maxResults);
	}
	
	
}
