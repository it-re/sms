package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		String id = ""; // 教師ID
		String name= ""; // 氏名
		String password = "";//パスワード
		boolean isAdmin = false; // 管理者権限フラグ
		Teacher teacherData = new Teacher();
		TeacherDao teacherDao = new TeacherDao();

		// リクエストパラメーターの取得 2
		id = req.getParameter("id");
		
		
		// DBからデータ取得
		teacherData = teacherDao.get(id);
		
		name = teacherData.getName();
		isAdmin = teacherData.isAdmin();

		// レスポンス値をセット
		// リクエストに学生番号をセット
		req.setAttribute("id", id);
		// リクエストに氏名をセット
		req.setAttribute("name", name);
		// リクエストに在学フラグをセット
		req.setAttribute("is_admin", isAdmin);

		// JSPへフォワード 7
		req.getRequestDispatcher("teacher_update.jsp")
			.forward(req, res);
	}

}
