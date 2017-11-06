package ru.home.workplane.ui.view;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.ListDataProvider;
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
import ru.home.workplane.ui.window.AbstractWindow;
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
	private TreeDataProvider<Project> dataProvider;
	private ListSelect<Skill> tagsList;
	private Grid<Bug> bugGrid;
	private Grid<Log> logGrid;
	private ListSelect<Diary> obstacleList;
	private TextArea textEdit;
	private RichTextArea htmlEdit;
	private BrowserFrame tabView;
	private BrowserFrame obstacleView;
	private Bug selectedBug;
	private Log selectedLog;
	private Diary selectedObstacle;
	private Project selectedParent;
	private Map<Long, Project> parentMap;
	private Map<Long, Organization> orgMap;
	private ListDataProvider<Bug> bugDataProvider;
	private ListDataProvider<Log> logDataProvider;

	public ProjectPage() {
		super("Менеджер проектов");
		selectedBug = null;
		selectedLog = null;
		selectedObstacle = null;
		selectedParent = null;
	}

	@Override
	protected Component getCentral() {
		parentMap = new HashMap<>();
		orgMap = new HashMap<>();
		
		grid = new TreeGrid<>();
		dataProvider = new TreeDataProvider<>(new TreeData<>());
 		grid.setDataProvider(dataProvider);
 		
 		bugGrid = new Grid<>();
		bugDataProvider = new ListDataProvider<Bug>(new HashSet<Bug>());
		bugGrid.setDataProvider(bugDataProvider);
		
		logGrid = new Grid<>();
		logDataProvider = new ListDataProvider<Log>(new HashSet<Log>());
		logGrid.setDataProvider(logDataProvider);		
		
		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		
		VerticalLayout projectLayout = new VerticalLayout();
		grid.setCaption("Список проектов");
		grid.setWidth("100%");
 		grid.addColumn(Project::getState).setRenderer((state) -> Tools.getResource(state).getHtml(), new HtmlRenderer());
		grid.addColumn(Project::getName).setCaption("Имя проекта");
		grid.addColumn(Project::getStateName).setCaption("Состояние");
		grid.addColumn(Project::getDateStart).setCaption("Дата начала").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		grid.addColumn(Project::getDateEnd).setCaption("Дата окончания").setRenderer(new DateRenderer(new SimpleDateFormat(Tools.SHORT_DATE_FORMAT)));
		grid.addColumn(Project::getPercentDouble).setCaption("Процент выполнения").setRenderer(new ProgressBarRenderer());
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

		VerticalLayout bugsLayout = new VerticalLayout();
		bugGrid = new Grid<>();
		bugGrid.setCaption("Найденные ошибки");
		bugGrid.addColumn(Bug::getBug).setRenderer((source) -> (source.isFlag()) ? "<html><body>"+source.getName()+"</body></html>" : "<html><body><strike>"+source.getName()+"</strike></body></html>", new HtmlRenderer());
		bugGrid.setWidth("100%");
		bugGrid.setHeight("100%");
		HorizontalLayout buttonBugsLayout = new HorizontalLayout();
		Button btnAddBug = new Button("Добавить баг");
		btnAddBug.setIcon(VaadinIcons.PLUS);
		btnAddBug.addClickListener(e -> UI.getCurrent().addWindow(new BugWindow(new Bug("", false, getSelectedItem()), WinMode.INSERT)));
		Button btnDelBug = new Button("Удалить баг");
		btnDelBug.setIcon(VaadinIcons.MINUS);
		btnDelBug.addClickListener(e -> UI.getCurrent().addWindow(new BugWindow(selectedBug, WinMode.DELETE)));
		Button btnEditBug = new Button("Изменить баг");
		btnEditBug.setIcon(VaadinIcons.EDIT);
		btnEditBug.addClickListener(e -> UI.getCurrent().addWindow(new BugWindow(selectedBug, WinMode.UPDATE)));
		buttonBugsLayout.addComponents(btnEditBug, btnAddBug, btnDelBug);
		bugsLayout.addComponents(bugGrid, buttonBugsLayout);
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
		btnAddLog.addClickListener(e -> UI.getCurrent().addWindow(new LogWindow(new Log(), WinMode.INSERT)));
		Button btnDelLog = new Button("Удалить запись");
		btnDelLog.setIcon(VaadinIcons.MINUS);
		btnDelLog.addClickListener(e -> UI.getCurrent().addWindow(new LogWindow(selectedLog, WinMode.DELETE)));
		Button btnEditLog = new Button("Изменить запись");
		btnEditLog.setIcon(VaadinIcons.EDIT);
		btnEditLog.addClickListener(e -> UI.getCurrent().addWindow(new LogWindow(selectedLog, WinMode.UPDATE)));
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
		btnAddObstacle.addClickListener(e -> UI.getCurrent().addWindow(new ObstacleWindow(Beans.getCurrentUser().getDiaryList(), WinMode.INSERT)));
		Button btnDelObstacle = new Button("Удалить запись");
		btnDelObstacle.setIcon(VaadinIcons.MINUS);
		btnDelObstacle.addClickListener(e -> UI.getCurrent().addWindow(new ObstacleWindow(new HashSet<Diary>(Arrays.asList(selectedObstacle)),WinMode.DELETE)));
		buttonObstacleLayout.addComponents(btnAddObstacle, btnDelObstacle);
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
	public void initData() {
		Set<Organization> orgList = getUser().getOrganizationList();
		dataProvider.getTreeData().clear();
		parentMap.clear();
		orgMap.clear();
		for(Organization org : orgList) {
			Project parent = new Project(org);
			dataProvider.getTreeData().addItem(null, parent);
			dataProvider.getTreeData().addItems(parent, org.getProjectList());
			parentMap.put(org.getId(), parent);
			orgMap.put(org.getId(), org);
		}
		
		grid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				Project prj = event.getFirstSelectedItem().get();
				setSelectedItem(prj);
				selectedParent = parentMap.get(prj.getOrganization().getId());
			} else {
				setSelectedItem(null);
				selectedParent = null;
			}
			refresh();
		});
		
		bugGrid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				selectedBug = event.getFirstSelectedItem().get();
			} else {
				selectedBug = null;
			}			
		});

		logGrid.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				selectedLog = event.getFirstSelectedItem().get();
			} else {
				selectedLog = null;
			}			
		});
		
		obstacleList.addSelectionListener(event -> {
			if(event.getFirstSelectedItem().isPresent()) {
				selectedObstacle = event.getFirstSelectedItem().get();
				refresh(selectedObstacle);
			} else {
				selectedObstacle = null;
				refresh(selectedObstacle);
			}			
		});
	}
	
	@Override
	protected void refresh() {
		Project selectedItem = getSelectedItem();
		if(selectedItem != null && selectedItem.getState() != ProjectStates.ORGANIZATION) {
			tagsList.clear();
			tagsList.setItems(selectedItem.getSkillList());
			bugDataProvider.getItems().clear();
			bugDataProvider.getItems().addAll(selectedItem.getBugList());
			logDataProvider.getItems().clear();
			logDataProvider.getItems().addAll(selectedItem.getLogList());
			obstacleList.clear();
			obstacleList.setItems(selectedItem.getObstacleList());
			textEdit.setValue(selectedItem.getDescription());
			htmlEdit.setValue(selectedItem.getDescription());
			
			StreamResource streamResource = new StreamResource(() -> new ByteArrayInputStream(selectedItem.getDescription().getBytes(StandardCharsets.UTF_8)), "temp"+selectedItem.getId()+".html");
			streamResource.setMIMEType("text/html");
			tabView.setSource(streamResource);
		} else {
			tagsList.clear();
			bugDataProvider.getItems().clear();
			logDataProvider.getItems().clear();
			obstacleList.clear();
			textEdit.setValue("");
			htmlEdit.setValue("");			
			tabView.setSource(new StreamResource(() -> new ByteArrayInputStream("".getBytes()), "tempnull.html"));
		}
		logGrid.getDataProvider().refreshAll();
		tagsList.getDataProvider().refreshAll();
		bugGrid.getDataProvider().refreshAll();
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

	@Override
	protected Project getEmptyItem() {
		Project project = new Project();
		return project;
	}

	@Override
	protected AbstractWindow<Project> getInsertWindow() {
		ProjectWindow projectWindow = new ProjectWindow(getEmptyItem(), WinMode.INSERT);
		return projectWindow;
	}

	@Override
	protected AbstractWindow<Project> getUpdateWindow() {
		ProjectWindow projectWindow = new ProjectWindow(getSelectedItem(), WinMode.UPDATE);
		return projectWindow;
	}

	@Override
	protected AbstractWindow<Project> getDeleteWindow() {
		ProjectWindow projectWindow = new ProjectWindow(getSelectedItem(), WinMode.DELETE);
		return projectWindow;
	}

	@Override
	protected void addItem(Project item) {
		dataProvider.getTreeData().addItem(selectedParent, item);
		orgMap.get(item.getOrganization().getId()).getProjectList().add(item);
	}

	@Override
	protected void removeItem(Project item) {
		dataProvider.getTreeData().removeItem(item);
		orgMap.get(item.getOrganization().getId()).getProjectList().remove(item);
	}
}
