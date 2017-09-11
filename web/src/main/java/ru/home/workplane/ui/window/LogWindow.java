package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class LogWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private DateField dateStart, dateEnd;
	private TextField textDescription;

	public LogWindow(WinMode mode) {
		super(mode, "запись");	
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
		dateEnd = new DateField("Дата окончания");
		dateEnd.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateEnd.setWidth("50%");
		datesLayout.addComponents(dateStart, dateEnd);
		datesLayout.setSizeFull();
		dateStart.setSizeFull();
		dateEnd.setSizeFull();
		
		TextArea textDescription = new TextArea("Описание записи");
		textDescription.setWidth("100%");
		textDescription.setHeight("295px");
				
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
}
