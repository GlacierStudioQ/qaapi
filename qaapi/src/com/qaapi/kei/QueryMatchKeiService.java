package com.qaapi.kei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qaapi.bean.FaqEntry;
import com.qaapi.lucenequery.ParseKeyword;
import com.qaapi.memorydb.SchemaNotExistException;
import com.qaapi.util.MatchDegreeAndId;

import static com.qaapi.memorydb.DataHolder.*;

public class QueryMatchKeiService {

	private static int matchCount = 1;

	public static List<FaqEntry> queryMatch(String schemaName, String question) throws Exception {
	
		// 检验schema正确性
		Map<Long, List<String>> wordElementsMap = FAQ_ENTRIES_PARSED.get(schemaName);
		if (wordElementsMap == null) {
			throw new SchemaNotExistException(schemaName);
		}
		Map<Long, FaqEntry> entriesMap = FAQ_ENTRIES.get(schemaName);

		// 存放匹配值最大的id
		List<MatchDegreeAndId> mdis = new ArrayList<MatchDegreeAndId>();
		
		// 遍历寻找lcs占较长问题的比例较大的
		ParseKeyword parseKeyword = new ParseKeyword();
		for(Entry<Long, List<String>> entry : wordElementsMap.entrySet()){
			Long id = entry.getKey();
			
			List<String> standerQuestionElement = entry.getValue();
			List<String> questionElement = parseKeyword.getWordElements(question);
			
			List<String> longQuestionElement = standerQuestionElement.size() > questionElement.size() ?
											standerQuestionElement : questionElement;
			int keiLength = FindKei.findInList(questionElement, standerQuestionElement);
			double matchDegree = (double)keiLength / (double)longQuestionElement.size();
			
			MatchDegreeAndId mdi = new MatchDegreeAndId(matchDegree, id);
			MatchDegreeAndId.insertIntoMDIS(mdi, mdis, matchCount);
		}
		
		// 转化为entry List
		List<FaqEntry> returnEntries = new ArrayList<FaqEntry>(matchCount);
		for (int i = 0; i < matchCount; i++) {
			returnEntries.add(entriesMap.get(mdis.get(i).id));
		}
		return returnEntries;
		
	}
}