package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import dao.TeacherDao;
import tool.Action;


public class ClassUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		/* 必要なローカル変数の定義 */
		String classNumStr = ""; // クラス番号
		ClassNum oldClassNum = new ClassNum();

		// 教師リスト
		List<Teacher> teacherList = new ArrayList<>();

		// DAO
		ClassNumDao classNumDao = new ClassNumDao();
		TeacherDao teacherDao = new TeacherDao();

		/* クラスを取得する */
		classNumStr = req.getParameter("cd");
		oldClassNum = classNumDao.get(classNumStr, teacher.getSchool());

		/* 教師一覧を取得する */
		teacherList = teacherDao.filter(teacher.getSchool());

		/* リクエストにそれぞれの値を埋め込む */
		req.setAttribute("oldClassNum", oldClassNum);
		req.setAttribute("teacherList", teacherList);

		//JSPへフォワード
		req.getRequestDispatcher("class_update.jsp").forward(req, res);

	}

}