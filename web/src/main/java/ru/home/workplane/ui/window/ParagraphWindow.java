package ru.home.workplane.ui.window;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.model.Paragraph;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ParagraphWindow extends AbstractWindow<Paragraph> {
	private static final long serialVersionUID = 1L;
	private TextField textNumber, textTitle;
	private NumericLayout pageLayout;

	public ParagraphWindow(Paragraph selectedItem, WinMode mode) {
		super(selectedItem, mode, "параграф");
		setHeight("330px");
		setWidth(Tools.SHORT_WIDTH);
	}
	
	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textNumber = new TextField("Номер параграфа");
		textNumber.setWidth("100%");
		textNumber.setValue(getSelectedItem().getNumber());
		textTitle = new TextField("Название параграфа");
		textTitle.setWidth("100%");
		textTitle.setValue(getSelectedItem().getTitle());
		
		pageLayout = new NumericLayout("Номер страницы");
		pageLayout.setValue(getSelectedItem().getPage());
		
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

	@Override
	protected Paragraph getItem() {
		Paragraph selectedItem = getSelectedItem();
		selectedItem.setNumber(textNumber.getValue());
		selectedItem.setTitle(textTitle.getValue());
		selectedItem.setPage(selectedItem.getPage());
		return selectedItem;
	}

}
