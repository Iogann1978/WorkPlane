package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class SkillWindow extends AbstractWindow<Skill> {
	private static final long serialVersionUID = 1L;
	private TextField textSkill;
	private Skill selectedItem; 
	
	public SkillWindow(Skill selectedItem, WinMode mode) {
		super(mode, "опыт");
		setHeight("180px");
		setWidth(Tools.SHORT_WIDTH);
		this.selectedItem = selectedItem; 
	}

	@Override
	protected Component getCentral() {
		textSkill = new TextField("Название:");
		textSkill.setWidth("100%");
		textSkill.setValue(selectedItem.getTitle());
		return textSkill;
	}

	@Override
	protected void setDeleteMode() {
		textSkill.setEnabled(false);
	}

	@Override
	protected Skill getItem() {
		selectedItem.setTitle(textSkill.getValue());
		return selectedItem;
	}
}
