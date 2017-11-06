package ru.home.workplane.ui.window;

import java.sql.Date;
import java.time.ZoneId;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Organization;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class OrganizationWindow extends AbstractWindow<Organization> {
	private static final long serialVersionUID = 1L;
	private TextField textOrganization;
	private DateField dateStart, dateEnd;
	
	public OrganizationWindow(Organization selectedItem, WinMode mode) {
		super(selectedItem, mode, "место работы");
		setHeight("250px");
		setWidth(Tools.SHORT_WIDTH);
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textOrganization = new TextField("Название:");
		textOrganization.setWidth("100%");
		textOrganization.setValue(getSelectedItem().getName());
		
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

		layout.addComponents(textOrganization, datesLayout);	
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textOrganization.setReadOnly(true);
		dateStart.setReadOnly(true);
		dateEnd.setReadOnly(true);
	}

	@Override
	protected Organization getItem() {
		Organization selectedItem = getSelectedItem();
		selectedItem.setName(textOrganization.getValue());
		selectedItem.setDateEnd(Date.valueOf(dateEnd.getValue()));
		selectedItem.setDateStart(Date.valueOf(dateStart.getValue()));
		return selectedItem;
	}
}