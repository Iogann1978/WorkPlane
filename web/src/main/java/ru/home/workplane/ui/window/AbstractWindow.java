package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ru.home.workplane.ui.enums.WinMode;

public abstract class AbstractWindow extends Window {
	private static final long serialVersionUID = 1L;
	protected Button btnOK, btnCancel;
	private VerticalLayout layout;
	
	public AbstractWindow() {
		super();
		init();
	}
	
	public AbstractWindow(WinMode mode, String title) {
		super();
		
		switch(mode) {
		case INSERT:
			setCaption("Добавить " + title);
			setIcon(VaadinIcons.PLUS);
			break;
		case UPDATE:
			setCaption("Изменить " + title);
			setIcon(VaadinIcons.EDIT);
			break;
		default:
			setCaption("Удалить " + title);
			setIcon(VaadinIcons.MINUS);
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
		btnCancel = new Button("Отмена");
		btnCancel.setIcon(VaadinIcons.CLOSE);	
		btnCancel.addClickListener(e -> close());
		buttonLayout.addComponents(btnOK, btnCancel);

		Component c = getCentral();
		layout.addComponents(c, buttonLayout);
		setContent(layout);
		layout.setExpandRatio(c, 1.0f);
	}
			
	protected abstract Component getCentral();
	protected abstract void setDeleteMode();
}
