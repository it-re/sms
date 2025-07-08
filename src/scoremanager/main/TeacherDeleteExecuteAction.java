  package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定
		String teacher_id = ""; //科目コード
		Teacher deleteTeacher=new Teacher();
		TeacherDao teacherDao = new TeacherDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		//リクエストパラメーターの取得
		teacher_id = req.getParameter("teacher_id");


		if (teacherDao.get(teacher_id)== null) { // 科目コードが重複している場合
			errors.put("1", "教師が存在していません");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			// subjectに科目情報をセット
			deleteTeacher.setId(teacher_id);
			deleteTeacher.setSchool(teacherDao.get(teacher_id).getSchool());

			// saveメソッドで情報を登録
			teacherDao.delete(deleteTeacher);
		}



		// JSPへフォワード
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("teacher_delete_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("TeacherDelete.action").forward(req, res);
		}
	}
}
