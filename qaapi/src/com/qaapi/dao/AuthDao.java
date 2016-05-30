package com.qaapi.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qaapi.bean.Authority;

@Repository("authDao")
public class AuthDao extends BaseDao {
	
	static final String ENTITY_NAME= "Authority";
	
	public void delete(Serializable id){
		super.delete(ENTITY_NAME, id);
	}
	
	public Authority getById(Serializable id){
		return (Authority) super.getById(ENTITY_NAME, id);
	}
	
	public List<Authority> findByProperty(String paramName,Object paramValue){
		return (List<Authority>) super.findByProperty(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<Authority> findByProperty(String paramName,
			Object paramValue, int firstResult, int maxResults) {
		return (List<Authority>) super.findByProperty(ENTITY_NAME, paramName, paramValue, firstResult, maxResults);
	}
	
	public List<Authority> findByPropertyFuzzy(String paramName,Object paramValue){
		return (List<Authority>) super.findByPropertyFuzzy(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<Authority> loadAll() {
		return (List<Authority>) super.loadAll(ENTITY_NAME);
	}

	public List<Authority> loadAll(String paramName, int firstResult, int maxResults) {
		return (List<Authority>) super.loadAll(ENTITY_NAME, firstResult, maxResults);
	}
	
	
}
