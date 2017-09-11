package ru.home.workplane.ui.window;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Organization;
import ru.home.workplane.util.Tools;

public class ProjectReportWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;

	public ProjectReportWindow() {
		super();
		setCaption("Отчет по проектам");
		setWidth(Tools.SHORT_WIDTH);
		setHeight("250px");
	}
	
	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		
		HorizontalLayout datesLayout = new HorizontalLayout();
		DateField dateStart = new DateField("Дата начала");
		dateStart.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateStart.setWidth("50%");
		DateField dateEnd = new DateField("Дата окончания");
		dateEnd.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateEnd.setWidth("50%");
		datesLayout.addComponents(dateStart, dateEnd);
		
		ComboBox<Organization> cmbOrganization = new ComboBox<>("Организации");
		cmbOrganization.setWidth("100%");
		cmbOrganization.setEmptySelectionAllowed(false);
		cmbOrganization.setTextInputAllowed(false);
		Set<Organization> items = new HashSet<>();
		items.add(new Organization("Банк", new Date(), new Date()));
		cmbOrganization.setItems(items);
		cmbOrganization.setEmptySelectionAllowed(true);
		cmbOrganization.setEmptySelectionCaption("Все организации");		

		layout.addComponents(datesLayout, cmbOrganization);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
	}

}
