package org.zttc.itat.model2;
/**
 * DTO对象没有存储的意义，仅仅是用来进行数据的传输的
 * @author Administrator
 *
 */
public class StudentDto {
	private Student stu;
	private Classroom cla;
	private Special spe;
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	public Classroom getCla() {
		return cla;
	}
	public void setCla(Classroom cla) {
		this.cla = cla;
	}
	public Special getSpe() {
		return spe;
	}
	public void setSpe(Special spe) {
		this.spe = spe;
	}
	
	
}
