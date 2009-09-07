/**
 * 
 */
package com.oneupfordev.loogle.infra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;

/**
 * LuceneUtil
 */
public class LuceneUtil {

	static final File INDEX_DIR = new File("index");

	public static void indexar(Class clazz, Field[] fields) {

		try {

			IndexWriter writer = new IndexWriter(INDEX_DIR,
					new StandardAnalyzer(), !INDEX_DIR.exists(),
					IndexWriter.MaxFieldLength.LIMITED);

			Document doc = new Document();
			for (Fieldable field : fields) {
				doc.add(field);
			}

			writer.addDocument(doc);
			// writer.optimize();
			writer.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Document[] pesquisar(String[] fields, String termos)
			throws Exception {

		IndexReader reader = IndexReader.open(INDEX_DIR);

		Searcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		QueryParser parser = new MultiFieldQueryParser(fields, analyzer);

		Query query = parser.parse(termos);

		TopDocs docs = searcher.search(query, 50);

		List<Document> resultList = new ArrayList<Document>();

		for (ScoreDoc score : docs.scoreDocs) {
			Document doc = searcher.doc(score.doc);
			resultList.add(doc);
		}

		return resultList.toArray(new Document[resultList.size()]);
	}

	public static Resultado pesquisar(Pesquisa pesquisa) {
		
		try {

			long start = System.currentTimeMillis();
			
			QueryParser parser = new MultiFieldQueryParser(pesquisa.getFields(), pesquisa.getAnalyser());
			Query query = parser.parse(pesquisa.getTermos());

			IndexReader reader = IndexReader.open(INDEX_DIR);
			Searcher searcher = new IndexSearcher(reader);

			TopDocs docs = searcher.search(query, pesquisa.getMaxResults());

			Resultado result = new Resultado();
			result.setOcorrencias(docs.totalHits);
			result.setPaginas(Double.valueOf(Math.floor(docs.totalHits / pesquisa.getPorPagina())).intValue());
			//result.setPaginaAtual(pesquisa.getPagina());
			
			int registroInicial = (pesquisa.getPagina() - 1) * pesquisa.getPorPagina();
			int registroFinal = registroInicial + pesquisa.getPorPagina();
			if (registroFinal > docs.totalHits)	registroFinal = docs.totalHits;
			
			int registrosAtuais = registroFinal - registroInicial; 
			
			Document[] documentos = new Document[registrosAtuais];
			int pos = 0;
			for(int i = registroInicial; i < registroFinal; i++) {
				documentos[pos++] = searcher.doc(i);				
			}
			result.setDocumentos(documentos);
			
			long end = System.currentTimeMillis();
			result.setDuracao(end - start);
			
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();
			return Resultado.NENHUM_ENCONTRADO;
		}
	}

}
