package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.beans.Beans;
import ru.home.workplane.util.Tools;

public class LoginWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;

	public LoginWindow() {
		super();
		setModal(true);
		setCaption("Вход");
		setHeight("250px");
		setWidth(Tools.SHORT_WIDTH);
		setIcon(VaadinIcons.USER);			
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		TextField txtLogin = new TextField("Имя пользователя");
		txtLogin.setWidth("100%");
		PasswordField txtPassword = new PasswordField("Пароль");
		txtPassword.setWidth("100%");
		
		btnOK.addClickListener(e -> {
			if(Beans.login(txtLogin.getValue(), txtPassword.getValue())) {
				close();
			}
			else {
				Notification.show("Неправильно имя пользователя или пароль", Type.ERROR_MESSAGE);
				UI.getCurrent().close();
			}
		});
		btnCancel.addClickListener(e -> UI.getCurrent().close());
		
		layout.addComponents(txtLogin, txtPassword);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		// TODO Auto-generated method stub
	}
}
