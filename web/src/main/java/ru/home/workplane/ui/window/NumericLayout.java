package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class NumericLayout extends HorizontalLayout {
	private static final long serialVersionUID = 1L;
	private TextField textValue;
	private Button btnPlus, btnMinus;
	
	public NumericLayout(String title) {
		super();
		btnMinus = new Button();
		btnMinus.setIcon(VaadinIcons.MINUS);
		btnMinus.setHeight("30px");
		textValue = new TextField(title);
		btnPlus = new Button();
		btnPlus.setIcon(VaadinIcons.PLUS);
		btnPlus.setHeight("30px");
		addComponents(btnMinus, textValue, btnPlus);
	}

	public int getValue() {
		return Integer.valueOf(textValue.getValue());
	}

	public Button getBtnPlus() {
		return btnPlus;
	}

	public Button getBtnMinus() {
		return btnMinus;
	}
	
	public void setValue(int value) {
		textValue.setValue(String.valueOf(value));
	}
	
	public void setDeleteMode() {
		textValue.setReadOnly(true);
		btnPlus.setEnabled(false);
		btnMinus.setEnabled(false);
	}
}
