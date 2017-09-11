package ru.home.workplane.ui.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class StartPage extends HorizontalLayout implements View {
	private static final long serialVersionUID = 1L;

	public StartPage() {
		super();
		setCaption("WorkPlane 1.0");
		
        VerticalLayout buttonsLayout = new VerticalLayout();
        Button btnSkill = new Button("Опыт");
        btnSkill.setWidth("150px");
        btnSkill.setIcon(VaadinIcons.TAGS);
        Button btnOrganization = new Button("Работа");
        btnOrganization.setWidth("150px");
        btnOrganization.setIcon(VaadinIcons.OFFICE);
        Button btnDiary = new Button("Записи");
        btnDiary.setWidth("150px");
        btnDiary.setIcon(VaadinIcons.NOTEBOOK);
        Button btnProjects = new Button("Проекты");
        btnProjects.setWidth("150px");
        btnProjects.setIcon(VaadinIcons.CALENDAR);
        Button btnResume = new Button("Резюме");
        btnResume.setWidth("150px");
        btnResume.setIcon(VaadinIcons.DIPLOMA);
        Button btnBooks = new Button("Книги");
        btnBooks.setIcon(VaadinIcons.BOOK);
        btnBooks.setWidth("150px");
        buttonsLayout.addComponents(btnSkill, btnOrganization, btnDiary, btnProjects, btnResume, btnBooks);
        buttonsLayout.setWidth("200px");
        
        btnSkill.addClickListener(e -> getUI().getNavigator().navigateTo("skill"));
        btnDiary.addClickListener(e -> getUI().getNavigator().navigateTo("diary"));
        btnProjects.addClickListener(e -> getUI().getNavigator().navigateTo("projects"));
        btnOrganization.addClickListener(e -> getUI().getNavigator().navigateTo("organization"));
        btnBooks.addClickListener(e -> getUI().getNavigator().navigateTo("book"));
        
        BrowserFrame textAbout = new BrowserFrame();
        textAbout.setSource(new ClassResource("/about.html"));
        textAbout.setWidth("100%");
        textAbout.setHeight("100%");
        addComponents(buttonsLayout, textAbout);
        setExpandRatio(textAbout, 1.0f);
        setSizeFull();
	}
}
