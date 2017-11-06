package ru.home.workplane.ui.window;

import java.sql.Date;
import java.time.ZoneId;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Log;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class LogWindow extends AbstractWindow<Log> {
	private static final long serialVersionUID = 1L;
	private DateField dateStart, dateEnd;
	private TextArea textDescription;

	public LogWindow(Log selectedItem, WinMode mode) {
		super(selectedItem, mode, "запись");	
		setWidth("500px");
		setHeight("500px");
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		
		HorizontalLayout datesLayout = new HorizontalLayout();
		dateStart = new DateField("Дата начала");
		dateStart.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateStart.setWidth("50%");
		dateStart.setValue(getSelectedItem().getDateStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateEnd = new DateField("Дата окончания");
		dateEnd.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateEnd.setWidth("50%");
		dateEnd.setValue(getSelectedItem().getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		datesLayout.addComponents(dateStart, dateEnd);
		datesLayout.setSizeFull();
		dateStart.setSizeFull();
		dateEnd.setSizeFull();
		
		textDescription = new TextArea("Описание записи");
		textDescription.setWidth("100%");
		textDescription.setHeight("295px");
		textDescription.setValue(getSelectedItem().getDescription());
				
		layout.addComponents(datesLayout, textDescription);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		dateStart.setReadOnly(true);
		dateEnd.setReadOnly(true);
		textDescription.setReadOnly(true);
	}

	@Override
	protected Log getItem() {
		Log selectedItem = getSelectedItem();
		selectedItem.setDateStart(Date.valueOf(dateStart.getValue()));
		selectedItem.setDateEnd(Date.valueOf(dateEnd.getValue()));
		selectedItem.setDescription(textDescription.getValue());
		return selectedItem;
	}
}
