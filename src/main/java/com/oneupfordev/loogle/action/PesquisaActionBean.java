/**
 * 
 */
package com.oneupfordev.loogle.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Controller;

import com.oneupfordev.loogle.infra.LuceneUtil;
import com.oneupfordev.loogle.infra.Pesquisa;
import com.oneupfordev.loogle.infra.Resultado;

/**
 * PesquisaActionBean
 */
@Controller
@UrlBinding("/pesquisa.htm")
public class PesquisaActionBean extends BaseActionBean {

	private String query;
	private Integer numResultados;
	private Document[] resultados;
	private Integer pagina = 1;
	
	private Resultado resultado;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public Integer getNumResultados() {
		return numResultados;
	}
	public void setNumResultados(Integer numResultados) {
		this.numResultados = numResultados;
	}
	public Document[] getResultados() {
		return resultados;
	}
	public void setResultados(Document[] results) {
		this.resultados = results;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getPagina() {
		return pagina;
	}
	public Resultado getResultado() {
		return resultado;
	}
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	
	@DefaultHandler
	public Resolution index() {
		return new ForwardResolution("/WEB-INF/jsp/pesquisa/index.jsp");
	}
	
	@HandlesEvent("pesquisa")
	public Resolution pesquisa() throws Exception {
		
		/*
		Document[] results = LuceneUtil.pesquisar(new String[] {"titulo", "texto"}, query);
		setNumResultados(results.length);
		setResultados(results);
		*/
		
		Pesquisa pesquisa = new Pesquisa();
		pesquisa.setTermos(query);
		pesquisa.setFields("titulo", "texto", "autor");
		pesquisa.setPagina(pagina);
		
		Resultado resultado = LuceneUtil.pesquisar(pesquisa);
		//setNumResultados(resultado.getOcorrencias());
		//setResultados(resultado.getDocumentos());
		
		System.out.println(resultado.getPaginas());
		System.out.println(resultado.getDocumentos());
		
		setResultado(resultado);
		setPagina(2);
		
		return new ForwardResolution("/WEB-INF/jsp/pesquisa/index.jsp");
	}
	
	
}
