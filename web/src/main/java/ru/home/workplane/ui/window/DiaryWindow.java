package ru.home.workplane.ui.window;

import java.sql.Date;
import java.time.ZoneId;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Diary;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class DiaryWindow extends AbstractWindow<Diary> {
	private static final long serialVersionUID = 1L;
	private DateField dateDiary;
	private TextField titleDiary;

	public DiaryWindow(Diary selectedItem, WinMode mode) {
		super(selectedItem, mode, "запись");		
		setHeight("250px");
		setWidth(Tools.LONG_WIDTH);
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		dateDiary = new DateField();
		dateDiary.setCaption("Дата записи");
		dateDiary.setDateFormat(Tools.LONG_DATE_FORMAT);
		dateDiary.setValue(getSelectedItem().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		titleDiary = new TextField();
		titleDiary.setCaption("Заголовок записи");
		titleDiary.setWidth("100%");
		titleDiary.setValue(getSelectedItem().getTitle());
		layout.addComponents(dateDiary, titleDiary);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		titleDiary.setReadOnly(true);
		dateDiary.setReadOnly(true);
	}

	@Override
	protected Diary getItem() {
		Diary selectedItem = getSelectedItem();
		selectedItem.setTitle(titleDiary.getValue());
		selectedItem.setDate(Date.valueOf(dateDiary.getValue()));
		return selectedItem;
	}
}
