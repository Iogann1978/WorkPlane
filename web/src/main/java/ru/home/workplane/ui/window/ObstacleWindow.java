package ru.home.workplane.ui.window;

import java.util.Set;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.home.workplane.model.Diary;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ObstacleWindow extends AbstractWindow<Diary> {
	private static final long serialVersionUID = 1L;
	private ComboBox<Diary> cmbDiary;
	private Set<Diary> items;
	
	public ObstacleWindow(Set<Diary> items, WinMode mode) {
		super(null, mode, "решение");
		setWidth(Tools.SHORT_WIDTH);
		setHeight("250px");
		this.items = items;
	}

	@Override
	protected Component getCentral() {
		cmbDiary = new ComboBox<>("Запись");
		cmbDiary.setWidth("100%");
		cmbDiary.setEmptySelectionAllowed(false);
		cmbDiary.setTextInputAllowed(false);
		cmbDiary.setItems(items);
		return cmbDiary;
	}

	@Override
	protected void setDeleteMode() {
		cmbDiary.setReadOnly(true);
	}

	@Override
	protected Diary getItem() {
		return cmbDiary.getValue();
	}

}
