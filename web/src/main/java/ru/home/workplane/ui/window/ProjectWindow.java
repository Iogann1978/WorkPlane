package ru.home.workplane.ui.window;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.home.workplane.enums.ProjectStates;
import ru.home.workplane.ui.enums.WinMode;
import ru.home.workplane.util.Tools;

public class ProjectWindow extends AbstractWindow {
	private static final long serialVersionUID = 1L;
	private TextField textName, valuePercent;
	private DateField dateStart, dateEnd;
	private Button btnMinus, btnPlus;
	private ComboBox<ProjectStates> cmbState;

	public ProjectWindow(WinMode mode) {
		super(mode, "проект");
		setHeight("400px");
		setWidth("410px");				
	}

	@Override
	protected Component getCentral() {
		VerticalLayout layout = new VerticalLayout();
		textName = new TextField("Название проекта");
		textName.setWidth("100%");
		
		HorizontalLayout datesLayout = new HorizontalLayout();
		dateStart = new DateField("Дата начала");
		dateStart.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateStart.setWidth("50%");
		dateEnd = new DateField("Дата окончания");
		dateEnd.setDateFormat(Tools.SHORT_DATE_FORMAT);
		dateEnd.setWidth("50%");
		datesLayout.addComponents(dateStart, dateEnd);
		
		HorizontalLayout percentLayout = new HorizontalLayout();
		btnMinus = new Button();
		btnMinus.setIcon(VaadinIcons.MINUS);
		btnMinus.setHeight("30px");
		valuePercent = new TextField("Процентов выполнено");
		valuePercent.setValue("0%");
		btnPlus = new Button();
		btnPlus.setIcon(VaadinIcons.PLUS);
		btnPlus.setHeight("30px");
		percentLayout.addComponents(btnMinus, valuePercent, btnPlus);
		
		cmbState = new ComboBox<>();
		cmbState.setCaption("Состояние проекта");
		cmbState.setItems(ProjectStates.values());
		cmbState.setTextInputAllowed(false);
		cmbState.setSelectedItem(ProjectStates.values()[0]);
		cmbState.setEmptySelectionAllowed(false);
		cmbState.setItemIconGenerator((item) -> Tools.getResource(item));
		cmbState.setWidth("100%");

		layout.addComponents(textName, datesLayout, percentLayout, cmbState);	
		layout.setMargin(false);
		return layout;
	}

	@Override
	protected void setDeleteMode() {
		textName.setReadOnly(true);
		dateStart.setReadOnly(true);
		dateEnd.setReadOnly(true);
		btnPlus.setEnabled(false);
		btnMinus.setEnabled(false);
		cmbState.setReadOnly(true);
	}

}
