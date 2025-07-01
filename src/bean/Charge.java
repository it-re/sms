package bean;

import java.io.Serializable;

public class Charge implements Serializable {
	private String teacherId;
	private String subjectCd;
	private String teacherName;
	private String subjectName;
	public String getTeacherNo() {
		return teacherId;
	}
	public void setTeacherNo(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getSubjectCd() {
		return subjectCd;
	}
	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

}
