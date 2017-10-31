package ru.home.workplane.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.home.workplane.model.Skill;
import ru.home.workplane.model.User;
import ru.home.workplane.service.Service;

public class Beans {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("WorkPlane.xml");
	private static User currentUser = null;
	
	public static Service<User> getUserService() {
		return (Service<User>)context.getBean("userService");
	}
	
	public static <T> void insert(T entity) {
		Service<T> service = (Service<T>)context.getBean(entity.getClass());
		service.insert(entity);
	}

	public static <T> void update(T entity) {
		Service<T> service = (Service<T>)context.getBean(entity.getClass());
		service.update(entity);
	}
	
	public static <T> void delete(T entity) {
		Service<T> service = (Service<T>)context.getBean(entity.getClass());
		service.delete(entity);
	}
	
	public static boolean login(String login, String pass) {
		Service<User> userService = getUserService();
		Set<User> userList = userService.find(login);
		if(userList == null || userList.size() == 0) {
			return false;
		}
		
		String hexDigest = getHashPass(pass);
		Iterator<User> iter = userList.iterator();
		while(iter.hasNext()) {
			User user = iter.next();
			if(user.getLogin().equals(login) && user.getPassHash().equals(hexDigest)) {
				currentUser = user;
				return true;
			}
		}
		return false;
	}
	
	public static String getHashPass(String pass) {
		MessageDigest messageDigest = null;
		byte[] digest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
	        messageDigest.reset();
	        messageDigest.update(pass.getBytes());
	        digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return DatatypeConverter.printHexBinary(digest);
	}

	public static User getCurrentUser() {
		return currentUser;
	}
}
