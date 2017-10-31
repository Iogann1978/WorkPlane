package ru.home.workplane.ui.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.util.Tools;

public abstract class AbstractView<T> extends HorizontalLayout implements View  {
	private static final long serialVersionUID = 1L;
	protected Button btnAdd, btnEdit, btnDel, btnExit;
	private T selectedItem;

	public AbstractView (String title) {
		super();
		setCaption(title);
		selectedItem = null;
		
		VerticalLayout buttonsLayout = new VerticalLayout();
		btnAdd = new Button("Добавить");
		btnEdit = new Button("Изменить");
		btnDel = new Button("Удалить");
		btnExit = new Button("Выйти");
		
		btnAdd.setWidth(Tools.BUTTON_WIDTH);
		btnAdd.setIcon(VaadinIcons.PLUS);
		btnEdit.setWidth(Tools.BUTTON_WIDTH);
		btnEdit.setIcon(VaadinIcons.EDIT);
		btnEdit.setEnabled(false);
		btnDel.setWidth(Tools.BUTTON_WIDTH);
		btnDel.setIcon(VaadinIcons.MINUS);
		btnDel.setEnabled(false);
		btnExit.setWidth(Tools.BUTTON_WIDTH);
		btnExit.setIcon(VaadinIcons.HOME);
		btnExit.addClickListener(e -> getUI().getNavigator().navigateTo("start"));
		buttonsLayout.addComponents(btnAdd, btnEdit, btnDel);
		Component extraLayout = getExtraButtons();
		if(extraLayout != null) {
			buttonsLayout.addComponent(extraLayout);
		}
		buttonsLayout.addComponent(btnExit);
		buttonsLayout.setWidth("200px");

		Component c = getCentral();
		addComponents(buttonsLayout, c);
		setExpandRatio(c, 1.0f);		
		setSizeFull();
	}
	
	
	protected abstract Component getCentral();
	protected abstract Component getExtraButtons();
	protected abstract void refresh();


	public T getSelectedItem() {
		return selectedItem;
	}


	public void setSelectedItem(T selectedItem) {
		this.selectedItem = selectedItem;
		if(selectedItem == null) {
			btnEdit.setEnabled(false);
			btnDel.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
			btnDel.setEnabled(true);
		}
	} 
}
