/**
 * 
 */
package com.oneupfordev.loogle.infra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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

	public static final File INDEX_DIR = new File("index");

	public static final Analyzer DEFAULT_ANALYZER = new BrazilianAnalyzer(new String[] {
				"de", "e", "a", "o", "do", "da", "nas", "em", "nos", "dos"
		});

	public static void indexar(Field... fields) {

		try {
			
			IndexWriter writer = new IndexWriter(INDEX_DIR,
					DEFAULT_ANALYZER , !INDEX_DIR.exists(),
					IndexWriter.MaxFieldLength.LIMITED);

			Document doc = new Document();
			for (Fieldable field : fields) {
				doc.add(field);
			}

			writer.addDocument(doc);
			writer.commit();
			//writer.optimize();
			writer.close();

		} catch (Exception ex) {
			throw new RuntimeException("Não foi possível indexar: " + ex.getMessage(), ex);
		}
	}

	public static Resultado pesquisar(Pesquisa pesquisa) {
		
		try {

			long start = System.currentTimeMillis();
			
			QueryParser parser = new MultiFieldQueryParser(pesquisa.getFields(), DEFAULT_ANALYZER);
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
				documentos[pos++] = searcher.doc(docs.scoreDocs[i].doc);
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
