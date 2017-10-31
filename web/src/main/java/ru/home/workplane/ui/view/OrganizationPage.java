package ru.home.workplane.ui.view;

import java.text.SimpleDateFormat;
import java.util.Set;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.DateRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Organization;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.OrganizationWindow;
import ru.home.workplane.util.Tools;

public class OrganizationPage extends AbstractView<Organization> {
	private static final long serialVersionUID = 1L;
	private Grid<Organization> grid;

	public OrganizationPage() {
		super("Места работы");
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new OrganizationWindow(WinMode.INSERT)));
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new OrganizationWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new OrganizationWindow(WinMode.DELETE)));				
	}

	@Override
	protected Component getCentral() {
		grid = new Grid<>();
		grid.setCaption("Список организаций");
		grid.setWidth("100%");
		grid.setHeight("100%");
		grid.addColumn(Organization::getName).setCaption("Организация");
		grid.addColumn(Organization::getDateStart).setCaption("Дата поступления").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		grid.addColumn(Organization::getDateEnd).setCaption("Дата увольнения").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		return grid;
	}

	@Override
	protected Component getExtraButtons() {
		return null;
	}
	
	@Override
	public void beforeClientResponse(boolean initial) {
		super.beforeClientResponse(initial);
		Set<Organization> list = Beans.getCurrentUser().getOrganizationList();
		grid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				setSelectedItem(event.getFirstSelectedItem().get());
			} else {
				setSelectedItem(null);
			}
		});
		grid.setItems(list);
		grid.getDataProvider().refreshAll();
	}

	@Override
	protected void refresh() {
	}	
}
