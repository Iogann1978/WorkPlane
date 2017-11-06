package ru.home.workplane.ui.view;

import java.util.HashSet;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.AbstractWindow;
import ru.home.workplane.ui.window.SkillWindow;

public class SkillPage extends AbstractView<Skill> {
	private static final long serialVersionUID = 1L;
	private Grid<Skill> grid;
	private ListDataProvider<Skill> dataProvider;

	public SkillPage() {
		super("Опыт");
	}

	@Override
	protected Component getCentral() {
		grid = new Grid<>();
		dataProvider = new ListDataProvider<>(new HashSet<Skill>());
		grid.setDataProvider(dataProvider);
		
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
	public void initData() {
		dataProvider.getItems().clear();
		dataProvider.getItems().addAll(getUser().getSkillList());
		grid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				setSelectedItem(event.getFirstSelectedItem().get());
			} else {
				setSelectedItem(null);
			}
		});
		grid.setDataProvider(dataProvider);
		refresh();
	}

	@Override
	protected void refresh() {
		dataProvider.refreshAll();
	}

	@Override
	protected Skill getEmptyItem() {
		Skill skill = new Skill();
		skill.setUser(Beans.getCurrentUser());
		return skill;
	}

	@Override
	protected AbstractWindow<Skill> getInsertWindow() {
		SkillWindow skillWindow = new SkillWindow(getEmptyItem(), WinMode.INSERT);
		return skillWindow;
	}

	@Override
	protected AbstractWindow<Skill> getUpdateWindow() {
		SkillWindow skillWindow = new SkillWindow(getSelectedItem(), WinMode.UPDATE);
		return skillWindow;
	}

	@Override
	protected AbstractWindow<Skill> getDeleteWindow() {
		SkillWindow skillWindow = new SkillWindow(getSelectedItem(), WinMode.DELETE);
		return skillWindow;
	}

	@Override
	protected void addItem(Skill item) {
		dataProvider.getItems().add(item);
		getUser().getSkillList().add(item);
	}

	@Override
	protected void removeItem(Skill item) {
		dataProvider.getItems().remove(item);
		getUser().getSkillList().remove(item);
	}
}
