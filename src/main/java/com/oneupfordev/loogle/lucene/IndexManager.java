/**
 * 
 */
package com.oneupfordev.loogle.lucene;

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.index.Term;
//import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

/**
 * IndexManager
 */
public class IndexManager {
	
	public static final Analyzer BRAZILIAN_ANALYZER = new BrazilianAnalyzer();
	public static final File INDEX_DIR = new File("index");
	
	protected static IndexReader getReader() throws Exception {
		return IndexReader.open(INDEX_DIR);
	}
	
	protected static IndexWriter getWriter() throws Exception {
		return new IndexWriter(INDEX_DIR,
				BRAZILIAN_ANALYZER , !INDEX_DIR.exists() || !INDEX_DIR.canRead(),
				IndexWriter.MaxFieldLength.LIMITED);
	}
	
	public static <T> boolean index(Indexable<T> objIndexable) {

		List<Indexable<T>> list = new ArrayList<Indexable<T>>();
		list.add(objIndexable);
		return IndexManager.index(list);

	}
	
	public static <T> boolean index(List<? extends Indexable<T>> objects) {
		
		try {
			
			IndexWriter writer = getWriter();

			for (Indexable<T> objIndexable : objects) {
				Document docToIndex = new Document();
				for (Field field : objIndexable.getIndexFields()) {
					docToIndex.add(field);
				}
				writer.addDocument(docToIndex);
			}
			writer.commit();
			writer.optimize();
			writer.close();
			
			return true;

		} catch (Exception ex) {
			throw new RuntimeException("Não foi possível indexar: " + ex.getMessage(), ex);
		}
	}

	public static <T> SearchResult<T> execute(final SearchOptions<T> options) {
		
		try {
			long start = System.currentTimeMillis();
			
			QueryParser parser = new MultiFieldQueryParser(options.getFields(), BRAZILIAN_ANALYZER);
			Query query = parser.parse(options.getTerms());

			Highlighter highlighter = buildHighlighter(query);
			options.getIndexable().setHighlighter(highlighter);
			
			IndexReader reader = getReader();
			Searcher searcher = new IndexSearcher(reader);
			
			TopDocs topDocs = searcher.search(query, null, options.getMaxResults(), options.getSort());
			
			SearchResult<T> result = new SearchResult<T>();
			result.setOccurrences(topDocs.totalHits);
			
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				T model = options.getIndexable().loadModel(document);
				result.addResult(model);
			}

			//TODO TRATAR PAGINACAO
			//result.setPaginas(Double.valueOf(Math.floor(docs.totalHits / pesquisa.getPorPagina())).intValue());
			//result.setPaginaAtual(pesquisa.getPagina());
			//int registroInicial = (pesquisa.getPagina() - 1) * pesquisa.getPorPagina();
			//int registroFinal = registroInicial + pesquisa.getPorPagina();
			//if (registroFinal > docs.totalHits)	registroFinal = docs.totalHits;
			
			//for(int i = registroInicial; i < registroFinal; i++) {
			//	result.addResultado(searcher.doc(docs.scoreDocs[i].doc));
			//}
			
			long end = System.currentTimeMillis();
			result.setDuration(end - start);
			
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public static void remove(Serializable idObject) {
		
		try {
			IndexWriter writer = getWriter();
			writer.deleteDocuments(new Term("id", idObject.toString()));
			writer.commit();
			writer.close();
		} catch (Exception ex) {
			throw new RuntimeException("Não foi possível indexar: " + ex.getMessage(), ex);
		}
	}	

	public static boolean dropIndex() {
		try {
			return deleteDir(INDEX_DIR);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	private static Highlighter buildHighlighter(final Query query) {
		Scorer scorer = new QueryScorer(query);
		Formatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");
		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(new SimpleFragmenter(50));
		return highlighter;
	}
	
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    } 

}
