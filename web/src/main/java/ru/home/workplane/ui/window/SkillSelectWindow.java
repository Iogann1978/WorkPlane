package ru.home.workplane.ui.window;

import java.util.Set;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class SkillSelectWindow extends AbstractWindow<Skill> {
	private static final long serialVersionUID = 1L;
	private ComboBox<Skill> cmbSkill;

	public SkillSelectWindow(WinMode mode) {
		super(null, mode, "опыт");
		setHeight("150px");
		setWidth(Tools.SHORT_WIDTH);
	}

	@Override
	protected Component getCentral() {
		cmbSkill = new ComboBox<>();
		cmbSkill.setWidth("100%");
		cmbSkill.setEmptySelectionAllowed(true);
		cmbSkill.setEmptySelectionCaption("Не выбрано");
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

	public void setItems(Set<Skill> items) {
		cmbSkill.setItems(items);
	}	
}
