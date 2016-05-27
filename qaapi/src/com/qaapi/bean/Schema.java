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
 * 用以存储当前系统共含有那些数据库
 * 
 * @author IceAsh
 *
 */
@Entity
@Table(name = "t_schema")
public class Schema implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;// 自增id，主键
    
    @Column(name = "f_name")
    private String name;// 当前所拥有的数据库的名字


    @Column(name = "f_nickname")
    private String nickname;// 当前所拥有的数据库的别名

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
    
}

