package com.qaapi.lucenequery;

import java.util.Map;

import org.apache.lucene.search.IndexSearcher;

/**
 * 
 * 存储每个schema对应的searcher
 * 
 * @author IceAsh
 *
 */
public class SearcherInstances {
	public static Map<String ,IndexSearcher> SEARCHERS;
}
