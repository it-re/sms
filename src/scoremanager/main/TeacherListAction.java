package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		/* ローカル変数の宣言 */
		List<Teacher> teacherlist = null;
		TeacherDao teacherDao = new TeacherDao();
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ

		/* データベースからの情報取得 */
		teacherlist = teacherDao.filter(teacher.getSchool());

		// リクエストに科目リストをセット
		req.setAttribute("teacherlist", teacherlist);
		req.setAttribute("isAdmin", teacher.isAdmin());
		//ログインユーザーのIDを取得
		req.setAttribute("login_user", teacher.getId());



		//JSPへフォワード
		req.getRequestDispatcher("teacher_list.jsp").forward(req, res);
	}

}
