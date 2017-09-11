package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class SkillWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textSkill;
	
	public SkillWindow(WinMode mode) {
		super(mode, "опыт");
		setHeight("180px");
		setWidth(Tools.SHORT_WIDTH);
	}

	@Override
	protected Component getCentral() {
		textSkill = new TextField("Название:");
		textSkill.setWidth("100%");
		return textSkill;
	}

	@Override
	protected void setDeleteMode() {
		textSkill.setEnabled(false);
	}
}
