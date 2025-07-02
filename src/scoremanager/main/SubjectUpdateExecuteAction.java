package scoremanager.main;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ChargeDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.Action;


public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定
		String subject_cd = ""; //科目コード
		String subject_name = ""; //科目名
		String teacher_id = "";
		Teacher chargeteacher = new Teacher();
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();
		ChargeDao chargeDao = new ChargeDao();
		TeacherDao teacherDao = new TeacherDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		//リクエストパラメーターの取得
		subject_cd = req.getParameter("cd");
		subject_name = req.getParameter("name");
		teacher_id = req.getParameter("teacher");

		if (subjectDao.get(subject_cd, teacher.getSchool())== null) { // 科目コードが重複している場合
			errors.put("1", "科目が存在していません");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			// subjectに科目情報をセット
			subject.setCd(subject_cd);
			subject.setName(subject_name);
			subject.setSchool(teacher.getSchool());

			// saveメソッドで情報を登録
			subjectDao.save(subject);
			if (teacher_id.equals("")){
				chargeDao.delete(subject, teacher.getSchool());
			} else {
				chargeteacher = teacherDao.get(teacher_id);
				chargeDao.save(subject, chargeteacher);
			}

		}

		//リクエストに値をセット
		req.setAttribute("cd", subject_cd);
		req.setAttribute("name", subject_name);

		// JSPへフォワード
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
		}
	}

}
