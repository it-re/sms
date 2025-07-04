package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		/* ローカル変数の宣言 */
		List<String> classlist = null; //クラスリスト
		ClassNumDao classNumDao = new ClassNumDao(); //クラスDao
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ

		/* データベースからの情報取得 */
		 classlist = classNumDao.filter(teacher.getSchool());

		// リクエストに科目リストをセット
		req.setAttribute("classlist", classlist);
		req.setAttribute("isAdmin", teacher.isAdmin());



		//JSPへフォワード
		req.getRequestDispatcher("class_list.jsp").forward(req, res);
	}

}
