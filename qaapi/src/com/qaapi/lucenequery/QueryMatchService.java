package com.qaapi.lucenequery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import com.qaapi.bean.FaqEntry;
import com.qaapi.memorydb.SchemaNotExistException;

import static com.qaapi.lucenequery.SearcherInstances.SEARCHERS;
import static com.qaapi.lucenequery.DocumentsLoader.FIELD_ID;
import static com.qaapi.memorydb.DataHolder.FAQ_ENTRIES;

/**
 * 
 * 此类用于提供给Action调用，搜索匹配的结果
 * 
 * @author IceAsh
 *
 */
@Service("queryMatchService")
public class QueryMatchService {
	
	// 要输出的结果数目
	private static int matchCount = 1;
	
	public static List<FaqEntry> queryMatch(String schemaName, String question) throws SchemaNotExistException {
		
		IndexSearcher searcher = SEARCHERS.get(schemaName);
		if(searcher == null){
			throw new SchemaNotExistException(schemaName);
		}
		Map<Long, FaqEntry> entriesMap = FAQ_ENTRIES.get(schemaName);

		
		// 存放匹配结果id的List
		List<FaqEntry> returnEntries = new ArrayList<FaqEntry>();
		try {
			Query query = (new ParseKeyword()).getQuery(question);
			System.out.println("lucene => 分词结果：" + query.toString());
			
			// 搜索相似度最高的记录
			TopDocs topDocs;
			topDocs = searcher.search(query, matchCount);
		
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document targetDoc = searcher.doc(scoreDoc.doc);
				returnEntries.add(entriesMap.get(Long.parseLong(targetDoc.get(FIELD_ID))));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnEntries;
	}
}
