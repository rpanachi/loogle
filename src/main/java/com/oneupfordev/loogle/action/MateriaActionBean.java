/**
 * 
 */
package com.oneupfordev.loogle.action;

import java.util.Date;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.springframework.stereotype.Controller;

import com.oneupfordev.loogle.domain.Materia;
import com.oneupfordev.loogle.domain.MateriaRepositorio;

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
	
	public Resolution create() {
		materia = new Materia();
		materia.setAutor("Autor Exemplo"); //usuário logado
		materia.setData(new Date()); //data atual
		return new ForwardResolution("/WEB-INF/jsp/materia/edit.jsp");
	}

	public Resolution edit() {
		materia = materias.get(getMateria().getId());
		return new ForwardResolution("/WEB-INF/jsp/materia/edit.jsp");
	}

	public Resolution save() {
		try {
			materias.save(materia);
			return new RedirectResolution(MateriaActionBean.class);
		} catch (Exception ex) {
			getContext().getValidationErrors().addGlobalError(new SimpleError(ex.getMessage()));
			return getContext().getSourcePageResolution();
		}
	}

	public Resolution loadSample() {
		try {
			materias.loadSample();
			getContext().getMessages().add(new SimpleMessage("Amostras carregadas com sucesso."));
			return new RedirectResolution(MateriaActionBean.class).flash(this);
		} catch (Exception ex) {
			getContext().getValidationErrors().addGlobalError(new SimpleError(ex.getMessage()));
			return getContext().getSourcePageResolution();
		}
	}

}
