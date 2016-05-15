package com.qaapi.lucenequery;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.qaapi.bean.FaqEntry;
import com.qaapi.bean.Schema;
import com.qaapi.memorydb.SchemaNotExistException;
import com.qaapi.util.LoaderUtils;

import static com.qaapi.lucenequery.SearcherInstances.SEARCHERS;
import static com.qaapi.memorydb.DataHolder.*;

/**
 * 
 * 调用DataHolder把全部数据输入Lucene中，
 * 之后生成每个Schema对应的Searcher，存入SearcherInstances中
 * 
 * @author IceAsh
 *
 */
public class DocumentsLoader {

	// Lucene Document的“域名”
	public static String FIELD_NAME = "question";
	public static String FIELD_ID = "id";
	
	/**
	 * 只加载一个schema的文档
	 * 实际上就是把一个放进一个list里
	 * 
	 * @param schemaName
	 * @throws SchemaNotExistException 
	 */
	public static void loadOneSchema(String schemaName) throws SchemaNotExistException {
			List<String> schemaList = new ArrayList<String>();
			schemaList.add(schemaName);
			loadList(schemaList);
	}
	/**
	 * 加载一个List中的schema的文档
	 * 如果有schema不存在则会抛出异常
	 * 
	 * @param schemaName
	 * @throws SchemaNotExistException 
	 */
	public static void loadList(List<String> schemaNames) throws SchemaNotExistException {
		if(LoaderUtils.isExistSchemas(schemaNames)){
			List<Schema> schemaList = new ArrayList<Schema>();
			for(Schema schema : SCHEMAS){
				if(schemaNames.contains(schema.getName())){
					schemaList.add(schema);
				}
			}
			load(schemaList);
		}
	}
	
	/**
	 * 加载全部schema的文档
	 */
	public static void loadAll() {
		load(SCHEMAS);
	}
	
	/**
	 * 通用加载文档方法
	 * @param schemaList
	 */
	private static void load(List<Schema> schemaList) {
		if(SEARCHERS == null){
			SEARCHERS = new HashMap<String, IndexSearcher>();
		}
		try {
			
			for (Schema schema : schemaList) {
				String schemaName = schema.getName();
				
				Directory directory = null;
				IndexWriter iwriter = null;
				IndexReader ireader = null;
				IndexSearcher isearcher = null;
				// 建立内存索引对象
				
				// 配置IndexWriterConfig
				// 实例化IKAnalyzer分词器
				/*
				 * 它还有一个构造函数
				 * Analyzer analyzer = new IKAnalyzer(boolean useSmart);
				 * 如果为true，则使用智能切分，否则是最细粒度切分
				 */
				Analyzer analyzer = new IKAnalyzer();
				IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_40, analyzer);
				iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
				
				directory = new RAMDirectory();
				iwriter = new IndexWriter(directory, iwConfig);
				// 写入索引
				
				//--------------------------------------------------------------------------------
				long startTime = System.nanoTime();
				System.out.println("lucene => 加载"+schemaName+"的文档...");
				//--------------------------------------------------------------------------------
				
				// 每个文本域都有确定的“域名”
				Map<Long, FaqEntry> faqEntries = FAQ_ENTRIES.get(schemaName);
				Set<Long> faqEntryIds = faqEntries.keySet();
				for (Long id : faqEntryIds) {
					Document doc = new Document();
					doc.add(new Field(FIELD_NAME, faqEntries.get(id).getQuestion(), Field.Store.YES, Field.Index.ANALYZED));
					doc.add(new Field(FIELD_ID, id.toString(), Field.Store.YES, Field.Index.ANALYZED));
					iwriter.addDocument(doc);
				}
				
				//--------------------------------------------------------------------------------
				long endTime = System.nanoTime();
				DecimalFormat df = new DecimalFormat(",###.00");
				System.out.println("lucene => 加载时间： "+ df.format(endTime-startTime) + "ns");
				//--------------------------------------------------------------------------------			
				
				iwriter.close();
				
				// 实例化搜索器
				ireader = IndexReader.open(directory);
				isearcher = new IndexSearcher(ireader);
				
				// 把搜索器实例添加入容器
				SEARCHERS.put(schemaName, isearcher);
				
				if (directory != null) {
					directory.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
