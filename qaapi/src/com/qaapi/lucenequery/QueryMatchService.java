package com.qaapi.lucenequery;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.qaapi.memorydb.SchemaNotExistException;

import static com.qaapi.lucenequery.SearcherInstances.SEARCHERS;
import static com.qaapi.lucenequery.DocumentsLoader.FIELD_ID;
import static com.qaapi.lucenequery.DocumentsLoader.FIELD_NAME;;

/**
 * 
 * 此类用于提供给Actiond调用，搜索匹配的结果
 * 
 * @author IceAsh
 *
 */
@Service("queryMatchService")
public class QueryMatchService {
	
	// 要输出的结果数目
	private static int matchCount = 1;
	
	public static List<Long> queryMatch(String schemaName, String question) throws SchemaNotExistException {
		
		IndexSearcher searcher = SEARCHERS.get(schemaName);
		if(searcher == null){
			throw new SchemaNotExistException();
		}

		// 使用QueryParser查询分析器构造Query对象
		// query对象需要知道自己要在什么样的“域名”里查找，所以也需要fieldName
		Analyzer analyzer = new IKAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_40, FIELD_NAME, analyzer);
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		
		// 存放匹配结果id的List
		List<Long> answerIds = new ArrayList<Long>();
		try {
			Query query = qp.parse(question);
			System.out.println("lucene => 分词结果：" + query.toString());
			
			// 搜索相似度最高的记录
			TopDocs topDocs;
			topDocs = searcher.search(query, matchCount);
		
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document targetDoc = searcher.doc(scoreDoc.doc);
				answerIds.add(Long.parseLong(targetDoc.get(FIELD_ID)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return answerIds;
	}
}
