/**
 * 
 */
package com.oneupfordev.loogle.lucene;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchResult
 */
public class SearchResult <T> {
	
	private int occurrences;
	private long duration;
	private List<T> results;
	
	public SearchResult() {
		results = new ArrayList<T>();
	}
	
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}
	public int getOccurrences() {
		return occurrences;
	}
	public void addResult(T result) {
		this.results.add(result);
	}
	public List<T> getResults() {
		return results;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}

}
