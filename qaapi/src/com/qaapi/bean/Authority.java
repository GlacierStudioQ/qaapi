package com.qaapi.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 用以存储哪个用户可以访问哪个数据库
 * 
 * @author IceAsh
 *
 */
@Entity
@Table(name = "t_authority")
public class Authority implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;// 自增id，主键
    
    @Column(name = "f_domain")
    private String domain;// 当前访问的用户的名字

    @Column(name = "f_schemas_name")
    private String schemasName;// 当前用户可以访问的数据库名

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSchemasName() {
		return schemasName;
	}

	public void setSchemasName(String schemasName) {
		this.schemasName = schemasName;
	}

}

