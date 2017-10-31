package ru.home.workplane.ui.view;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Set;

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

import ru.home.workplane.beans.Beans;
import ru.home.workplane.model.Diary;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.DiaryFilterWindow;
import ru.home.workplane.ui.window.DiaryFindWindow;
import ru.home.workplane.ui.window.DiaryWindow;
import ru.home.workplane.ui.window.SkillSelectWindow;
import ru.home.workplane.util.Tools;

public class DiaryPage extends AbstractView<Diary> {
	private static final long serialVersionUID = 1L;
	private Grid<Diary> grid;
	private ListSelect<Skill> tagsList;
	private TextArea textEdit;
	private RichTextArea htmlEdit;
	private BrowserFrame tabView;

	public DiaryPage() {
		super("Записи");	
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new DiaryWindow(WinMode.INSERT)));		
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new DiaryWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new DiaryWindow(WinMode.DELETE)));
	}

	@Override
	protected Component getCentral() {
		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		
		VerticalLayout diaryLayout = new VerticalLayout();
		grid = new Grid<>();
		grid.setCaption("Список записей");
		grid.setWidth("100%");
		grid.addColumn(Diary::getDate).setCaption("Дата записи").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.LONG_DATE_FORMAT)));
		grid.addColumn(Diary::getTitle).setCaption("Название записи");
		diaryLayout.addComponent(grid);
		diaryLayout.setMargin(false);
		
		TabSheet tabSheet = new TabSheet();
		tabView = new BrowserFrame();
		tabView.setWidth("100%");
		tabView.setHeight("100%");
		tabSheet.addTab(tabView, "Просмотр", VaadinIcons.PRESENTATION);
		
		VerticalLayout htmlLayout = new VerticalLayout();
		htmlEdit = new RichTextArea();
		htmlEdit.setWidth("100%");
		htmlEdit.setHeight("100%");
		Button btnHtmlEdit = new Button("Изменить");
		btnHtmlEdit.setIcon(VaadinIcons.EDIT);
		htmlLayout.addComponents(htmlEdit, btnHtmlEdit);
		tabSheet.addTab(htmlLayout, "HTML-редактор", VaadinIcons.GLOBE);
		
		VerticalLayout textLayout = new VerticalLayout();
		textEdit = new TextArea();
		textEdit.setWidth("100%");
		textEdit.setHeight("100%");
		Button btnTextEdit = new Button("Изменить");
		btnTextEdit.setIcon(VaadinIcons.EDIT);
		textLayout.addComponents(textEdit, btnTextEdit);
		tabSheet.addTab(textLayout, "TEXT-редактор", VaadinIcons.TEXT_INPUT);

		VerticalLayout tagsLayout = new VerticalLayout();
		tagsList = new ListSelect<>();
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

		gridPanel.addComponents(diaryLayout, tabSheet);
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
	
	@Override
	public void beforeClientResponse(boolean initial) {
		super.beforeClientResponse(initial);
		Set<Diary> list = Beans.getCurrentUser().getDiaryList();
		grid.setItems(list);
		grid.getDataProvider().refreshAll();
		grid.setItems(list);
		grid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				setSelectedItem(event.getFirstSelectedItem().get());
			} else {
				setSelectedItem(null);
			}
			refresh();
		});
	}
	
	@Override
	protected void refresh() {
		Diary selectedItem = getSelectedItem();
		if(selectedItem != null && selectedItem.getContent() != null) {
			tagsList.clear();
			tagsList.setItems(selectedItem.getSkillList());
			textEdit.setValue(selectedItem.getContent());
			htmlEdit.setValue(selectedItem.getContent());
			
			StreamResource streamResource = new StreamResource(() -> new ByteArrayInputStream(selectedItem.getContent().getBytes(StandardCharsets.UTF_8)), "temp"+selectedItem.getId()+".html");
			streamResource.setMIMEType("text/html");
			tabView.setSource(streamResource);
		} else {
			tagsList.clear();
			textEdit.setValue("");
			htmlEdit.setValue("");
			tabView.setSource(new StreamResource(() -> new ByteArrayInputStream("".getBytes()), "tempnull.html"));
		}
		tagsList.getDataProvider().refreshAll();
	}
}
