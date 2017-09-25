package ru.home.workplane.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import ru.home.workplane.model.Project;

@Repository
public class ProjectService extends AbstractService<Project> {

	public ProjectService() {
		super(Project.class);
	}
	
	@Override
	public Set<Project> findAll() {
		String queryName = "Diary.findAll";
		List<Project> list = em.createNamedQuery(queryName, Project.class).getResultList();
		return list.stream().collect(Collectors.toSet());
	}

	@Override
	public Set<Project> find(String param) {
		String queryName = "Diary.find";
		List<Project> list = em.createNamedQuery(queryName, Project.class).setParameter(1, param).getResultList();
		return list.stream().collect(Collectors.toSet());
	}

}
