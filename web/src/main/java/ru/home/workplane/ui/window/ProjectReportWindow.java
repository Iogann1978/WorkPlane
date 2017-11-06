package ru.home.workplane.ui.window;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Organization;
import ru.home.workplane.model.ProjectReport;
import ru.home.workplane.util.Tools;

public class ProjectReportWindow extends AbstractWindow<ProjectReport> {
	private static final long serialVersionUID = 1L;
	private DateField dateStart, dateEnd;
	private ComboBox<Organization> cmbOrganization;
	private Set<Organization> items;

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
		dateStart = new DateField("Дата начала");
		dateStart.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateStart.setWidth("50%");
		dateEnd = new DateField("Дата окончания");
		dateEnd.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateEnd.setWidth("50%");
		datesLayout.addComponents(dateStart, dateEnd);
		
		cmbOrganization = new ComboBox<>("Организации");
		cmbOrganization.setWidth("100%");
		cmbOrganization.setEmptySelectionAllowed(false);
		cmbOrganization.setTextInputAllowed(false);
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

	@Override
	protected ProjectReport getItem() {
		ProjectReport projectReport = new ProjectReport();
		projectReport.setDateStart(Date.valueOf(dateStart.getValue()));
		projectReport.setDateEnd(Date.valueOf(dateEnd.getValue()));
		projectReport.setOrganization(cmbOrganization.getValue());
		return projectReport;
	}

}
