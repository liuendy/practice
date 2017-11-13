package com.ybwh.lucene7;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.junit.Test;

public class IndexSimpleTest {
	private static String indexPath = "E:\\github\\practice\\lucene7\\index";

	@Test
	public void testCreateIndex() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(indexPath, new String[0]));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir, iwc);

		Document doc = new Document();
		IntPoint id = new IntPoint("id", 1);
		StringField title = new StringField("title", "鸟哥的linux私房菜", Store.YES);
		TextField keyword = new TextField("keyword", "鸟哥  linux 入门    书籍", Store.YES);
		LongPoint createTime = new LongPoint("createTime", new Date().getTime());

		doc.add(id);
		doc.add(title);
		doc.add(keyword);
		doc.add(createTime);

		writer.addDocument(doc);
		writer.commit();
		writer.close();
		dir.close();

	}

	@Test
	public void testRead() {
		try {
			Directory dir = FSDirectory.open(Paths.get(indexPath, new String[0]));

			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new StandardAnalyzer();
			QueryBuilder queryBuilder = new QueryBuilder(analyzer);
			Query query = queryBuilder.createPhraseQuery("keyword", "linux");
			ScoreDoc[] scoreDocs = searcher.search(query, 10);
			
			System.out.println(topDocs.totalHits);
			
			
			if(null !=  topDocs.scoreDocs){
				for ( ScoreDoc doc : topDocs.scoreDocs) {
					System.out.println(doc);
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}
