package ru.home.workplane.ui.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.TreeData;
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

import ru.home.workplane.model.Book;
import ru.home.workplane.model.Content;
import ru.home.workplane.model.Paragraph;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.BookWindow;
import ru.home.workplane.ui.window.ParagraphWindow;
import ru.home.workplane.ui.window.SkillSelectWindow;
import ru.home.workplane.util.Tools;

public class BookPage extends AbstractView {
	private static final long serialVersionUID = 1L;
	private TreeData<Paragraph> data;

	public BookPage() {
		super("Книги");
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new BookWindow(WinMode.UPDATE)));
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new BookWindow(WinMode.INSERT)));
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new BookWindow(WinMode.DELETE)));
	}

	@Override
	protected Component getCentral() {
		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		
		VerticalLayout bookLayout = new VerticalLayout();
		Grid<Book> gridBook = new Grid<>();
		gridBook.setCaption("Список книг");
		gridBook.setWidth("100%");
		gridBook.addColumn(Book::getTitle).setCaption("Название");
		gridBook.addColumn(Book::getAuthor).setCaption("Автор");
		gridBook.addColumn(Book::getPublisher).setCaption("Издатель");
		gridBook.addColumn(Book::getPages).setCaption("Страниц").setRenderer(new NumberRenderer());
		gridBook.addColumn(Book::getYear).setCaption("Год").setRenderer(new NumberRenderer());
		gridBook.addColumn(Book::isStudied).setCaption("Прочитал").setRenderer(flag -> Tools.getFlagResource(flag).getHtml(), new HtmlRenderer());
		gridBook.setItems(Arrays.asList(new Book("Программирование на Java", 600, "Керниган, Ричи", "Deitail", 1997, true),
				new Book("Java EE", 600, "Керниган, Ричи", "Deitail", 1997, false)));
		bookLayout.addComponent(gridBook);
		bookLayout.setMargin(false);
		
		TabSheet tabSheet = new TabSheet();
		
		VerticalLayout contentLayout = new VerticalLayout();
		TreeGrid<Paragraph> gridContent = new TreeGrid<>();
		gridContent.setCaption("Содержание");
		gridContent.setWidth("100%");
		data = new TreeData<>();
		Content content = getContent();
		content.getParagraphList().stream().forEach(p -> addParagraph(null, p));
		TreeDataProvider<Paragraph> dataProvider = new TreeDataProvider<>(data);
		gridContent.setDataProvider(dataProvider);
		gridContent.addColumn(Paragraph::getNumber).setCaption("Номер");
		gridContent.addColumn(Paragraph::getTitle).setCaption("Название");
		gridContent.addColumn(Paragraph::getPage).setCaption("Страница").setRenderer(new NumberRenderer());
		HorizontalLayout buttonContentLayout = new HorizontalLayout();
		Button btnEditParagraph = new Button("Изменить параграф");
		btnEditParagraph.setIcon(VaadinIcons.EDIT);
		btnEditParagraph.addClickListener(e -> UI.getCurrent().addWindow(new ParagraphWindow(WinMode.UPDATE)));
		Button btnAddParagraph = new Button("Добавить параграф");
		btnAddParagraph.setIcon(VaadinIcons.PLUS);
		btnAddParagraph.addClickListener(e -> UI.getCurrent().addWindow(new ParagraphWindow(WinMode.INSERT)));
		Button btnDelParagraph = new Button("Удалить параграф");
		btnDelParagraph.setIcon(VaadinIcons.MINUS);
		btnDelParagraph.addClickListener(e -> UI.getCurrent().addWindow(new ParagraphWindow(WinMode.DELETE)));
		buttonContentLayout.addComponents(btnEditParagraph, btnAddParagraph, btnDelParagraph);		
		contentLayout.addComponents(gridContent, buttonContentLayout);
		tabSheet.addTab(contentLayout, "Содержание");
		
		VerticalLayout tagsLayout = new VerticalLayout();
		ListSelect<Skill> tagsList = new ListSelect<>();
		tagsList.setItems(Arrays.asList(new Skill("Java")));
		tagsList.setCaption("Тэги к книге");
		tagsList.setWidth("100%");
		tagsList.setHeight("100%");
		HorizontalLayout buttonTagsLayout = new HorizontalLayout();
		Button btnAddTag = new Button("Добавить тэг");
		btnAddTag.setIcon(VaadinIcons.PLUS);
		btnAddTag.addClickListener(e -> UI.getCurrent().addWindow(new SkillSelectWindow(WinMode.INSERT)));
		Button btnDelTag = new Button("Удалить тэг");
		btnDelTag.setIcon(VaadinIcons.MINUS);
		btnDelTag.addClickListener(e -> UI.getCurrent().addWindow(new SkillSelectWindow(WinMode.DELETE)));
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
		buttonLayout.addComponent(btnFind);
		buttonLayout.setMargin(false);
		return buttonLayout;
	}

	private void addParagraph(Paragraph parent, Paragraph child) {
		data.addItem(parent, child);
		if(child != null && child.getChilds() != null && child.getChilds().size() > 0) {
			child.getChilds().stream().forEach(p -> addParagraph(child, p));
		}
	}
	
	private Content getContent() {
		Content content = new Content();
		List<Paragraph> pList = new ArrayList<>();
		pList.add(new Paragraph("1", "Один", 1));
		pList.add(new Paragraph("2", "Два", 10));
		List<Paragraph> cList1 = new ArrayList<>();
		cList1.add(new Paragraph("1.1", "Один", 2));
		cList1.add(new Paragraph("1.2", "Два", 3));
		pList.get(0).setChilds(cList1.stream().collect(Collectors.toSet()));
		List<Paragraph> cList2 = new ArrayList<>();
		cList2.add(new Paragraph("2.1", "Один", 11));
		cList2.add(new Paragraph("2.2", "Два", 12));
		pList.get(1).setChilds(cList2.stream().collect(Collectors.toSet()));
		content.setParagraphList(pList.stream().collect(Collectors.toSet()));
		
		return content;
	}
}
