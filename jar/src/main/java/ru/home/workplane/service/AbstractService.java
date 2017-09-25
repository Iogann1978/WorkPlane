package ru.home.workplane.service;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<T> implements Service<T> {
	@PersistenceContext
	protected EntityManager em;
	private Class<T> entityClass;
	
	public AbstractService(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	@Transactional(value = "txm", propagation = Propagation.REQUIRED)
	public void insert(T entity) {
		em.persist(entity);
	}

	@Override
	@Transactional(value = "txm", propagation = Propagation.REQUIRED)
	public T update(T entity) {
		return em.merge(entity);
	}

	@Override
	@Transactional(value = "txm", propagation = Propagation.REQUIRED)
	public void delete(T entity) {
		em.remove(em.merge(entity));
	}

	@Override
	@Transactional(value = "txm", propagation = Propagation.REQUIRED, readOnly = true)
	public T findById(long id) {
		return em.find(entityClass, id);
	}

	@Override
	@Transactional(value = "txm", propagation = Propagation.REQUIRED, readOnly = true)
	public abstract Set<T> findAll();

	@Override
	public abstract Set<T> find(String param);

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
