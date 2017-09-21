package ru.home.workplane.ui.view;

import java.util.Set;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.NumberRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.SkillWindow;

public class SkillPage extends AbstractView {
	private static final long serialVersionUID = 1L;
	private Grid<Skill> grid;

	public SkillPage() {
		super("Опыт");
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.INSERT)));
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.DELETE)));
	}

	@Override
	protected Component getCentral() {
		grid = new Grid<>();
		grid.setCaption("Список навыков");
		grid.setWidth("100%");
		grid.setHeight("100%");
		grid.addColumn(Skill::getTitle).setCaption("Опыт");
		grid.addColumn(Skill::getProjectCount).setCaption("Число связанных проектов").setRenderer(new NumberRenderer());
		grid.addColumn(Skill::getDiaryCount).setCaption("Число связанных записей").setRenderer(new NumberRenderer());
		return grid;
	}

	@Override
	protected Component getExtraButtons() {
		return null;
	}
	
	@Override
	public void beforeClientResponse(boolean initial) {
		super.beforeClientResponse(initial);
		Set<Skill> list = Beans.getCurrentUser().getSkillList();
		grid.setItems(list);
		grid.getDataProvider().refreshAll();
	}
}
