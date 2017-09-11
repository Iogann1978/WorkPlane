package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ParagraphWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textNumber, textTitle, valuePage;
	private Button btnPlus, btnMinus;

	public ParagraphWindow(WinMode mode) {
		super(mode, "параграф");
		setHeight("320px");
		setWidth(Tools.SHORT_WIDTH);
	}
	
	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textNumber = new TextField("Номер параграфа");
		textNumber.setWidth("100%");
		textTitle = new TextField("Название параграфа");
		textTitle.setWidth("100%");
		
		HorizontalLayout pageLayout = new HorizontalLayout();
		btnMinus = new Button();
		btnMinus.setIcon(VaadinIcons.MINUS);
		btnMinus.setHeight("30px");
		valuePage = new TextField("Номер страницы");
		valuePage.setValue("1");
		btnPlus = new Button();
		btnPlus.setIcon(VaadinIcons.PLUS);
		btnPlus.setHeight("30px");
		pageLayout.addComponents(btnMinus, valuePage, btnPlus);
		
		layout.addComponents(textNumber, textTitle, pageLayout);
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textNumber.setReadOnly(true);
		textTitle.setReadOnly(true);
		valuePage.setReadOnly(true);
		btnPlus.setEnabled(false);
		btnMinus.setEnabled(false);
	}

}
