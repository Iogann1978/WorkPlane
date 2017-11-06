package ru.home.workplane.ui.window;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Book;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class BookWindow extends AbstractWindow<Book> {
	private static final long serialVersionUID = 1L;
	private TextField textTitle, textAuthor, textPublisher;
	private NumericLayout pagesLayout, yearLayout;
	private CheckBox checkFlag;

	public BookWindow(Book selectedItem, WinMode mode) {
		super(selectedItem, mode, "книгу");
		setHeight("510px");
		setWidth(Tools.SHORT_WIDTH);
	}
	
	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textTitle = new TextField();
		textTitle.setCaption("Название");
		textTitle.setWidth("100%");
		textTitle.setValue(getSelectedItem().getTitle());
		textAuthor = new TextField();
		textAuthor.setCaption("Автор");
		textAuthor.setWidth("100%");
		textAuthor.setValue(getSelectedItem().getAuthor());
		textPublisher = new TextField();
		textPublisher.setCaption("Издатель");
		textPublisher.setWidth("100%");
		textPublisher.setValue(getSelectedItem().getPublisher());
		
		checkFlag = new CheckBox("Прочитал");
		checkFlag.setValue(getSelectedItem().isStudied());
		
		pagesLayout = new NumericLayout("Число страниц");
		pagesLayout.setValue(getSelectedItem().getPages());
		
		yearLayout = new NumericLayout("Год издания");
		yearLayout.setValue(getSelectedItem().getYear());		
		
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

	@Override
	protected Book getItem() {
		Book selectedItem = getSelectedItem();   
		selectedItem.setTitle(textTitle.getValue());
		selectedItem.setAuthor(textAuthor.getValue());
		selectedItem.setPublisher(textPublisher.getValue());
		selectedItem.setStudied(checkFlag.getValue());
		selectedItem.setPages(pagesLayout.getValue());
		selectedItem.setYear(yearLayout.getValue());
		return selectedItem;
	}
}
