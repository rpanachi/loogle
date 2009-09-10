/**
 * 
 */
package com.oneupfordev.loogle.domain;

import java.io.IOException;
import java.io.StringReader;

import net.sourceforge.stripes.integration.spring.SpringHelper;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.search.highlight.Highlighter;

import com.oneupfordev.loogle.lucene.IndexManager;
import com.oneupfordev.loogle.lucene.Indexable;

/**
 * MateriaIndex
 */
public class MateriaIndex implements Indexable<Materia> {
	
	private MateriaRepositorio repositorio;
	private Materia materia;
	private Highlighter highlighter;
	
	public MateriaIndex() {
		this(new Materia());
	}
	public MateriaIndex(Materia materia) {
		this.materia = materia;
	}
	
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setRepositorio(MateriaRepositorio repositorio) {
		this.repositorio = repositorio;
	}
	public MateriaRepositorio getRepositorio() {
		return repositorio;
	}
	public void setHighlighter(Highlighter highlighter) {
		this.highlighter = highlighter;
	}
	public Highlighter getHighlighter() {
		return highlighter;
	}

	public Field[] getIndexFields() {
		return new Field[] {
				new Field("id", materia.getId().toString(), Store.YES, Index.NO),
				new Field("autor", materia.getAutor(), Store.NO, Index.NOT_ANALYZED),
				new Field("titulo", materia.getTitulo(), Store.NO, Index.ANALYZED),
				new Field("texto", materia.getTexto(), Store.NO, Index.ANALYZED),
				new Field("data", materia.getData().toString(), Store.NO, Index.NOT_ANALYZED)
		};
	}
	
	public Materia loadModel(Document document) {
		//UM ALTERNATIVA SERIA ARMAZENAR O CONTEUDO DO DOCUMENT
		//PARA CRIAR UMA INSTANCIA SEM NECESSIDADE DE CONSULTAR O BD
		Materia materia = this.repositorio.get(Integer.parseInt(document.get("id")));
		processHighlight(materia);
		return materia; 
	}
	
	private void processHighlight(Materia materia) {

		if (getHighlighter() == null) {
			return;
		}

		try {
			String highlightedText = highlightText("texto", materia.getTexto());
			if (!highlightedText.isEmpty()) {
				materia.setTexto(highlightedText);
			}
			highlightedText = highlightText("titulo", materia.getTitulo());
			if (!highlightedText.isEmpty()) {
				materia.setTitulo(highlightedText);
			}
			highlightedText = highlightText("autor", materia.getAutor());
			if (!highlightedText.isEmpty()) {
				materia.setAutor(highlightedText);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private String highlightText(final String fieldName, final String fieldValue) throws IOException {
		TokenStream tokenStream = IndexManager.BRAZILIAN_ANALYZER.tokenStream(fieldName, new StringReader(fieldValue));
		String highlightedText = getHighlighter().getBestFragments(tokenStream, fieldValue, 15, "...");
		return highlightedText;
	}

}
