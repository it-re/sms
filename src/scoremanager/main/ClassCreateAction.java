package scoremanager.main;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class ClassCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		List<Teacher> teacherlist = new ArrayList<>();
		TeacherDao teacherDao = new TeacherDao();

		teacherlist = teacherDao.filter(teacher.getSchool());

		req.setAttribute("teacherlist", teacherlist);

		//JSPへフォワード
		req.getRequestDispatcher("class_create.jsp").forward(req,res);


	}

}
