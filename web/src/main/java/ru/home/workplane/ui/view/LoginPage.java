package ru.home.workplane.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.beans.Beans;

public class LoginPage extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public LoginPage() {
		super();
		setCaption("Вход");
		
		TextField txtLogin = new TextField("Имя пользователя");
		txtLogin.setWidth("100%");
		PasswordField txtPassword = new PasswordField("Пароль");
		txtPassword.setWidth("100%");
		HorizontalLayout buttonLayout = new HorizontalLayout();
		Button btnOK = new Button("OK");
		Button btnCancel = new Button("Отмена");
		buttonLayout.addComponents(btnOK, btnCancel);
		
		btnOK.addClickListener(e -> {
			if(Beans.login(txtLogin.getValue(), txtPassword.getValue())) {
				getUI().getNavigator().navigateTo("start");
			}
			else {
				Notification.show("Неправильно имя пользователя или пароль", Type.ERROR_MESSAGE);
				UI.getCurrent().close();
			}
		});
		btnCancel.addClickListener(e -> UI.getCurrent().close());
		
		addComponents(txtLogin, txtPassword, buttonLayout);
		setSizeFull();
	}
}
