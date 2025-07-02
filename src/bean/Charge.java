package bean;

import java.io.Serializable;

public class Charge implements Serializable {
	private Teacher teacher;
	private Subject subject;
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}



}
