package ru.home.workplane.ui.window;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class BookWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textTitle, textAuthor, textPublisher;
	private NumericLayout pagesLayout, yearLayout;
	private CheckBox checkFlag;

	public BookWindow(WinMode mode) {
		super(mode, "книгу");
		setHeight("510px");
		setWidth(Tools.SHORT_WIDTH);
	}
	
	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textTitle = new TextField();
		textTitle.setCaption("Название");
		textTitle.setWidth("100%");
		textAuthor = new TextField();
		textAuthor.setCaption("Автор");
		textAuthor.setWidth("100%");
		textPublisher = new TextField();
		textPublisher.setCaption("Издатель");
		textPublisher.setWidth("100%");
		
		checkFlag = new CheckBox("Прочитал");
		
		pagesLayout = new NumericLayout("Число страниц");
		pagesLayout.setValue(1);
		
		yearLayout = new NumericLayout("Год издания");
		yearLayout.setValue(1970);		
		
		layout.addComponents(textTitle, textAuthor, textPublisher, checkFlag, pagesLayout, yearLayout);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textTitle.setReadOnly(true);
		textAuthor.setReadOnly(true);
		textPublisher.setReadOnly(true);
		checkFlag.setReadOnly(true);
		pagesLayout.setDeleteMode();
		yearLayout.setDeleteMode();
	}
}
