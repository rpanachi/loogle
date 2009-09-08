/**
 * 
 */
package com.oneupfordev.loogle.infra;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Sort;

/**
 * Pesquisa
 */
public class Pesquisa {

	private String termos;
	private Analyzer analyser;
	private ModoPesquisa modoPesquisa;
	private String[] fields;
	private Filter filter;
	private Sort sort;
	private int maxResults;
	private int pagina;
	private int porPagina;
	
	public Pesquisa() {
		analyser = new StandardAnalyzer(new String[] {
				"de", "e", "a", "o", "do", "da", "nas", "em", "nos", "dos"
		});
		modoPesquisa = ModoPesquisa.FULL_TEXT_SEARCH;
		maxResults = 100;
		pagina = 1;
		porPagina = 10;
	}
	
	public String getTermos() {
		return this.termos;
	}
	public void setTermos(String termos) {
		this.termos = termos;
	}
	public void setFields(String... fields) {
		this.fields = fields;
	}
	public String[] getFields() {
		return this.fields;
	}
	public Analyzer getAnalyser() {
		return analyser;
	}
	public void setAnalyser(Analyzer analyser) {
		this.analyser = analyser;
	}
	
	public enum ModoPesquisa {
		FULL_TEXT_SEARCH,
		EXACT_PHRASE
	}
	public Filter getFilter() {
		return filter;
	}
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPorPagina(int porPagina) {
		this.porPagina = porPagina;
	}
	public int getPorPagina() {
		return porPagina;
	}
	
}
