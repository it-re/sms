package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;


public class ClassUpdateAction extends Action {

	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */

		HttpSession session = req.getSession();

		Teacher teacher = (Teacher)session.getAttribute("user");

		/* 必要なローカル変数の定義 */

		String classNumStr = ""; // クラス番号
		ClassNum classNum = new ClassNum();

		ClassNumDao classNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();

		/* クラスを取得する */

		classNumStr = req.getParameter("cd");
		classNum = classNumDao.get(classNumStr, teacher.getSchool());

		/* リクエストにそれぞれの値を埋め込む */

		req.setAttribute("newclassnum", classNumStr);
		req.setAttribute("classnum", classNum);

		//JSPへフォワード

		req.getRequestDispatcher("class_update.jsp").forward(req, res);

	}

}