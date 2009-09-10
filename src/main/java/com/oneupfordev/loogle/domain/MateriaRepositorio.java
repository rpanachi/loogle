/**
 * 
 */
package com.oneupfordev.loogle.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oneupfordev.loogle.lucene.IndexManager;

/**
 * Repositório de Matérias
 */
@Repository
@Transactional
public class MateriaRepositorio {

	private JpaTemplate persistence;
	
	@PersistenceContext
	public void setPersistence(EntityManager entityManager) {
		this.persistence = new JpaTemplate(entityManager);
	}
	public JpaTemplate getPersistence() {
		return persistence;
	}	
	
	public Materia get(int id) {
		return persistence.find(Materia.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Materia> all() {
		return persistence.find("from Materia");
	}
	
	public Materia save(Materia materia) {
		if (materia.getId() == null) {
			persistence.persist(materia);
		} else {
			persistence.merge(materia);
		}
		indexarMateria(materia);
		return materia;
	}

	public void loadSample() {
		//textos retirados de: http://www.lerolero.com/
		List<String> textos = new ArrayList<String>();
		textos.add("Assim mesmo, a valorização de fatores subjetivos aponta para a melhoria dos relacionamentos verticais entre as hierarquias.");
		textos.add("Não obstante, a contínua expansão de nossa atividade oferece uma interessante oportunidade para verificação de todos os recursos funcionais envolvidos.");
		textos.add("Desta maneira, o julgamento imparcial das eventualidades garante a contribuição de um grupo importante na determinação dos modos de operação convencionais.");

		textos.add("Neste sentido, a valorização de fatores subjetivos estimula a padronização das diversas correntes de pensamento.");
		textos.add("Evidentemente, a mobilidade dos capitais internacionais cumpre um papel essencial na formulação dos relacionamentos verticais entre as hierarquias.");
		textos.add("Neste sentido, a mobilidade dos capitais internacionais causa impacto indireto na reavaliação do fluxo de informações.");

		textos.add("Desta maneira, a contínua expansão de nossa atividade maximiza as possibilidades por conta das diretrizes de desenvolvimento para o futuro.");
		textos.add("A prática cotidiana prova que a crescente influência da mídia agrega valor ao estabelecimento do remanejamento dos quadros funcionais.");
		textos.add("Neste sentido, o entendimento das metas propostas maximiza as possibilidades por conta das formas de ação.");

		textos.add("Não obstante, a constante divulgação das informações exige a precisão e a definição do levantamento das variáveis envolvidas.");
		textos.add("Não obstante, a contínua expansão de nossa atividade oferece uma interessante oportunidade para verificação das posturas dos órgãos dirigentes com relação às suas atribuições.");
		textos.add("É importante questionar o quanto a expansão dos mercados mundiais cumpre um papel essencial na formulação dos conhecimentos estratégicos para atingir a excelência.");

		int i = 1;
		for (String texto : textos) {
			Materia materia = new Materia();
			materia.setAutor("Gerente " + i);
			materia.setData(new Date());
			materia.setTitulo("Lero lero " + i);
			materia.setTexto(texto);
			
			save(materia);
			
			try {
			Thread.sleep(1000);
			} catch (Exception ex) {}
			
			i++;
		}
	}

	private void indexarMateria(final Materia materia) {

		MateriaIndex indexObj = new MateriaIndex(materia);
		IndexManager.index(indexObj);
	}

}
