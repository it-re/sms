package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ChargeDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.Action;

public class TeacherCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String teacher_pw = "";
		String teacher_name = "";
		String teacher_id = "";
		String isAdminStr = "";
		boolean isAdmin = false;
		Teacher createTeacher = new Teacher();
		SubjectDao subjectDao = new SubjectDao();
		ChargeDao chargeDao = new ChargeDao();
		TeacherDao teacherDao = new TeacherDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		//リクエストパラメーターの取得
		teacher_pw = req.getParameter("pw");
		teacher_name = req.getParameter("name");
		teacher_id = req.getParameter("id");
		isAdminStr = req.getParameter("is_admin");

		if (isAdminStr != null) {
			isAdmin = true;
		}

		if (teacherDao.get(teacher_id)!= null) { // 教師が重複している場合
			errors.put("1", "教師IDが重複しています");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			// subjectに科目情報をセット
			createTeacher.setId(teacher_id);
			createTeacher.setName(teacher_name);
			createTeacher.setPassword(teacher_pw);
			createTeacher.setAdmin(isAdmin);
			createTeacher.setSchool(teacher.getSchool());
			// saveメソッドで情報を登録
			teacherDao.save(createTeacher);
		}

		//リクエストに値をセット
		req.setAttribute("pw", teacher_pw);
		req.setAttribute("name", teacher_name);
		req.setAttribute("id", teacher_id);
		req.setAttribute("is_admin", isAdmin);

		// JSPへフォワード
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("teacher_create_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("TeacherCreate.action").forward(req, res);
		}
	}

}
