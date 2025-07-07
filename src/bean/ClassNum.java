package bean;

import java.io.Serializable;

public class ClassNum implements Serializable {

	/**
	 * クラス名:class_num
	 */
	private String class_num;

	/**
	 * 学校:School
	 */
	private School school;

	/**
	 * 教師:Teacher
	 */
	private Teacher teacher;


	/**
	 * ゲッター・セッター
	 */



	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getClass_num() {
		return class_num;
	}

	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}




}
