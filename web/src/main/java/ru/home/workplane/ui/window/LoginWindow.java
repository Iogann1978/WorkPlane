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

public class LoginWindow extends AbstractWindow<String> {
	private static final long serialVersionUID = 1L;
	private TextField textLogin;

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
		textLogin = new TextField("Имя пользователя");
		textLogin.setWidth("100%");
		PasswordField textPassword = new PasswordField("Пароль");
		textPassword.setWidth("100%");
		
		btnOK.addClickListener(e -> {
			if(Beans.login(textLogin.getValue(), textPassword.getValue())) {
				close();
			}
			else {
				Notification.show("Неправильное имя пользователя или пароль", Type.ERROR_MESSAGE);
				UI.getCurrent().close();
			}
		});
		btnCancel.addClickListener(e -> UI.getCurrent().close());
		
		layout.addComponents(textLogin, textPassword);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
	}

	@Override
	protected String getItem() {
		return textLogin.getValue();
	}
}
