package ru.home.workplane.model;

import java.io.Serializable;

public class Bug implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private boolean flag;
	
	public Bug() {
		super();
		flag = true;
	}

	public Bug(String name, Boolean flag) {
		super();
		this.name = name;
		this.flag = flag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Bug getBug() {
		return this;
	}
}
