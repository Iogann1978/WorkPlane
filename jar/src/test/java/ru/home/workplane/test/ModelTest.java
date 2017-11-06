package ru.home.workplane.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.enums.ProjectStates;
import ru.home.workplane.model.Book;
import ru.home.workplane.model.Bug;
import ru.home.workplane.model.Content;
import ru.home.workplane.model.Diary;
import ru.home.workplane.model.Log;
import ru.home.workplane.model.Organization;
import ru.home.workplane.model.Paragraph;
import ru.home.workplane.model.Project;
import ru.home.workplane.model.Skill;
import ru.home.workplane.model.User;
import ru.home.workplane.service.Service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class ModelTest {
	
	@Test
	public void testModel() {
		ApplicationContext context = new ClassPathXmlApplicationContext("WorkPlaneTest.xml");
		Service<User> userService = (Service<User>) context.getBean("userService");
		assertNotNull(userService);
		Service<Skill> skillService = (Service<Skill>) context.getBean("skillService");
		assertNotNull(skillService);
		Service<Book> bookService = (Service<Book>) context.getBean("bookService");
		assertNotNull(bookService);
		Service<Diary> diaryService = (Service<Diary>) context.getBean("diaryService");
		assertNotNull(diaryService);
		Service<Project> projectService = (Service<Project>) context.getBean("projectService");
		assertNotNull(projectService);
		Service<Organization> organizationService = (Service<Organization>) context.getBean("organizationService");
		assertNotNull(organizationService);
		Service<Bug> bugService = (Service<Bug>) context.getBean("bugService");
		assertNotNull(bugService);
		Service<Log> logService = (Service<Log>) context.getBean("logService");
		assertNotNull(logService);
		
		User user = new User();
		user.setLogin("admin");
		String hexDigest = Beans.getHashPass("123456");
		user.setPassHash(hexDigest);
		userService.insert(user);
		
		List<Skill> skillList = new ArrayList<>();
		skillList.addAll(Arrays.asList(new Skill("Java", user), new Skill("JavaScript", user), 
				new Skill("Delphi", user),	new Skill("C++", user), new Skill("C#", user), 
				new Skill("VBA", user), new Skill("SQL", user)));
		for(Skill skill : skillList) {
			skillService.insert(skill);
		}
		user.setSkillList(skillList.stream().collect(Collectors.toSet()));
		
		List<Book> bookList = new ArrayList<>();
		bookList.addAll(Arrays.asList(new Book("Java for beginner", 120, "none", "none", 1995, true, user),
				new Book("Delphi for beginner", 260, "none", "none", 2005, false, user)));		
		for(Book book : bookList) {
			bookService.insert(book);
		}
		Set<Skill> skillSet1 = new HashSet<>();
		skillSet1.add(skillList.get(0));
		bookList.get(0).setSkillList(skillSet1);
		bookList.get(0).setContent(getContent());
		Set<Skill> skillSet2 = new HashSet<>();
		skillSet2.add(skillList.get(2));
		bookList.get(1).setSkillList(skillSet2);
		bookList.get(1).setContent(getContent());
		for(Book book : bookList) {
			bookService.update(book);
		}
		user.setBookList(bookList.stream().collect(Collectors.toSet()));
				
		List<Diary> diaryList = new ArrayList<>();
		diaryList.addAll(Arrays.asList(new Diary("Запись 1", new Date(), "<html><body><h1>Запись 1</h1></body></html>", user),
				new Diary("Запись 2", new Date(), "<html><body><h1>Запись 2</h1></body></html>", user)));
		for(Diary diary : diaryList) {
			diaryService.insert(diary);
		}
		diaryList.get(0).setSkillList(skillSet1);
		diaryList.get(1).setSkillList(skillSet2);
		for(Diary diary : diaryList) {
			diaryService.update(diary);
		}
		user.setDiaryList(diaryList.stream().collect(Collectors.toSet()));
				
		List<Organization> orgList = new ArrayList<>();
		orgList.addAll(Arrays.asList(new Organization("Банк Старый Кремль", new Date(), new Date(), user),
				new Organization("Рост Банк", new Date(), new Date(), user)));
		for(Organization org : orgList) {
			organizationService.insert(org);
		}
		
		List<Project> prjList = new ArrayList<>();
		prjList.addAll(Arrays.asList(new Project("Проект 1", new Date(), new Date(), "Проект 1", 30, ProjectStates.PLANING, orgList.get(0)),
				new Project("Проект 2", new Date(), new Date(), "Проект 2", 70, ProjectStates.RELEASE, orgList.get(1))));
		for(Project prj : prjList) {
			projectService.insert(prj);
		}
		prjList.get(0).setBugList(Arrays.asList(new Bug("Баг1", true, prjList.get(0)), 
				new Bug("Баг2", false, prjList.get(0))).stream().collect(Collectors.toSet()));
		prjList.get(0).setLogList(Arrays.asList(new Log(new Date(), new Date(), "Лог 1", prjList.get(0)),
				new Log(new Date(), new Date(), "Лог 2", prjList.get(0))).stream().collect(Collectors.toSet()));
		prjList.get(0).setObstacleList(diaryList.stream().collect(Collectors.toSet()));
		prjList.get(0).setSkillList(skillSet1);
		prjList.get(1).setBugList(Arrays.asList(new Bug("Баг3", false, prjList.get(1)), 
				new Bug("Баг4", true, prjList.get(1))).stream().collect(Collectors.toSet()));
		prjList.get(1).setLogList(Arrays.asList(new Log(new Date(), new Date(), "Лог 3", prjList.get(1)),
				new Log(new Date(), new Date(), "Лог 4", prjList.get(1))).stream().collect(Collectors.toSet()));
		prjList.get(1).setObstacleList(diaryList.stream().collect(Collectors.toSet()));
		prjList.get(1).setSkillList(skillSet2);
		for(Project prj : prjList) {
			for(Bug bug : prj.getBugList()) {
				bugService.insert(bug);
			}
			for(Log log : prj.getLogList()) {
				logService.insert(log);
			}
			projectService.update(prj);
		}
		
		orgList.get(0).setProjectList(prjList.stream().collect(Collectors.toSet()));
		orgList.get(1).setProjectList(prjList.stream().collect(Collectors.toSet()));
		for(Organization org : orgList) {
			organizationService.update(org);
		}
		user.setOrganizationList(orgList.stream().collect(Collectors.toSet()));
		
		userService.update(user);
		
		User nuser = userService.findById(user.getId());
		assertNotNull(nuser);
		assertTrue(nuser.getBookList().size() > 0);
		assertTrue(nuser.getDiaryList().size() > 0);
		assertTrue(nuser.getSkillList().size() > 0);
		assertTrue(nuser.getOrganizationList().size() > 0);
		
		assertTrue(nuser.getLogin().equals("admin"));
		String nhexDigest = Beans.getHashPass("123456");
		assertTrue(nuser.getPassHash().equals(nhexDigest));
		
		Skill tempSkill = new Skill("test", user);
		skillService.insert(tempSkill);
		user.getSkillList().add(tempSkill);
		userService.update(user);
		skillService.delete(tempSkill);
		user.getSkillList().remove(tempSkill);
		userService.update(user);
		Skill findSkill = skillService.findById(tempSkill.getId());
		assertNull(findSkill);
		
		((ClassPathXmlApplicationContext)context).close();
	}

	@Test
	public void testXML() {
		String str = getContent();
		System.out.println(str);
		Content xml = new Content();
		xml.unmarshal(str);
		assertTrue(xml.getParagraphList().size() == 2);
	}
	
	private String getContent() {
		Content content = new Content();
		List<Paragraph> paragraphList = new ArrayList<>();
		paragraphList.addAll(Arrays.asList(new Paragraph("1", "Параграф 1", 1), new Paragraph("2", "Параграф 2", 20)));
		List<Paragraph> childs1 = new ArrayList<>();
		childs1.addAll(Arrays.asList(new Paragraph("1.1", "Параграф 1.1", 2), new Paragraph("1.2", "Параграф 1.2", 3)));		
		paragraphList.get(0).setParagraphList(childs1.stream().collect(Collectors.toSet()));
		List<Paragraph> childs2 = new ArrayList<>();
		childs2.addAll(Arrays.asList(new Paragraph("2.1", "Параграф 2.1", 12), new Paragraph("2.2", "Параграф 2.2", 13)));		
		paragraphList.get(1).setParagraphList(childs2.stream().collect(Collectors.toSet()));
		content.setParagraphList(paragraphList.stream().collect(Collectors.toSet()));
		
		String str = content.marshal();
		return str;
	}
}
