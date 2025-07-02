package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ChargeDao;
import dao.SubjectDao;
import dao.TeacherDao;
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

		Teacher chargeteacher = new Teacher();

		List<Teacher> teacherlist = new ArrayList<>();

		School school = new School();

		Subject subject = new Subject();

		SubjectDao subjectDao = new SubjectDao();

		ChargeDao chargeDao = new ChargeDao();

		TeacherDao teacherDao = new TeacherDao();

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
		teacherlist = teacherDao.filter(school);



		if (chargeDao.get(subject, teacher.getSchool()) != null) {
			chargeteacher = chargeDao.get(subject, teacher.getSchool()).getTeacher();
			req.setAttribute("chargeteacher", chargeteacher);
		}

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

		req.setAttribute("teacherlist", teacherlist);

		//JSPへフォワード

		req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}

}