/**
 * 
 */
package com.oneupfordev.loogle.infra;

import org.apache.lucene.document.Document;

/**
 * Resultado
 */
public class Resultado {

	private int ocorrencias;
	private int paginas;
	private Document[] documentos;
	private long duracao;
	
	public void setOcorrencias(int ocorrencias) {
		this.ocorrencias = ocorrencias;
	}
	public int getOcorrencias() {
		return ocorrencias;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setDocumentos(Document[] documentos) {
		this.documentos = documentos;
	}
	public Document[] getDocumentos() {
		return documentos;
	}
	public void setDuracao(long duracao) {
		this.duracao = duracao;
	}
	public long getDuracao() {
		return duracao;
	}
	
	
	public boolean proximos() {
		return true;
	}

	public static Resultado NENHUM_ENCONTRADO = new Resultado();
}
