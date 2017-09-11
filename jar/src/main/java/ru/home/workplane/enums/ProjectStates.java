package ru.home.workplane.enums;

public enum ProjectStates {
	PLANING("Планирование"), 
	CREATING("Создание"), 
	TESTING("Тестирование"), 
	REFACTORING("Рефакторинг"), 
	RELEASE("Выпуск"),
	ABORTED("Отменён"),
	ORGANIZATION("Место работы");
	
	private String name;

	private ProjectStates(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
