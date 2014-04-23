package org.zttc.itat.model;

import java.util.Set;

public class Course {
	private int id;
	private String name;
	private Set<TeacherCourse> tcs;
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
	public Set<TeacherCourse> getTcs() {
		return tcs;
	}
	public void setTcs(Set<TeacherCourse> tcs) {
		this.tcs = tcs;
	}
	
}
