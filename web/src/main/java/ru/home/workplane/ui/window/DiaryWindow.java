package ru.home.workplane.ui.window;

import java.time.LocalDate;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class DiaryWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private DateField dateDiary;
	private TextField titleDiary;
	private WinMode mode;

	public DiaryWindow(WinMode mode) {
		super(mode, "запись");		
		setHeight("250px");
		setWidth(Tools.LONG_WIDTH);
		this.mode = mode;
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		dateDiary = new DateField();
		dateDiary.setCaption("Дата записи");
		dateDiary.setDateFormat(Tools.LONG_DATE_FORMAT);
		if(mode == WinMode.INSERT) {
			dateDiary.setValue(LocalDate.now());
		}				
		TextField titleDiary = new TextField();
		titleDiary.setCaption("Заголовок записи");
		titleDiary.setWidth("100%");
		layout.addComponents(dateDiary, titleDiary);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		titleDiary.setEnabled(false);
		dateDiary.setEnabled(false);
	}
}
