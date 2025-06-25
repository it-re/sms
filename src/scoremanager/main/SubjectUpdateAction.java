package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;


public class SubjectUpdateAction extends Action {

	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */

		HttpSession session = req.getSession();

		Teacher teacher = (Teacher)session.getAttribute("user");

		/* 必要なローカル変数の定義 */

		String cd = ""; //科目コード

		String name = ""; //科目名

		School school = new School();

		Subject subject = new Subject();

		SubjectDao subjectDao = new SubjectDao();

		Map<String, String> errors = new HashMap<>();

		/* 科目コードを取得する */

		cd = req.getParameter("cd");

		/* 科目名を取得する */

		name = req.getParameter("name");

		//学校を取得

//		school = req.getParameter(schoolDao.teacher.);

		school = teacher.getSchool();

		/* 1件のデータを取得する */

		subject = subjectDao.get(cd, school);

		// nullチェック

		if (subject == null) {

			errors.put("1", "科目が存在していません");

			req.setAttribute("errors", errors);

		} else {

			/* beanで受け取ったデータをローカル変数に代入 */

			cd = subject.getCd();

			name = subject.getName();

		}

		/* リストを取得する */

//		List<Subject> subjectList = subjectDao.filter(school);



		/* リクエストにそれぞれの値を埋め込む */

		req.setAttribute("cd", cd);

		req.setAttribute("name", name);

		//JSPへフォワード

		req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}

}