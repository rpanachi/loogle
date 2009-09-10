/**
 * 
 */
package com.oneupfordev.loogle.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.springframework.stereotype.Controller;

import com.oneupfordev.loogle.domain.Materia;
import com.oneupfordev.loogle.domain.MateriaIndex;
import com.oneupfordev.loogle.domain.MateriaRepositorio;
import com.oneupfordev.loogle.lucene.IndexManager;
import com.oneupfordev.loogle.lucene.SearchOptions;
import com.oneupfordev.loogle.lucene.SearchResult;

/**
 * PesquisaActionBean
 */
@Controller
@UrlBinding("/pesquisa.htm")
public class PesquisaActionBean extends BaseActionBean {

	@SpringBean
	private MateriaRepositorio repositorio;
	
	private String query;
	private Integer pagina = 1;
	private SearchResult<Materia> resultado;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getPagina() {
		return pagina;
	}
	public SearchResult<Materia> getResultado() {
		return resultado;
	}
	public void setResultado(SearchResult<Materia> resultado) {
		this.resultado = resultado;
	}
	
	@DefaultHandler
	public Resolution index() {
		return new ForwardResolution("/WEB-INF/jsp/pesquisa/index.jsp");
	}

	public Resolution pesquisa() throws Exception {
		
	
		MateriaIndex index = new MateriaIndex();
		index.setRepositorio(repositorio);
		
		SearchOptions<Materia> options = new SearchOptions<Materia>(index);
		options.setTerms(query);
		options.setFields("titulo", "texto", "autor");
		options.setPage(pagina);
		
		SearchResult<Materia> resultado = IndexManager.execute(options);
		setResultado(resultado);
		
		return new ForwardResolution("/WEB-INF/jsp/pesquisa/index.jsp");
	}

}
