package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import ru.home.workplane.model.Organization;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class OrganizationWindow extends AbstractWindow<Organization> {
	private static final long serialVersionUID = 1L;
	private TextField textOrganization;
	private Organization selectedItem;
	
	public OrganizationWindow(Organization selectedItem, WinMode mode) {
		super(mode, "Место работы");
		setHeight("180px");
		setWidth(Tools.SHORT_WIDTH);
		this.selectedItem = selectedItem;
	}

	@Override
	protected Component getCentral() {
		textOrganization = new TextField("Название:");
		textOrganization.setWidth("100%");
		return textOrganization;
	}

	@Override
	protected void setDeleteMode() {
		textOrganization.setEnabled(false);
	}

	@Override
	protected Organization getItem() {
		selectedItem.setName(textOrganization.getValue());
		return selectedItem;
	}
}