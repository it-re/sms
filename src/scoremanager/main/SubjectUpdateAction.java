package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import tool.Action;
import bean.School;
import dao.SubjectDao;

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
		SubjectDao subjectDao = new SubjectDao();

		/* 科目コードを取得する */
		cd = req.getParameter("cd");

		/* 科目名を取得する */
		name = req.getParameter("name");

		/* データを取得する */

		/* リストを取得する */
		List<Subject>

		/* beanで受け取ったデータをローカル変数に代入 */


		/* リクエストにそれぞれの値を埋め込む */
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		//JSPへフォワード
		req.getRequestDispatcher("subject_update.jsp");
	}

}
