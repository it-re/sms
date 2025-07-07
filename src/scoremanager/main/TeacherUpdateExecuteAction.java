package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");


		// ローカル変数の指定
		String id = "";
		String name = "";
		String password = "";
		String isAdminStr = "";
		boolean isAdmin = false;
		Teacher teacherData = new Teacher();
		TeacherDao teacherDao = new TeacherDao();

		// リクエストパラメーターの取得
		id = req.getParameter("id");
		name = req.getParameter("name");
		password = req.getParameter("password");
		isAdminStr = req.getParameter("is_admin");


		// ビジネスロジック
		if (isAdminStr != null) {
			isAdmin = true;
		}
		// studentに学生情報をセット
		teacherData.setId(id);
		teacherData.setName(name);
		teacherData.setPassword(password);
		teacherData.setSchool(teacher.getSchool());
		teacherData.setAdmin(isAdmin);
		// 変更内容を保存
		teacherDao.save(teacherData);

		// JSPへフォワード
		req.getRequestDispatcher("teacher_update_done.jsp")
			.forward(req, res);
	}
}
