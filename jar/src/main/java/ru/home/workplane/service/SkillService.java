package ru.home.workplane.service;

import java.util.Set;

import org.springframework.stereotype.Repository;

import ru.home.workplane.model.Skill;

@Repository
public class SkillService extends AbstractService<Skill> {

	public SkillService() {
		super(Skill.class);
	}
	
	@Override
	public Set<Skill> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Skill> find(String param) {
		// TODO Auto-generated method stub
		return null;
	}

}
