package com.qaapi.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qaapi.bean.Feedback;

@Repository("feedbackDao")
public class FeedbackDao extends BaseDao {
	
	static final String ENTITY_NAME= "Feedback";
	
	public void delete(Serializable id){
		super.delete(ENTITY_NAME, id);
	}
	
	public Feedback getById(Serializable id){
		return (Feedback) super.getById(ENTITY_NAME, id);
	}
	
	public List<Feedback> findByProperty(String paramName,Object paramValue){
		return (List<Feedback>) super.findByProperty(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<Feedback> findByProperty(String paramName,
			Object paramValue, int firstResult, int maxResults) {
		return (List<Feedback>) super.findByProperty(ENTITY_NAME, paramName, paramValue, firstResult, maxResults);
	}
	
	public List<Feedback> findByPropertyFuzzy(String paramName,Object paramValue){
		return (List<Feedback>) super.findByPropertyFuzzy(ENTITY_NAME, paramName, paramValue);
	}
	
	public List<Feedback> loadAll() {
		return (List<Feedback>) super.loadAll(ENTITY_NAME);
	}

	public List<Feedback> loadAll(String paramName, int firstResult, int maxResults) {
		return (List<Feedback>) super.loadAll(ENTITY_NAME, firstResult, maxResults);
	}
	
	public List<Feedback> findByHql(String hql, int firstResult, int maxResults) {
		return (List<Feedback>) super.findByHql(hql, firstResult, maxResults);
	}
}
