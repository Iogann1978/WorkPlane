package ru.home.workplane.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import ru.home.workplane.model.User;

@Repository
public class UserService extends AbstractService<User> {
	
	public UserService() {
		super(User.class);
	}
	
	@Override
	public Set<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> find(String param) {
		List<User> list = em.createNamedQuery("User.find", User.class).setParameter(1, param).getResultList();
		return list.stream().collect(Collectors.toSet());
	}
}
