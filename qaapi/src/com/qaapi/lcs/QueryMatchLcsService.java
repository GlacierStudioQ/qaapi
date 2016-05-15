package com.qaapi.lcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.qaapi.bean.FaqEntry;
import com.qaapi.memorydb.SchemaNotExistException;
import com.qaapi.util.MatchDegreeAndId;

import static com.qaapi.memorydb.DataHolder.*;

public class QueryMatchLcsService {

	private static int matchCount = 3;

	public static List<FaqEntry> queryMatch(String schemaName, String question) throws SchemaNotExistException {

		// 检验schema正确性
		Map<Long, FaqEntry> entriesMap = FAQ_ENTRIES.get(schemaName);
		if (entriesMap == null) {
			throw new SchemaNotExistException(schemaName);
		}

		// 存放匹配值最大的id
		List<MatchDegreeAndId> mdis = new ArrayList<MatchDegreeAndId>();
		
		// 遍历寻找lcs占较长问题的比例较大的
		List<FaqEntry> entries = new ArrayList<FaqEntry>(entriesMap.values());
		for(FaqEntry entry : entries){
			String standerQuestion = entry.getQuestion();
			String longQuestion = standerQuestion.length() > question.length() ?
											standerQuestion : question;
			int lcsLength = FindLcs.find(question, standerQuestion);
			double matchDegree = (double)lcsLength / (double)longQuestion.length();
			
			MatchDegreeAndId mdi = new MatchDegreeAndId(matchDegree, entry.getId());
			MatchDegreeAndId.insertIntoMDIS(mdi, mdis, matchCount);
		}

		List<FaqEntry> returnEntries = new ArrayList<FaqEntry>(matchCount);
		for (int i = 0; i < matchCount; i++) {
			returnEntries.add(entriesMap.get(mdis.get(i).id));
		}
		return returnEntries;
		
	}

}
