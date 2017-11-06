package ru.home.workplane.ui.window;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Bug;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class BugWindow extends AbstractWindow<Bug> {
	private static final long serialVersionUID = 1L;
	private TextField textTitle;
	private CheckBox checkFlag;

	public BugWindow(Bug selectedItem, WinMode mode) {
		super(selectedItem, mode, "баг");
		setHeight("220px");
		setWidth(Tools.SHORT_WIDTH);
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textTitle = new TextField("Название");
		textTitle.setWidth("100%");
		textTitle.setValue(getSelectedItem().getName());
		checkFlag = new CheckBox("Исправлен");
		checkFlag.setValue(getSelectedItem().isFlag());
		layout.addComponents(textTitle, checkFlag);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textTitle.setReadOnly(true);
		checkFlag.setReadOnly(true);
	}

	@Override
	protected Bug getItem() {
		Bug selectedItem = getSelectedItem();
		selectedItem.setName(textTitle.getValue());
		selectedItem.setFlag(checkFlag.getValue());
		return selectedItem;
	}
}
