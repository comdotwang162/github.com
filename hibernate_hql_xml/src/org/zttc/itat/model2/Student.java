package org.zttc.itat.model2;

public class Student {
	private int id;
	private String name;
	private String sex;
	/**
	 * 增加两个冗余来设计班级信息
	 */
	private String claBh;
	private String claName;
	
	private String speBh;
	private String speName;
	public Student() {
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
