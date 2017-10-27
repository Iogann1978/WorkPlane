package ru.home.workplane.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import ru.home.workplane.ui.view.BookPage;
import ru.home.workplane.ui.view.DiaryPage;
import ru.home.workplane.ui.view.OrganizationPage;
import ru.home.workplane.ui.view.ProjectPage;
import ru.home.workplane.ui.view.SkillPage;
import ru.home.workplane.ui.view.StartPage;
import ru.home.workplane.ui.window.LoginWindow;

@Theme("valo")
public class WorkPlaneUI extends UI {
	private static final long serialVersionUID = 1L;
	private Navigator navigator;
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		navigator = new Navigator(this, this);
		navigator.addView("start", new StartPage());
		navigator.addView("skill", new SkillPage());
		navigator.addView("diary", new DiaryPage());
		navigator.addView("projects", new ProjectPage());
		navigator.addView("organization", new OrganizationPage());
		navigator.addView("book", new BookPage());
		navigator.navigateTo("start");
		UI.getCurrent().addWindow(new LoginWindow());
    }

    @WebServlet(urlPatterns = "/*", name = "WorkPlaneUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WorkPlaneUI.class, productionMode = false)
    public static class WorkPlaneUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
}
