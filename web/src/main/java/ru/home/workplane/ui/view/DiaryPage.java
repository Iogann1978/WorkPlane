package ru.home.workplane.ui.view;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.renderers.DateRenderer;

import ru.home.workplane.model.Diary;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.DiaryFilterWindow;
import ru.home.workplane.ui.window.DiaryFindWindow;
import ru.home.workplane.ui.window.DiaryWindow;
import ru.home.workplane.ui.window.SkillSelectWindow;
import ru.home.workplane.util.Tools;

public class DiaryPage extends AbstractView {
	private static final long serialVersionUID = 1L;

	public DiaryPage() {
		super("Записи");
		
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new DiaryWindow(WinMode.INSERT)));		
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new DiaryWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new DiaryWindow(WinMode.DELETE)));		
	}

	@Override
	protected Component getCentral() {
		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		Grid<Diary> grid = new Grid<>();
		grid.setCaption("Список записей");
		grid.setWidth("100%");
		grid.addColumn(Diary::getDate).setCaption("Дата записи").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.LONG_DATE_FORMAT)));
		grid.addColumn(Diary::getTitle).setCaption("Название записи");
		List<Diary> diaryItems = new ArrayList<>();
		diaryItems.add(new Diary("Первая запись", new Date(), "<html><body><h1>Первая запись</h1></body></html>"));
		Set<Skill> skillItems = new HashSet<>();
		skillItems.add(new Skill("Java"));
		diaryItems.get(0).setSkillList(skillItems);
		grid.setItems(diaryItems);
		
		TabSheet tabSheet = new TabSheet();
		BrowserFrame tabView = new BrowserFrame();
		tabView.setWidth("100%");
		tabView.setHeight("100%");
		tabSheet.addTab(tabView, "Просмотр", VaadinIcons.PRESENTATION);
		
		VerticalLayout htmlLayout = new VerticalLayout();
		RichTextArea htmlEdit = new RichTextArea();
		htmlEdit.setWidth("100%");
		htmlEdit.setHeight("100%");
		Button btnHtmlEdit = new Button("Изменить");
		btnHtmlEdit.setIcon(VaadinIcons.EDIT);
		htmlLayout.addComponents(htmlEdit, btnHtmlEdit);
		tabSheet.addTab(htmlLayout, "HTML-редактор", VaadinIcons.GLOBE);
		
		VerticalLayout textLayout = new VerticalLayout();
		TextArea textEdit = new TextArea();
		textEdit.setWidth("100%");
		textEdit.setHeight("100%");
		Button btnTextEdit = new Button("Изменить");
		btnTextEdit.setIcon(VaadinIcons.EDIT);
		textLayout.addComponents(textEdit, btnTextEdit);
		tabSheet.addTab(textLayout, "TEXT-редактор", VaadinIcons.TEXT_INPUT);

		VerticalLayout tagsLayout = new VerticalLayout();
		ListSelect<Skill> tagsList = new ListSelect<>();
		tagsList.setItems(diaryItems.get(0).getSkillList());
		tagsList.setCaption("Опыт к записи");
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
		
		tabSheet.setWidth("100%");
		tabSheet.setHeight("100%");

		Binder<Diary> binder = new Binder<>();
		binder.bind(textEdit, diary -> diary.getContent(), (diary, content) -> diary.setContent(content));
		binder.bind(htmlEdit, diary -> diary.getContent(), (diary, content) -> diary.setContent(content));
		binder.readBean(diaryItems.get(0));
		tabView.setSource(new StreamResource(() -> new ByteArrayInputStream(diaryItems.get(0).getContent().getBytes()), "temp.html"));
		
		gridPanel.addComponents(grid, tabSheet);
		return gridPanel;
	}

	@Override
	protected Component getExtraButtons() {
		VerticalLayout buttonLayout = new VerticalLayout();
		Button btnFind = new Button("Поиск");
		btnFind.setIcon(VaadinIcons.SEARCH);
		btnFind.setWidth(Tools.BUTTON_WIDTH);
		btnFind.addClickListener(e -> UI.getCurrent().addWindow(new DiaryFindWindow()));
		Button btnFilter = new Button("Фильтр тэгов");
		btnFilter.setIcon(VaadinIcons.FILTER);
		btnFilter.setWidth(Tools.BUTTON_WIDTH);
		btnFilter.addClickListener(e -> UI.getCurrent().addWindow(new DiaryFilterWindow()));
		buttonLayout.addComponents(btnFind, btnFilter);
		buttonLayout.setMargin(false);
		return buttonLayout;
	}
}
