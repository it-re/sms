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

		/**ローカル変数の指定**/
		String id = ""; // 教師ID
		String name= ""; // 教師名
		boolean isAdmin = false; // 管理者権限フラグ
		Teacher teacherData = new Teacher();//bean_Teacher
		TeacherDao teacherDao = new TeacherDao();//TeacherDao

		// リクエストパラメーターの取得
		id = req.getParameter("id");


		// DBからデータ取得
		teacherData = teacherDao.get(id);
		/** 現在ログインしている教師のIDをString型で取得 **/
		String loginId = teacher.getId();

		/** beanのteacherDataから取得 **/
		name = teacherData.getName();
		isAdmin = teacherData.isAdmin();

		/**レスポンス値をセット**/
		/**リクエストに教師IDをセット**/
		req.setAttribute("id", id);
		/** リクエストに教師名をセット**/
		req.setAttribute("name", name);
		/**リクエストに管理者権限をセット**/
		req.setAttribute("is_admin", isAdmin);
		/** ログインしている教師のIDをセット **/
		req.setAttribute("LoginId", loginId);


		// JSPへフォワード
		req.getRequestDispatcher("teacher_update.jsp")
			.forward(req, res);
	}

}
