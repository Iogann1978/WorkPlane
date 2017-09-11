package ru.home.workplane.ui.window;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;

public class SkillSelectWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private ComboBox<Skill> cmbSkill;

	public SkillSelectWindow(WinMode mode) {
		super(mode, "опыт");
		setHeight("150px");
		setWidth("400px");		
	}

	@Override
	protected Component getCentral() {
		ComboBox<Skill> cmbSkill = new ComboBox<>();
		List<Skill> items = new ArrayList<>();
		items.add(new Skill("Java"));
		cmbSkill.setItems(items);
		cmbSkill.setWidth("100%");
		cmbSkill.setSelectedItem(items.get(0));
		cmbSkill.setEmptySelectionAllowed(false);
		cmbSkill.setTextInputAllowed(false);
		return cmbSkill;
	}

	@Override
	protected void setDeleteMode() {
		cmbSkill.setReadOnly(true);
	}
}
