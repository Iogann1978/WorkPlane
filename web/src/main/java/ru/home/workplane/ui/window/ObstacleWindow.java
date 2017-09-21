package ru.home.workplane.ui.window;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Diary;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ObstacleWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textTitle;
	private ComboBox<Diary> cmbDiary;
	
	public ObstacleWindow(WinMode mode) {
		super(mode, "решение");
		setWidth(Tools.SHORT_WIDTH);
		setHeight("250px");
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textTitle = new TextField("Название");
		textTitle.setWidth("100%");
		cmbDiary = new ComboBox<>("Запись");
		cmbDiary.setWidth("100%");
		cmbDiary.setEmptySelectionAllowed(false);
		cmbDiary.setTextInputAllowed(false);
		Diary diary = new Diary("Запись", new Date(), "", null); 
		Set<Diary> items = new HashSet<>();
		items.add(diary);
		cmbDiary.setItems(items);
		cmbDiary.setSelectedItem(diary);
		layout.addComponents(textTitle, cmbDiary);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textTitle.setReadOnly(true);
		cmbDiary.setReadOnly(true);
	}

}
