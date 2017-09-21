package ru.home.workplane.ui.view;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Binder;
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

public class ProjectPage extends AbstractView {
	private static final long serialVersionUID = 1L;

	public ProjectPage() {
		super("Менеджер проектов");

		btnAdd.addClickListener(e -> UI.getCurrent().addWindow(new ProjectWindow(WinMode.INSERT)));
		btnEdit.addClickListener(e -> UI.getCurrent().addWindow(new ProjectWindow(WinMode.UPDATE)));		
		btnDel.addClickListener(e -> UI.getCurrent().addWindow(new ProjectWindow(WinMode.DELETE)));				
	}

	@Override
	protected Component getCentral() {
		List<Organization> items = new ArrayList<>();
		items.add(new Organization("Завод", new Date(), new Date(), null));
		items.add(new Organization("Банк", new Date(), new Date(), null));
		
		VerticalSplitPanel gridPanel = new VerticalSplitPanel();
		
		VerticalLayout projectLayout = new VerticalLayout();
		TreeGrid<Project> grid = new TreeGrid<>();
		grid.setCaption("Список проектов");
		grid.setWidth("100%");
		Set<Project> list1 = new HashSet<>();
		Project project1 = new Project("первый проект", new Date(), new Date(), 0.37d, ProjectStates.CREATING);
		project1.setDescription("<html><body><h1>Первый проект<h1></body></html>");
		Project project2 = new Project("второй проект", new Date(), new Date(), 0.37d, ProjectStates.CREATING);
		list1.add(project1);
		list1.add(project2);
		items.get(0).setProjectList(list1);
		Set<Project> list2 = new HashSet<>();
		list2.add(new Project("третий проект", new Date(), new Date(), 0.37d, ProjectStates.CREATING));
		list2.add(new Project("четвертый проект", new Date(), new Date(), 0.37d, ProjectStates.CREATING));
		items.get(1).setProjectList(list2);
		TreeData<Project> data = new TreeData<>();
		Project parent1 = new Project(items.get(0));
		Project parent2 = new Project(items.get(1));
		data.addItems(null, parent1);
		data.addItems(null, parent2);
		data.addItems(parent1, items.get(0).getProjectList());
		data.addItems(parent2, items.get(1).getProjectList());
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
		
		Set<Skill> skillItems = new HashSet<>();
		skillItems.add(new Skill("Java", null));
		Set<Bug> bugItems = new HashSet<>();
		bugItems.add(new Bug("Баг 1", true));
		bugItems.add(new Bug("Баг 2", false));
		Set<Log> logItems = new HashSet<>();
		logItems.add(new Log(new Date(), new Date(), "Первая запись"));
		Set<Diary> obstacleItems = new HashSet<>();
		Diary obstacle1 = new Diary("Первая запись", new Date() ,"<html><body><h1>Первая запись</h1></body></html>", null);
		obstacle1.setSkillList(skillItems);
		obstacleItems.add(obstacle1);
		project1.setSkillList(skillItems);
		project1.setBugList(bugItems);
		project1.setLogList(logItems);
		project1.setObstacleList(obstacleItems);
		
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
		tagsList.setItems(project1.getSkillList());
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
		Grid<Bug> bugsGrid = new Grid<>();
		bugsGrid.setItems(project1.getBugList());
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
		Grid<Log> logGrid = new Grid<>();
		logGrid.setItems(project1.getLogList());
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
		ListSelect<Diary> obstacleList = new ListSelect<>();
		obstacleList.setItems(project1.getObstacleList());
		obstacleList.setCaption("Проблемы и решения");
		obstacleList.setWidth("100%");
		obstacleList.setHeight("100%");
		BrowserFrame obstacleView = new BrowserFrame();
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
		obstacleView.setSource(new StreamResource(
				() -> new ByteArrayInputStream(obstacle1.getContent().getBytes()), 
				"temp.html")
				);	
		
		tabSheet.setWidth("100%");
		gridPanel.addComponents(projectLayout, tabSheet);
		
		Binder<Project> binder = new Binder<>();
		binder.bind(textEdit, project -> project.getDescription(), (project, description) -> project.setDescription(description));
		binder.bind(htmlEdit, project -> project.getDescription(), (project, description) -> project.setDescription(description));
		binder.readBean(project1);
		tabView.setSource(new StreamResource(() -> new ByteArrayInputStream(project1.getDescription().getBytes()), "temp.html"));	
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
}
