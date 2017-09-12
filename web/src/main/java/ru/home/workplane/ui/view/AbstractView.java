package ru.home.workplane.ui.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.util.Tools;

public abstract class AbstractView extends HorizontalLayout implements View  {
	private static final long serialVersionUID = 1L;
	protected Button btnAdd, btnEdit, btnDel, btnExit;

	public AbstractView (String title) {
		super();
		setCaption(title);
		
		VerticalLayout buttonsLayout = new VerticalLayout();
		btnAdd = new Button("Добавить");
		btnEdit = new Button("Изменить");
		btnDel = new Button("Удалить");
		btnExit = new Button("Выйти");
		
		btnAdd.setWidth(Tools.BUTTON_WIDTH);
		btnAdd.setIcon(VaadinIcons.PLUS);
		btnEdit.setWidth(Tools.BUTTON_WIDTH);
		btnEdit.setIcon(VaadinIcons.EDIT);
		btnDel.setWidth(Tools.BUTTON_WIDTH);
		btnDel.setIcon(VaadinIcons.MINUS);
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
}
