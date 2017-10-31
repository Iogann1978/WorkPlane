package ru.home.workplane.ui.view;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
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
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.enums.ProjectStates;
import ru.home.workplane.model.Bug;
import ru.home.workplane.model.Diary;
import ru.home.workplane.model.Log;
import ru.home.workplane.model.Organization;
import ru.home.workplane.model.Project;
import ru.home.workplane.model.Skill;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.ui.window.BugWindow;
import ru.home.workplane.ui.window.LogWindow;
import ru.home.workplane.ui.window.ObstacleWindow;
import ru.home.workplane.ui.window.ProjectReportWindow;
import ru.home.workplane.ui.window.ProjectWindow;
import ru.home.workplane.ui.window.SkillSelectWindow;
import ru.home.workplane.util.Tools;

public class ProjectPage extends AbstractView<Project> {
	private static final long serialVersionUID = 1L;
	private TreeGrid<Project> grid;
	private TreeData<Project> data;
	private ListSelect<Skill> tagsList;
	private Grid<Bug> bugsGrid;
	private Grid<Log> logGrid;
	private ListSelect<Diary> obstacleList;
	private TextArea textEdit;
	private RichTextArea htmlEdit;
	private BrowserFrame tabView;
	private BrowserFrame obstacleView;

	public ProjectPage() {
		super("Менеджер проектов");
		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new ProjectWindow(WinMode.INSERT)));
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new ProjectWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new ProjectWindow(WinMode.DELETE)));
	}

	@Override
	protected Component getCentral() {
		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		
		VerticalLayout projectLayout = new VerticalLayout();
		grid = new TreeGrid<>();
		grid.setCaption("Список проектов");
		grid.setWidth("100%");
		data = new TreeData<>();
		TreeDataProvider<Project> dataProvider = new TreeDataProvider<>(data);
 		grid.setDataProvider(dataProvider);
 		grid.addColumn(Project::getState).setRenderer((state) -> Tools.getResource(state).getHtml(), new HtmlRenderer());
		grid.addColumn(Project::getName).setCaption("Имя проекта");
		grid.addColumn(Project::getStateName).setCaption("Состояние");
		grid.addColumn(Project::getDateStart).setCaption("Дата начала").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		grid.addColumn(Project::getDateEnd).setCaption("Дата окончания").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		grid.addColumn(Project::getPercent).setCaption("Процент выполнения").setRenderer(new ProgressBarRenderer());
		projectLayout.addComponent(grid);
		projectLayout.setMargin(false);
		
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
		tagsList.setCaption("Опыт к проекту");
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

		VerticalLayout bugsLayout = new VerticalLayout();
		bugsGrid = new Grid<>();
		bugsGrid.setCaption("Найденные ошибки");
		bugsGrid.addColumn(Bug::getBug).setRenderer((source) -> (source.isFlag()) ? "<html><body>"+source.getName()+"</body></html>" : "<html><body><strike>"+source.getName()+"</strike></body></html>", new HtmlRenderer());
		bugsGrid.setWidth("100%");
		bugsGrid.setHeight("100%");
		HorizontalLayout buttonBugsLayout = new HorizontalLayout();
		Button btnAddBug = new Button("Добавить баг");
		btnAddBug.setIcon(VaadinIcons.PLUS);
		btnAddBug.addClickListener(e -> UI.getCurrent().addWindow(new BugWindow(WinMode.INSERT)));
		Button btnDelBug = new Button("Удалить баг");
		btnDelBug.setIcon(VaadinIcons.MINUS);
		btnDelBug.addClickListener(e -> UI.getCurrent().addWindow(new BugWindow(WinMode.DELETE)));
		Button btnEditBug = new Button("Изменить баг");
		btnEditBug.setIcon(VaadinIcons.EDIT);
		btnEditBug.addClickListener(e -> UI.getCurrent().addWindow(new BugWindow(WinMode.UPDATE)));
		buttonBugsLayout.addComponents(btnEditBug, btnAddBug, btnDelBug);
		bugsLayout.addComponents(bugsGrid, buttonBugsLayout);
		tabSheet.addTab(bugsLayout, "Найденные ошибки", VaadinIcons.BUG);

		VerticalLayout logLayout = new VerticalLayout();
		logGrid = new Grid<>();
		logGrid.setCaption("Журнал работ");
		logGrid.addColumn(Log::getDateStart).setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		logGrid.addColumn(Log::getDateEnd).setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		logGrid.addColumn(Log::getDescription);
		logGrid.setWidth("100%");
		logGrid.setHeight("100%");
		HorizontalLayout buttonLogLayout = new HorizontalLayout();
		Button btnAddLog = new Button("Добавить запись");
		btnAddLog.setIcon(VaadinIcons.PLUS);
		btnAddLog.addClickListener(e -> UI.getCurrent().addWindow(new LogWindow(WinMode.INSERT)));
		Button btnDelLog = new Button("Удалить запись");
		btnDelLog.setIcon(VaadinIcons.MINUS);
		Button btnEditLog = new Button("Изменить запись");
		btnEditLog.setIcon(VaadinIcons.EDIT);
		buttonLogLayout.addComponents(btnEditLog, btnAddLog, btnDelLog);
		logLayout.addComponents(logGrid, buttonLogLayout);
		tabSheet.addTab(logLayout, "Журнал работ", VaadinIcons.LIST);

		VerticalLayout vobstacleLayout = new VerticalLayout();
		HorizontalLayout hobstacleLayout = new HorizontalLayout();
		obstacleList = new ListSelect<>();
		obstacleList.setCaption("Проблемы и решения");
		obstacleList.setWidth("100%");
		obstacleList.setHeight("100%");
		obstacleView = new BrowserFrame();
		obstacleView.setWidth("100%");
		obstacleView.setHeight("100%");
		HorizontalLayout buttonObstacleLayout = new HorizontalLayout();
		Button btnAddObstacle = new Button("Добавить запись");
		btnAddObstacle.setIcon(VaadinIcons.PLUS);
		btnAddObstacle.addClickListener(e -> UI.getCurrent().addWindow(new ObstacleWindow(WinMode.INSERT)));
		Button btnDelObstacle = new Button("Удалить запись");
		btnDelObstacle.setIcon(VaadinIcons.MINUS);
		btnDelObstacle.addClickListener(e -> UI.getCurrent().addWindow(new ObstacleWindow(WinMode.DELETE)));
		Button btnEditObstacle = new Button("Изменить запись");
		btnEditObstacle.setIcon(VaadinIcons.EDIT);
		btnEditObstacle.addClickListener(e -> UI.getCurrent().addWindow(new ObstacleWindow(WinMode.UPDATE)));
		buttonObstacleLayout.addComponents(btnEditObstacle, btnAddObstacle, btnDelObstacle);
		hobstacleLayout.addComponents(obstacleList, obstacleView);
		vobstacleLayout.addComponents(hobstacleLayout, buttonObstacleLayout);
		tabSheet.addTab(vobstacleLayout, "Проблемы и решения", VaadinIcons.ERASER);
		tabSheet.setWidth("100%");
		gridPanel.addComponents(projectLayout, tabSheet);
		
		return gridPanel;
	}

	@Override
	protected Component getExtraButtons() {
		VerticalLayout buttonLayout = new VerticalLayout();
		Button btnView = new Button("Просмотреть");
		Button btnReport = new Button("Отчёт");
		btnView.setWidth(Tools.BUTTON_WIDTH);
		btnView.setIcon(VaadinIcons.BROWSER);
		btnReport.setWidth(Tools.BUTTON_WIDTH);
		btnReport.setIcon(VaadinIcons.PRINT);
		btnReport.addClickListener(e -> UI.getCurrent().addWindow(new ProjectReportWindow()));
		buttonLayout.addComponents(btnView, btnReport);
		buttonLayout.setMargin(false);
		return buttonLayout;
	}
	
	@Override
	public void beforeClientResponse(boolean initial) {
		super.beforeClientResponse(initial);
		Set<Organization> orgList = Beans.getCurrentUser().getOrganizationList();
		for(Organization org : orgList) {
			Project parent = new Project(org);
			data.addItem(null, parent);
			data.addItems(parent, org.getProjectList());
		}
		
		grid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				setSelectedItem(event.getFirstSelectedItem().get());
			} else {
				setSelectedItem(null);
			}
			refresh();
		});
		
		obstacleList.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				Diary diary = event.getFirstSelectedItem().get();
				refresh(diary);
			} else {
				refresh(null);
			}			
		});
	}
	
	@Override
	protected void refresh() {
		Project selectedItem = getSelectedItem();
		if(selectedItem != null && selectedItem.getState() != ProjectStates.ORGANIZATION) {
			tagsList.clear();
			tagsList.setItems(selectedItem.getSkillList());
			bugsGrid.setItems(selectedItem.getBugList());
			logGrid.setItems(selectedItem.getLogList());
			obstacleList.clear();
			obstacleList.setItems(selectedItem.getObstacleList());
			textEdit.setValue(selectedItem.getDescription());
			htmlEdit.setValue(selectedItem.getDescription());
			
			StreamResource streamResource = new StreamResource(() -> new ByteArrayInputStream(selectedItem.getDescription().getBytes(StandardCharsets.UTF_8)), "temp"+selectedItem.getId()+".html");
			streamResource.setMIMEType("text/html");
			tabView.setSource(streamResource);
		} else {
			tagsList.clear();
			bugsGrid.setItems(new HashSet<Bug>());
			logGrid.setItems(new HashSet<Log>());
			obstacleList.clear();
			textEdit.setValue("");
			htmlEdit.setValue("");			
			tabView.setSource(new StreamResource(() -> new ByteArrayInputStream("".getBytes()), "tempnull.html"));
		}
		logGrid.getDataProvider().refreshAll();
		tagsList.getDataProvider().refreshAll();
		bugsGrid.getDataProvider().refreshAll();
		obstacleList.getDataProvider().refreshAll();
	}
	
	private void refresh(Diary diary) {
		if(diary != null) {
			StreamResource streamResource = new StreamResource(() -> new ByteArrayInputStream(diary.getContent().getBytes(StandardCharsets.UTF_8)), "temp"+diary.getId()+".html");
			streamResource.setMIMEType("text/html");
			obstacleView.setSource(streamResource);			
		} else {
			obstacleView.setSource(new StreamResource(() -> new ByteArrayInputStream("".getBytes()), "tempnull.html"));
		}
	}
}
