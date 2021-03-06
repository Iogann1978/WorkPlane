package ru.home.workplane.ui.window;

import java.util.Set;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.home.workplane.model.Skill;
import ru.home.workplane.util.Tools;

public class DiaryFilterWindow extends AbstractWindow<Skill> {
	private static final long serialVersionUID = 1L;
	private ComboBox<Skill> cmbSkill;
	private Set<Skill> items;

	public DiaryFilterWindow(Set<Skill> items) {
		super();
		setCaption("Фильтр по тэгам");
		setHeight("150px");
		setWidth(Tools.SHORT_WIDTH);
		setIcon(VaadinIcons.FILTER);
		this.items = items;
	}

	@Override
	protected Component getCentral() {
		cmbSkill = new ComboBox<>();
		cmbSkill.setWidth("100%");
		cmbSkill.setTextInputAllowed(false);
		items.add(new Skill("Все записи", null));
		cmbSkill.setItems(items);
		cmbSkill.setEmptySelectionCaption("Все записи");
		return cmbSkill;
	}

	@Override
	protected void setDeleteMode() {
	}

	@Override
	protected Skill getItem() {
		return cmbSkill.getValue();
	}
}
