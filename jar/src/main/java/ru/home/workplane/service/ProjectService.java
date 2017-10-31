package ru.home.workplane.service;

import java.util.Set;

import org.springframework.stereotype.Repository;

import ru.home.workplane.model.Project;

@Repository
public class ProjectService extends AbstractService<Project> {

	public ProjectService() {
		super(Project.class);
	}
	
	@Override
	public Set<Project> findAll() {
		return null;
	}

	@Override
	public Set<Project> find(String param) {
		return null;
	}

}
