package ru.home.workplane.ui.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.DateRenderer;

import ru.home.workplane.model.Organization;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.SkillWindow;
import ru.home.workplane.util.Tools;

public class OrganizationPage extends AbstractView {
	private static final long serialVersionUID = 1L;

	public OrganizationPage() {
		super("Места работы");
		
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.INSERT)));
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new SkillWindow(WinMode.DELETE)));				
	}

	@Override
	protected Component getCentral() {
		Grid<Organization> grid = new Grid<>();
		grid.setCaption("Список организаций");
		grid.setWidth("100%");
		grid.setHeight("100%");
		List<Organization> list = new ArrayList<>();
		list.add(new Organization("Завод", new Date(), new Date()));
		list.add(new Organization("Банк", new Date(), new Date()));
		grid.setItems(list);
		grid.addColumn(Organization::getName).setCaption("Организация");
		grid.addColumn(Organization::getDateStart).setCaption("Дата поступления").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		grid.addColumn(Organization::getDateEnd).setCaption("Дата увольнения").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		return grid;
	}

	@Override
	protected Component getExtraButtons() {
		return null;
	}
}
