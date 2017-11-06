package ru.home.workplane.ui.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.User;
import ru.home.workplane.ui.enums.ActionType;
import ru.home.workplane.ui.window.AbstractWindow;
import ru.home.workplane.util.Tools;

public abstract class AbstractView<T> extends HorizontalLayout implements View  {
	private static final long serialVersionUID = 1L;
	private Button btnAdd, btnEdit, btnDel, btnExit;
	private T selectedItem;
	private User user;

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
		
		btnAdd.addClickListener(e -> {
			AbstractWindow<T> window = getInsertWindow();
			window.addCloseListener(event -> {
				if(window.getAction() == ActionType.INSERT) {
					addItem(window.getSelectedItem());
					Beans.getUserService().update(user);
					refresh();
				}
			});
			UI.getCurrent().addWindow(window);
		});
		btnEdit.addClickListener(e -> {
			AbstractWindow<T> window = getUpdateWindow();
			window.addCloseListener(event -> {
				if(window.getAction() == ActionType.UPDATE) {
					removeItem(getSelectedItem());
					addItem(window.getSelectedItem());
					Beans.getUserService().update(user);
					refresh();
				}
			});
			UI.getCurrent().addWindow(window);
		});
		btnDel.addClickListener(e -> {
			AbstractWindow<T> window = getDeleteWindow();
			window.addCloseListener(event -> {
				if(window.getAction() == ActionType.DELETE) {
					removeItem(getSelectedItem());
					Beans.getUserService().update(user);
					refresh();
				}
			});
			UI.getCurrent().addWindow(window);
		});

	}
	
	@Override
	public void beforeClientResponse(boolean initial) {
		super.beforeClientResponse(initial);
		user = Beans.getCurrentUser();
		initData();
	}
	
	protected abstract Component getCentral();
	protected abstract Component getExtraButtons();
	protected abstract void refresh();
	protected abstract T getEmptyItem();
	protected abstract AbstractWindow<T> getInsertWindow();
	protected abstract AbstractWindow<T> getUpdateWindow();
	protected abstract AbstractWindow<T> getDeleteWindow();
	protected abstract void addItem(T item);
	protected abstract void removeItem(T item);
	protected abstract void initData();

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

	public User getUser() {
		return user;
	}
}
