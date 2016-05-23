package com.qaapi.util;

import java.util.List;

import com.qaapi.bean.FaqEntry;

/**
 * 为了从多种匹配方法中找到最合适的匹配结果的工具
 * @author IceAsh
 *
 */
public class FilterAnswersUtil {
	
	/**
	 * 找到和提问字数最相近的标准问题，作为最匹配
	 * @return
	 */
	public static FaqEntry LengthMaxMatch(List<List<FaqEntry>> answersByAllMethod){
		FaqEntry fitAnswer = new FaqEntry();
		int answerLength = Integer.MAX_VALUE;
		
		for (List<FaqEntry> answersByOneMethod : answersByAllMethod) {
			for (FaqEntry answer : answersByOneMethod) {
				if(answer.getAnswer().length() < answerLength){
					fitAnswer = answer;
					answerLength = answer.getAnswer().length();
				}
			}
		}
		return fitAnswer;
	}
}
