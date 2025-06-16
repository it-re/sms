package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		/* ローカル変数の宣言 */
		List<Subject> subjects = null; //科目リスト
		SubjectDao subjectDao = new SubjectDao(); //科目Dao
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ

		/* データベースからの情報取得 */
		subjects = subjectDao.filter(teacher.getSchool());

		// リクエストに科目リストをセット
		req.setAttribute("subjects", subjects);



		//JSPへフォワード
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}

}
