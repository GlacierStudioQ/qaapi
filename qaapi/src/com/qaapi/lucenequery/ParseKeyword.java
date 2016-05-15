package com.qaapi.lucenequery;

import static com.qaapi.lucenequery.DocumentsLoader.FIELD_NAME;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ParseKeyword {
	
	QueryParser qp = null;
	Analyzer analyzer = null;
	
	public ParseKeyword() {
		initQueryPaser();
	}
	
	private void initQueryPaser(){
		analyzer = new IKAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_40, FIELD_NAME, analyzer);
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		this.qp = qp;
	}
	
	//==============================================
	
	/**
	 * 得到一个用于搜索的query
	 * @param keyword
	 * @return
	 * @throws ParseException
	 */
	public Query getQuery(String keyword) throws ParseException{
		if(qp == null){
			initQueryPaser();
		}
		return qp.parse(keyword);
	}
	
	/**
	 * 得到一句话的语素
	 * 
	 * @param keyword
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public List<String> getWordElements(String keyword) throws ParseException, IOException{
		TokenStream stream = analyzer.tokenStream(null, new StringReader(keyword));
		stream.reset();
        CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
        List<String> result = new ArrayList<String>();
        while(stream.incrementToken()) {
            result.add(term.toString());
        }
        return result;
	}
	
	//======================================================
	
	public static void main(String[] args) throws ParseException, IOException {
		ParseKeyword parseKeyword = new ParseKeyword();
		System.out.println(parseKeyword.getWordElements("何杨带领宿舍人去网吧"));
		System.out.println(parseKeyword.getWordElements("龙哥在东莞嫖娼被抓，民警发现杨哥是幕后真凶"));
	}
	
}
