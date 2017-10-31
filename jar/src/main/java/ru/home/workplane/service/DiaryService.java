package ru.home.workplane.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import ru.home.workplane.model.Diary;

@Repository
public class DiaryService extends AbstractService<Diary> {

	public DiaryService() {
		super(Diary.class);
	}
	
	@Override
	public Set<Diary> findAll() {
		List<Diary> list = em.createNamedQuery("Diary.findAll", Diary.class).getResultList();
		return list.stream().collect(Collectors.toSet());
	}

	@Override
	public Set<Diary> find(String param) {
		List<Diary> list = em.createNamedQuery("Diary.find", Diary.class).setParameter(1, param).getResultList();
		return list.stream().collect(Collectors.toSet());
	}
}
