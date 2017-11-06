package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class SkillWindow extends AbstractWindow<Skill> {
	private static final long serialVersionUID = 1L;
	private TextField textSkill;
	
	public SkillWindow(Skill selectedItem, WinMode mode) {
		super(selectedItem, mode, "опыт");
		setHeight("180px");
		setWidth(Tools.SHORT_WIDTH);
	}

	@Override
	protected Component getCentral() {
		textSkill = new TextField("Название:");
		textSkill.setWidth("100%");
		textSkill.setValue(getSelectedItem().getTitle());
		return textSkill;
	}

	@Override
	protected void setDeleteMode() {
		textSkill.setReadOnly(true);
	}

	@Override
	protected Skill getItem() {
		Skill selectedItem = getSelectedItem(); 
		selectedItem.setTitle(textSkill.getValue());
		return selectedItem;
	}
}
