package ru.home.workplane.ui.view;

import java.text.SimpleDateFormat;
import java.util.HashSet;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.DateRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Organization;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.AbstractWindow;
import ru.home.workplane.ui.window.OrganizationWindow;
import ru.home.workplane.util.Tools;

public class OrganizationPage extends AbstractView<Organization> {
	private static final long serialVersionUID = 1L;
	private Grid<Organization> grid;
	private ListDataProvider<Organization> dataProvider;

	public OrganizationPage() {
		super("Места работы");
	}

	@Override
	protected Component getCentral() {
		grid = new Grid<>();
		dataProvider = new ListDataProvider<>(new HashSet<Organization>());
		grid.setDataProvider(dataProvider);
		
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
	public void initData() {
		dataProvider.getItems().clear();
		dataProvider.getItems().addAll(getUser().getOrganizationList());
		grid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				setSelectedItem(event.getFirstSelectedItem().get());
			} else {
				setSelectedItem(null);
			}
		});
		refresh();
	}

	@Override
	protected void refresh() {
		grid.getDataProvider().refreshAll();
	}

	@Override
	protected Organization getEmptyItem() {
		Organization organization = new Organization();
		organization.setUser(Beans.getCurrentUser());
		return organization;
	}

	@Override
	protected AbstractWindow<Organization> getInsertWindow() {
		OrganizationWindow organizationWindow = new OrganizationWindow(getEmptyItem(), WinMode.INSERT);
		return organizationWindow;
	}

	@Override
	protected AbstractWindow<Organization> getUpdateWindow() {
		OrganizationWindow organizationWindow = new OrganizationWindow(getSelectedItem(), WinMode.UPDATE);
		return organizationWindow;
	}

	@Override
	protected AbstractWindow<Organization> getDeleteWindow() {
		OrganizationWindow organizationWindow = new OrganizationWindow(getSelectedItem(), WinMode.DELETE);
		return organizationWindow;
	}

	@Override
	protected void addItem(Organization item) {
		dataProvider.getItems().add(item);
		getUser().getOrganizationList().add(item);
	}

	@Override
	protected void removeItem(Organization item) {
		dataProvider.getItems().remove(item);
		getUser().getOrganizationList().remove(item);
	}	
}
