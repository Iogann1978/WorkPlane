package ru.home.workplane.service;

import java.util.Set;

public interface Service<T> {
	public void insert(T entity);
	public T update(T entity);
	public void delete(T entity);
	public T findById(long id);
	public Set<T> findAll();
	public Set<T> find(String param);
}