package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.ui.enums.ActionType;
import ru.home.workplane.ui.enums.WinMode;

public abstract class AbstractWindow<T> extends Window {
	private static final long serialVersionUID = 1L;
	protected Button btnOK, btnCancel;
	private VerticalLayout layout;
	private WinMode mode;
	private ActionType action;
	private T selectedItem;
	
	public AbstractWindow() {
		super();
		mode = WinMode.NONE;
		action = ActionType.CANCEL;
		init();
	}
	
	public AbstractWindow(T selectedItem, WinMode mode, String title) {
		super();
		this.mode = mode;
		this.selectedItem = selectedItem; 
		
		switch(mode) {
		case INSERT:
			setCaption("Добавить " + title);
			setIcon(VaadinIcons.PLUS);
			break;
		case UPDATE:
			setCaption("Изменить " + title);
			setIcon(VaadinIcons.EDIT);
			break;
		case DELETE:
			setCaption("Удалить " + title);
			setIcon(VaadinIcons.MINUS);
			break;
		default:
			break;
		}
		
		init();
		if(mode == WinMode.DELETE) {
			setDeleteMode();
		}
	}
	
	private void init() {
		setResizable(false);
		center();
		setModal(true);

		layout = new VerticalLayout();

		HorizontalLayout buttonLayout = new HorizontalLayout();
		btnOK = new Button("OK");
		btnOK.setIcon(VaadinIcons.CHECK);
		btnOK.addClickListener(e -> {
			switch(mode) {
			case INSERT:
				insert();
				action = ActionType.INSERT;
				break;
			case UPDATE:
				update();
				action = ActionType.UPDATE;
				break;
			case DELETE:
				delete();
				action = ActionType.DELETE;
				break;	
			default:
				action = ActionType.CANCEL;
				break;
			}
			close();
		});
		btnCancel = new Button("Отмена");
		btnCancel.setIcon(VaadinIcons.CLOSE);	
		btnCancel.addClickListener(e -> {
			action = ActionType.CANCEL;
			close();
		});
		buttonLayout.addComponents(btnOK, btnCancel);

		Component c = getCentral();
		layout.addComponents(c, buttonLayout);
		setContent(layout);
		layout.setExpandRatio(c, 1.0f);
	}

	private void insert() {
		T item = getItem();
		Beans.insert(item);
		selectedItem = item;
	}
	private void update() {
		T item = getItem();
		Beans.update(item);
		selectedItem = item;
	}
	private void delete() {
		T item = getItem();
		Beans.delete(item);
		selectedItem = null;
	}
	
	protected abstract Component getCentral();
	protected abstract void setDeleteMode();
	protected abstract T getItem();

	public T getSelectedItem() {
		return selectedItem;
	}

	public ActionType getAction() {
		return action;
	}
}
