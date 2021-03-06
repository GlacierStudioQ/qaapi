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
 * 问题/答案
 * 
 * @author IceAsh
 *
 */
@Entity
@Table(name = "t_faq_entry")
public class FaqEntry implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;// 自增id，主键
    
    @Column(name = "f_question")
    private String question = "没有此问题";// 问题

    @Column(name = "f_answer")
    private String answer = "未查询到结果";// 答案

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}

