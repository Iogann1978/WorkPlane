package ru.home.workplane.ui.window;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.home.workplane.model.Skill;
import ru.home.workplane.util.Tools;

public class DiaryFilterWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;

	public DiaryFilterWindow() {
		super();
		setCaption("Фильтр по тэгам");
		setHeight("150px");
		setWidth(Tools.SHORT_WIDTH);
		setIcon(VaadinIcons.FILTER);	
	}

	@Override
	protected Component getCentral() {
		ComboBox<Skill> cmbSkill = new ComboBox<>();
		cmbSkill.setWidth("100%");
		cmbSkill.setTextInputAllowed(false);
		cmbSkill.setEmptySelectionAllowed(false);
		List<Skill> items = new ArrayList<>();
		items.add(new Skill("Все записи"));
		cmbSkill.setItems(items);
		cmbSkill.setSelectedItem(items.get(0));			
		return cmbSkill;
	}

	@Override
	protected void setDeleteMode() {
	}
}
