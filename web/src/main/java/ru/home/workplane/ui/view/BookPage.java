package ru.home.workplane.ui.view;

import java.util.HashSet;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Book;
import ru.home.workplane.model.Content;
import ru.home.workplane.model.Paragraph;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.AbstractWindow;
import ru.home.workplane.ui.window.BookFilterWindow;
import ru.home.workplane.ui.window.BookFindWindow;
import ru.home.workplane.ui.window.BookWindow;
import ru.home.workplane.ui.window.ParagraphWindow;
import ru.home.workplane.ui.window.SkillSelectWindow;
import ru.home.workplane.util.Tools;

public class BookPage extends AbstractView<Book> {
	private static final long serialVersionUID = 1L;
	private Grid<Book> gridBook;
	private ListDataProvider<Book> bookDataProvider;
	private ListSelect<Skill> tagsList;
	private TreeGrid<Paragraph> gridContent;
	private TreeDataProvider<Paragraph> dataProvider;
	private Paragraph selectedParagraph;

	public BookPage() {
		super("Книги");
		selectedParagraph = null;
	}

	@Override
	protected Component getCentral() {
		gridContent = new TreeGrid<>();
		dataProvider = new TreeDataProvider<>(new TreeData<>());
		gridContent.setDataProvider(dataProvider);
		
		gridBook = new Grid<>();
		bookDataProvider = new ListDataProvider<>(new HashSet<Book>());
		gridBook.setDataProvider(bookDataProvider);

		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		
		VerticalLayout bookLayout = new VerticalLayout();
		gridBook.setCaption("Список книг");
		gridBook.setWidth("100%");
		gridBook.addColumn(Book::getTitle).setCaption("Название");
		gridBook.addColumn(Book::getAuthor).setCaption("Автор");
		gridBook.addColumn(Book::getPublisher).setCaption("Издатель");
		gridBook.addColumn(Book::getPages).setCaption("Страниц").setRenderer(new NumberRenderer());
		gridBook.addColumn(Book::getYear).setCaption("Год").setRenderer(new NumberRenderer());
		gridBook.addColumn(Book::isStudied).setCaption("Прочитал").setRenderer(flag -> Tools.getFlagResource(flag).getHtml(), new HtmlRenderer());
		bookLayout.addComponent(gridBook);
		bookLayout.setMargin(false);
		
		TabSheet tabSheet = new TabSheet();
		
		VerticalLayout contentLayout = new VerticalLayout();
		gridContent.setCaption("Содержание");
		gridContent.setWidth("100%");
		gridContent.addColumn(Paragraph::getNumber).setCaption("Номер");
		gridContent.addColumn(Paragraph::getTitle).setCaption("Название");
		gridContent.addColumn(Paragraph::getPage).setCaption("Страница").setRenderer(new NumberRenderer());
		HorizontalLayout buttonContentLayout = new HorizontalLayout();
		Button btnEditParagraph = new Button("Изменить параграф");
		btnEditParagraph.setIcon(VaadinIcons.EDIT);
		btnEditParagraph.addClickListener(e -> UI.getCurrent().addWindow(new ParagraphWindow(new Paragraph(), WinMode.UPDATE)));
		Button btnAddParagraph = new Button("Добавить параграф");
		btnAddParagraph.setIcon(VaadinIcons.PLUS);
		btnAddParagraph.addClickListener(e -> UI.getCurrent().addWindow(new ParagraphWindow(selectedParagraph, WinMode.INSERT)));
		Button btnDelParagraph = new Button("Удалить параграф");
		btnDelParagraph.setIcon(VaadinIcons.MINUS);
		btnDelParagraph.addClickListener(e -> UI.getCurrent().addWindow(new ParagraphWindow(selectedParagraph, WinMode.DELETE)));
		buttonContentLayout.addComponents(btnEditParagraph, btnAddParagraph, btnDelParagraph);		
		contentLayout.addComponents(gridContent, buttonContentLayout);
		tabSheet.addTab(contentLayout, "Содержание");
		
		VerticalLayout tagsLayout = new VerticalLayout();
		tagsList = new ListSelect<>();
		tagsList.setCaption("Тэги к книге");
		tagsList.setWidth("100%");
		tagsList.setHeight("100%");
		HorizontalLayout buttonTagsLayout = new HorizontalLayout();
		Button btnAddTag = new Button("Добавить тэг");
		btnAddTag.setIcon(VaadinIcons.PLUS);
		btnAddTag.addClickListener(e -> {
			SkillSelectWindow selectWindow = new SkillSelectWindow(WinMode.INSERT);
			selectWindow.setItems(getUser().getSkillList());
			UI.getCurrent().addWindow(selectWindow);
		});
		Button btnDelTag = new Button("Удалить тэг");
		btnDelTag.setIcon(VaadinIcons.MINUS);
		btnDelTag.addClickListener(e -> {
			SkillSelectWindow selectWindow = new SkillSelectWindow(WinMode.DELETE);
			selectWindow.setItems(getSelectedItem().getSkillList());
			UI.getCurrent().addWindow(selectWindow);
		});
		buttonTagsLayout.addComponents(btnAddTag, btnDelTag);
		tagsLayout.addComponents(tagsList, buttonTagsLayout);
		tabSheet.addTab(tagsLayout, "Список тэгов", VaadinIcons.TAGS);

		gridPanel.addComponents(bookLayout, tabSheet);
		return gridPanel;
	}

	@Override
	protected Component getExtraButtons() {
		VerticalLayout buttonLayout = new VerticalLayout();
		Button btnFind = new Button("Поиск");
		btnFind.setWidth(Tools.BUTTON_WIDTH);
		btnFind.addClickListener(e -> UI.getCurrent().addWindow(new BookFindWindow()));		
		Button btnFilter = new Button("Фильтр тэгов");
		btnFilter.setIcon(VaadinIcons.FILTER);
		btnFilter.setWidth(Tools.BUTTON_WIDTH);
		btnFilter.addClickListener(e -> UI.getCurrent().addWindow(new BookFilterWindow(Beans.getCurrentUser().getSkillList())));		
		buttonLayout.addComponents(btnFind, btnFilter);
		buttonLayout.setMargin(false);
		return buttonLayout;
	}

	@Override
	public void initData() {
		bookDataProvider.getItems().clear();
		bookDataProvider.getItems().addAll(getUser().getBookList());
		gridBook.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				setSelectedItem(event.getFirstSelectedItem().get());
			} else {
				setSelectedItem(null);
			}
			refresh();
		});

		gridContent.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				selectedParagraph = event.getFirstSelectedItem().get();
			} else {
				selectedParagraph = null;
			}			
		});
	}
	
	@Override
	protected void refresh() {
		Book selectedItem = getSelectedItem();
		if(selectedItem != null && selectedItem.getContent() != null) {
			tagsList.clear();
			dataProvider.getTreeData().clear();
			tagsList.setItems(selectedItem.getSkillList());
			
			Content content = new Content();
			content.unmarshal(selectedItem.getContent());
			for(Paragraph paragraph : content.getParagraphList()) {
				dataProvider.getTreeData().addItem(null, paragraph);
				if(paragraph != null && paragraph.getParagraphList() != null && paragraph.getParagraphList().size() > 0) {
					addParagraph(paragraph);
				}
			}
		} else {
			tagsList.clear();
			dataProvider.getTreeData().clear();
		}
		tagsList.getDataProvider().refreshAll();
		dataProvider.refreshAll();
		bookDataProvider.refreshAll();
	}
	
	private void addParagraph(Paragraph paragraph) {
		for(Paragraph p : paragraph.getParagraphList()) {
			dataProvider.getTreeData().addItems(paragraph, p);
			if(p != null && p.getParagraphList() != null && p.getParagraphList().size() > 0) {
				addParagraph(p);
			}
		}
	}

	@Override
	protected Book getEmptyItem() {
		Book book = new Book();
		book.setUser(getUser());
		return book;
	}

	@Override
	protected AbstractWindow<Book> getInsertWindow() {
		BookWindow bookWindow = new BookWindow(getEmptyItem(), WinMode.INSERT);
		return bookWindow;
	}

	@Override
	protected AbstractWindow<Book> getUpdateWindow() {
		BookWindow bookWindow = new BookWindow(getSelectedItem(), WinMode.UPDATE);
		return bookWindow;
	}

	@Override
	protected AbstractWindow<Book> getDeleteWindow() {
		BookWindow bookWindow = new BookWindow(getSelectedItem(), WinMode.DELETE);
		return bookWindow;
	}

	@Override
	protected void addItem(Book item) {
		bookDataProvider.getItems().add(item);
		getUser().getBookList().add(item);
	}

	@Override
	protected void removeItem(Book item) {
		bookDataProvider.getItems().remove(item);
		getUser().getBookList().remove(item);
	}
}
