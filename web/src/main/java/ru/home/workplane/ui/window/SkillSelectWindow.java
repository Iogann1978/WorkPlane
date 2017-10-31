package ru.home.workplane.ui.window;

import java.util.Set;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class SkillSelectWindow extends AbstractWindow<Skill> {
	private static final long serialVersionUID = 1L;
	private ComboBox<Skill> cmbSkill;
	private Skill selectedItem;

	public SkillSelectWindow(Skill selectedItem, WinMode mode) {
		super(mode, "опыт");
		setHeight("150px");
		setWidth(Tools.SHORT_WIDTH);		
	}

	@Override
	protected Component getCentral() {
		cmbSkill = new ComboBox<>();
		Set<Skill> items = Beans.getCurrentUser().getSkillList();
		cmbSkill.setItems(items);
		cmbSkill.setWidth("100%");
		cmbSkill.setSelectedItem(selectedItem);
		cmbSkill.setEmptySelectionAllowed(false);
		cmbSkill.setTextInputAllowed(false);
		return cmbSkill;
	}

	@Override
	protected void setDeleteMode() {
		cmbSkill.setReadOnly(true);
	}

	@Override
	protected Skill getItem() {
		return cmbSkill.getValue();
	}
}
