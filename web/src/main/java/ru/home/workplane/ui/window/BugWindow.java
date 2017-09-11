package ru.home.workplane.ui.window;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.ui.enums.WinMode;

public class BugWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textTitle;
	private CheckBox checkFlag;

	public BugWindow(WinMode mode) {
		super(mode, "баг");
		setHeight("220px");
		setWidth("400px");
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textTitle = new TextField("Название");
		textTitle.setWidth("100%");
		checkFlag = new CheckBox("Исправлен");
		layout.addComponents(textTitle, checkFlag);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textTitle.setReadOnly(true);
		checkFlag.setReadOnly(true);
	}
}
