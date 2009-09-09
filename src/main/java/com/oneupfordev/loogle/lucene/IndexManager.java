/**
 * 
 */
package com.oneupfordev.loogle.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * IndexManager
 */
public class IndexManager {
	
	private static Analyzer BRAZILIAN_ANALYZER = new BrazilianAnalyzer();
	private static File INDEX_DIR = new File("index");
	
	public static boolean index(Indexable objIndexable) {
		return true;
	}

	public static <T> IndexSearch<T> query(Indexable objIndexable, String query) {
		return new IndexSearch<T>();
	}
	
}
