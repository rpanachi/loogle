/**
 * 
 */
package com.oneupfordev.loogle.action;

import java.util.Date;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.springframework.stereotype.Controller;

import com.oneupfordev.loogle.domain.Materia;
import com.oneupfordev.loogle.domain.MateriaRepositorio;
import com.oneupfordev.loogle.infra.LuceneUtil;

/**
 * MateriaActionBean
 */

@Controller
@UrlBinding("/materias.htm")
public class MateriaActionBean extends BaseActionBean {

	@SpringBean
	private MateriaRepositorio materias;
	
	private Materia materia;
	
	public List<Materia> getMaterias() {
		return materias.all();
	}
	
	public Materia getMateria() {
		return materia;
	}

	@ValidateNestedProperties({
		@Validate(field="titulo", required=true, maxlength=50, on="save"),
		@Validate(field="autor", required=true, maxlength=50, on="save"),
		@Validate(field="texto", required=true, maxlength=5000, on="save")
	})
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	@DefaultHandler
	public Resolution index() {
		return new ForwardResolution("/WEB-INF/jsp/materia/index.jsp");
	}
	
	@HandlesEvent("new")
	public Resolution create() {
		materia = new Materia();
		materia.setAutor("Rodrigo Panachi"); //usuário logado
		materia.setData(new Date()); //data atual
		return new ForwardResolution("/WEB-INF/jsp/materia/edit.jsp");
	}
	
	@HandlesEvent("edit")
	public Resolution edit() {
		materia = materias.get(getMateria().getId());
		return new ForwardResolution("/WEB-INF/jsp/materia/edit.jsp");
	}

	@HandlesEvent("save")
	public Resolution save() {
		try {
			
			materias.save(materia);
			indexarMateria(materia);
			
			return new RedirectResolution(MateriaActionBean.class);
			
		} catch (Exception ex) {
			getContext().getValidationErrors().addGlobalError(new SimpleError(ex.getMessage()));
			return getContext().getSourcePageResolution();
		}
	}
	
	public void indexarMateria(Materia materia) {
		
		LuceneUtil.indexar(Materia.class, new Field[] {
			new Field("id", materia.getId().toString(), Store.NO, Index.NOT_ANALYZED),
			new Field("titulo", materia.getTitulo(), Store.YES, Index.ANALYZED),
			new Field("autor", materia.getAutor(), Store.YES, Index.ANALYZED),
			new Field("data", materia.getData().toString(), Store.YES, Index.NO),
			new Field("texto", materia.getTexto(), Store.COMPRESS, Index.ANALYZED)
		});
		
	}
}
