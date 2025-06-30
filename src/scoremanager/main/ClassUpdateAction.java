package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;


public class ClassUpdateAction extends Action {

	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */

		HttpSession session = req.getSession();

		Teacher teacher = (Teacher)session.getAttribute("user");

		/* 必要なローカル変数の定義 */

		String num = ""; // クラス

		School school = new School();

		Subject subject = new Subject();

		SubjectDao subjectDao = new SubjectDao();

		ClassNumDao classnumDao = new ClassNumDao();

		Map<String, String> errors = new HashMap<>();

		/* クラスを取得する */

		num = req.getParameter("cd");

		school = teacher.getSchool();


		/* リクエストにそれぞれの値を埋め込む */

		req.setAttribute("newclassnum", num);
		req.setAttribute("classnum", num);

		//JSPへフォワード

		req.getRequestDispatcher("class_update.jsp").forward(req, res);

	}

}