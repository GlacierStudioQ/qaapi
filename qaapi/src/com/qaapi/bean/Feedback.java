package com.qaapi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 用以存储用户对某个问题的反馈
 * 实际上只有用户不满意的问题才会被加在这个反馈里
 * 
 * @author IceAsh
 *
 */
@Entity
@Table(name = "t_feedback")
public class Feedback implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;// 自增id，主键
    
    @Column(name = "f_faq_id")
    private Long faqId;// 被指回答错误的问题的id

    @Column(name = "f_user_question")
    private String userQuestion;// 用户的提问
    
    @Column(name = "f_comment")
    private String comment;// 用户对这个回答添加的评论


    @Column(name = "f_create_time")
    private Date createTime;// 评价时间

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFaqId() {
		return faqId;
	}

	public void setFaqId(Long faqId) {
		this.faqId = faqId;
	}

	public String getUserQuestion() {
		return userQuestion;
	}

	public void setUserQuestion(String userQuestion) {
		this.userQuestion = userQuestion;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	
}

