package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ParagraphWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textNumber, textTitle;
	private NumericLayout pageLayout;

	public ParagraphWindow(WinMode mode) {
		super(mode, "параграф");
		setHeight("330px");
		setWidth(Tools.SHORT_WIDTH);
	}
	
	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textNumber = new TextField("Номер параграфа");
		textNumber.setWidth("100%");
		textTitle = new TextField("Название параграфа");
		textTitle.setWidth("100%");
		
		pageLayout = new NumericLayout("Номер страницы");
		pageLayout.setValue(1);
		
		layout.addComponents(textNumber, textTitle, pageLayout);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textNumber.setReadOnly(true);
		textTitle.setReadOnly(true);
		pageLayout.setDeleteMode();
	}

}
