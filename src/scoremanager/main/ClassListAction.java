package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
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
		List<String> classListStr = null; //クラスリスト
		List<ClassNum> classList = new ArrayList<>(); //クラスリスト
		ClassNumDao classNumDao = new ClassNumDao(); //クラスDao
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ

		/* データベースからの情報取得 */
		 classListStr = classNumDao.filter(teacher.getSchool());

		 for (String classNum : classListStr) {
			 classList.add(classNumDao.get(classNum, teacher.getSchool()));
		 }
		// リクエストに科目リストをセット
		req.setAttribute("classlist", classList);
		req.setAttribute("isAdmin", teacher.isAdmin());



		//JSPへフォワード
		req.getRequestDispatcher("class_list.jsp").forward(req, res);
	}

}
