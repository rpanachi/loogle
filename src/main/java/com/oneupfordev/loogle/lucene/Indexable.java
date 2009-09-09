/**
 * 
 */
package com.oneupfordev.loogle.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.lucene.search.highlight.Highlighter;

/**
 * Indexavel
 */
public interface Indexable <T> {

	Field[] getIndexFields();
	
	T loadModel(Document document);
	
	T processHighlight(Highlighter highligh, T model);
	
	
}
