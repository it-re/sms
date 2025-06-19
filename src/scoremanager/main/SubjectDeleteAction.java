package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		/* 必要なローカル変数の定義 */
		SubjectDao subjectDao = new SubjectDao();

		/* 科目コードを取得する */
		String cd = req.getParameter("subject_cd");

		/* 科目名を取得する */
		String name = req.getParameter("subject_name");

		/* 削除処理 */
		subjectDao.delete(cd, teacher.getSchool());

		// 削除完了ページへフォワード
	    req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}

}
