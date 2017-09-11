package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class DiaryFindWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	
	public DiaryFindWindow() {
		super();
		
		setCaption("Поиск по строке");
		setHeight("180px");
		setWidth("400px");
		setIcon(VaadinIcons.SEARCH);
	}

	@Override
	protected Component getCentral() {
		TextField textFind = new TextField("Введите строку для поиска");
		textFind.setWidth("100%");
		return textFind;
	}

	@Override
	protected void setDeleteMode() {
	}	
}
