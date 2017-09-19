package ru.home.workplane.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.NumberRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Skill;
import ru.home.workplane.model.User;
import ru.home.workplane.service.Service;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.SkillWindow;

public class SkillPage extends AbstractView {
	private static final long serialVersionUID = 1L;

	public SkillPage() {
		super("Опыт");

		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.INSERT)));
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.DELETE)));		
	}

	@Override
	protected Component getCentral() {
		Grid<Skill> grid = new Grid<>();
		grid.setCaption("Список навыков");
		grid.setWidth("100%");
		grid.setHeight("100%");
		List<Skill> list = new ArrayList<>();
		list.add(new Skill("Java"));
		//Set<Skill> list = getItems();
		grid.setItems(list);
		grid.addColumn(Skill::getTitle).setCaption("Опыт");
		grid.addColumn(Skill::getProjectCount).setCaption("Число связанных проектов").setRenderer(new NumberRenderer());
		grid.addColumn(Skill::getDiaryCount).setCaption("Число связанных записей").setRenderer(new NumberRenderer());
		return grid;
	}

	@Override
	protected Component getExtraButtons() {
		return null;
	}
	
	private Set<Skill> getItems() {
		Beans beans = new Beans();
		Service<User> userService = beans.getUserService();
		return userService.findById(0L).getSkillList();
	}
}
