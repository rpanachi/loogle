/**
 * 
 */
package com.oneupfordev.loogle.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		return materia;
	}
}
