/**
 * 
 */
package com.oneupfordev.loogle.lucene;


/**
 * IndexSearch
 */
public class SearchOptions <T> {
	
	private Indexable<T> indexable;
	private String terms;
	private String[] fields;
	private String[] highlightFields;
	private int maxResults;
	private int page;
	private int perPage;
	
	public SearchOptions(Indexable<T> indexable) {
		this.indexable = indexable;
		setMaxResults(100);
	}
	
	public Indexable<T> getIndexable() {
		return indexable;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getTerms() {
		return terms;
	}
	public void setFields(String... fields) {
		this.fields = fields;
	}
	public String[] getFields() {
		return fields;
	}
	public void setHighlightFields(String... highlightFields) {
		this.highlightFields = highlightFields;
	}
	public String[] getHighlightFields() {
		return highlightFields;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPage() {
		return page;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getPerPage() {
		return perPage;
	}
}