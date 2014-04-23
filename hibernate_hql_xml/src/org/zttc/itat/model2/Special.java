package org.zttc.itat.model2;

import java.util.Set;

public class Special {
	private int id;
	private String name;
	private String type;
	private Set<Classroom> clas;
	public Special() {
	}
	public Special(String name, String type) {
		this.name = name;
		this.type = type;
	}
	public Special(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<Classroom> getClas() {
		return clas;
	}
	public void setClas(Set<Classroom> clas) {
		this.clas = clas;
	}
	
	
}
