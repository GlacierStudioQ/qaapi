package com.qaapi.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDao extends HibernateDaoSupport {
	
	static final String ENTITY_PATH= "com.qaapi.bean.";
	
	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory)   
    {   
        super.setSessionFactory(sessionFactory);   
    }
	
	public Serializable save(Object obj){
		return getHibernateTemplate().save(obj);
	}

	public void update(Object obj){
		getHibernateTemplate().update(obj);
	}
	
	public void saveOrUpdate(Object obj){
		getHibernateTemplate().saveOrUpdate(obj);
	}
	
	public void delete(String entityName, Serializable id){
		Object obj = getById(entityName, id);
		getHibernateTemplate().delete(obj);
	}
	
	public Object getById(String entityName,Serializable id){
		try {
			Object obj = getHibernateTemplate().get(ENTITY_PATH+entityName, id);
			return obj;
		}catch(Exception e){
			return null;
		}
	}
	
	public List<?> findByProperty(String entityName,String paramName,Object paramValue){
		String hql = "from " + ENTITY_PATH + entityName + " entity where entity." + paramName + "=?";
		return getHibernateTemplate().find(hql, paramValue);
	}
	
	public List<?> findByProperty(String entityName, String paramName,
			Object paramValue, int firstResult, int maxResults) {
		DetachedCriteria criteria=DetachedCriteria.forEntityName(ENTITY_PATH+entityName);
		criteria.add(Restrictions.eq(paramName, paramValue));				//Restrictions�ഴ��criteria
		return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}
	
	public List<?> findByPropertyFuzzy(String entityName,String paramName,Object paramValue){
		String hql = "from " + ENTITY_PATH + entityName + " entity where entity." + paramName + "like ?";
		return getHibernateTemplate().find(hql, paramValue);
	}
	
	public <T> List<?> findByExample(T exampleEntity){
		return getHibernateTemplate().findByExample(exampleEntity);
	}
	
	public  <T> List<?> findByExample(T exampleEntity,int firstResult,int maxResults){
		return getHibernateTemplate().findByExample(exampleEntity, firstResult, maxResults);
	}
	
	public  <T> List<?> findByHql(String hql){
		return getHibernateTemplate().find(hql);
	}
	
	public List<?> loadAll(String entityName) {
		String hql = "from " + ENTITY_PATH + entityName;
		return getHibernateTemplate().find(hql);
	}

	public List<?> loadAll(String entityName, String paramName, int firstResult, int maxResults) {
	
		DetachedCriteria criteria=DetachedCriteria.forEntityName(ENTITY_PATH + entityName);
		//criteria.add(Restrictions.isNotNull(paramName));//Restrictions类创建criteria
		return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}
	
	
}
