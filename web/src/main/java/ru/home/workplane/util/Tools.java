package ru.home.workplane.util;

import com.vaadin.icons.VaadinIcons;

import ru.home.workplane.enums.ProjectStates;

public class Tools {
	public static final String SHORT_DATE_FORMAT = "dd.MM.yyyy";
	public static final String LONG_DATE_FORMAT = "dd.MM.yyyy HH:mm";	
	
	public static VaadinIcons getResource(ProjectStates state) {
		VaadinIcons icon = null;
		if(state == ProjectStates.PLANING) {
			icon = VaadinIcons.LINE_BAR_CHART;
		} else if(state == ProjectStates.CREATING) {
			icon = VaadinIcons.EDIT;
		} else if(state == ProjectStates.TESTING) {
			icon = VaadinIcons.QUESTION;
		} else if(state == ProjectStates.REFACTORING) {
			icon = VaadinIcons.MAGIC;
		} else if(state == ProjectStates.RELEASE) {
			icon = VaadinIcons.DIAMOND;
		} else if(state == ProjectStates.ABORTED) {
			icon = VaadinIcons.TRASH;
		} else {
			icon = VaadinIcons.OFFICE;
		}
		return icon;
	}
}
