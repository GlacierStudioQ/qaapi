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
public class FaqEntry implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;// 自增id，主键
    
    @Column(name = "f_question")
    private String question;// 问题

    @Column(name = "f_answer")
    private String answer;// 答案

}

