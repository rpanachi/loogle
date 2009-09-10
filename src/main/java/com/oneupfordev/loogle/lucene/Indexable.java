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
	
	void setHighlighter(Highlighter highlighter);

	//Document getDocument();
}
