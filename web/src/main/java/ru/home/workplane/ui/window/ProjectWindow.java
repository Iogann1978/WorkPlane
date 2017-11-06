package ru.home.workplane.ui.window;

import java.sql.Date;
import java.time.ZoneId;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.enums.ProjectStates;
import ru.home.workplane.model.Organization;
import ru.home.workplane.model.Project;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ProjectWindow extends AbstractWindow<Project> {
	private static final long serialVersionUID = 1L;
	private TextField textName;
	private DateField dateStart, dateEnd;
	private ComboBox<ProjectStates> cmbState;
	private ComboBox<Organization> cmbOrg;
	private NumericLayout percentLayout;

	public ProjectWindow(Project selectedItem, WinMode mode) {
		super(selectedItem, mode, "проект");
		setHeight(Tools.SHORT_WIDTH);
		setWidth("410px");
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textName = new TextField("Название проекта");
		textName.setWidth("100%");
		textName.setValue(getSelectedItem().getName());
		
		cmbOrg = new ComboBox<>();
		cmbOrg.setCaption("Организация");
		cmbOrg.setItems(Beans.getCurrentUser().getOrganizationList());
		cmbOrg.setTextInputAllowed(false);
		cmbOrg.setEmptySelectionAllowed(false);
		cmbOrg.setWidth("100%");
		cmbOrg.setSelectedItem(getSelectedItem().getOrganization());		
		
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
		
		percentLayout = new NumericLayout("Процентов выполнено");
		percentLayout.setValue(getSelectedItem().getPercent());
		
		cmbState = new ComboBox<>();
		cmbState.setCaption("Состояние проекта");
		cmbState.setItems(ProjectStates.values());
		cmbState.setTextInputAllowed(false);
		cmbState.setEmptySelectionAllowed(false);
		cmbState.setItemIconGenerator((item) -> Tools.getResource(item));
		cmbState.setWidth("100%");
		cmbState.setSelectedItem(getSelectedItem().getState());

		layout.addComponents(textName, datesLayout, percentLayout, cmbState);	
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textName.setReadOnly(true);
		dateStart.setReadOnly(true);
		dateEnd.setReadOnly(true);
		cmbState.setReadOnly(true);
		percentLayout.setDeleteMode();
	}

	@Override
	protected Project getItem() {
		Project selectedItem = getSelectedItem();
		selectedItem.setName(textName.getValue());
		selectedItem.setDateStart(Date.valueOf(dateStart.getValue()));
		selectedItem.setDateEnd(Date.valueOf(dateEnd.getValue()));
		selectedItem.setPercent(percentLayout.getValue());
		selectedItem.setState(cmbState.getValue());
		selectedItem.setOrganization(cmbOrg.getValue());
		return selectedItem;
	}

}
