package scoremanager.main;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import dao.TeacherDao;
import tool.Action;


public class ClassUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定
		String newClassNumStr = ""; //クラス
		String oldClassNumStr = ""; //クラス
		String teacherId = "";
		ClassNum newClassNum = new ClassNum();
		ClassNum oldClassNum = new ClassNum();

		ClassNumDao classNumDao = new ClassNumDao();
		TeacherDao teacherDao = new TeacherDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ


		//リクエストパラメーターの取得
		newClassNumStr = req.getParameter("newClassNum");
		oldClassNumStr = req.getParameter("oldClassNum");
		teacherId = req.getParameter("teacher");

		//変更前のクラス情報取得
		oldClassNum = classNumDao.get(oldClassNumStr, teacher.getSchool());



		// newClassNumに入力情報を登録
		newClassNum.setClass_num(newClassNumStr);
		newClassNum.setSchool(teacher.getSchool());
		newClassNum.setTeacher(teacherDao.get(teacherId));

		// 保存とエラー処理
		try {
			// クラス番号が数字か判定
			Integer.parseInt(newClassNumStr);

			if (classNumDao.get(oldClassNumStr, teacher.getSchool()) == null) { // 変更対象クラスが存在しない場合
				errors.put("1", "クラスが存在していません");
			} else if(classNumDao.get(newClassNumStr, teacher.getSchool()) != null && !newClassNumStr.equals(oldClassNum.getClass_num())) { // 記入したクラス番号が重複している場合
				errors.put("1", "クラス番号が重複しています");
			} else if(newClassNumStr == null || newClassNumStr.length() != 3) {
				errors.put("1", "クラス番号は3文字で入力してください。");
			} else {

				// 問題がなければsaveメソッドで情報を登録
				classNumDao.save(oldClassNum, newClassNum);
			}
		} catch(NumberFormatException e) {
			errors.put("1", "クラス番号は数値で入力してください");

		}



		// JSPへフォワード
		if (errors.isEmpty()) {
			// エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("class_update_done.jsp").forward(req, res);
		} else {
			// エラーメッセージがある場合
			// エラーメッセージとnewClassNum・oldClassNum、教師一覧をJSPに渡す
			req.setAttribute("errors", errors);
			req.setAttribute("newClassNum", newClassNum);
			req.setAttribute("oldClassNum", oldClassNum);
			req.setAttribute("teacherList", teacherDao.filter(teacher.getSchool()));
			// 登録画面にフォワード
			req.getRequestDispatcher("class_update.jsp").forward(req, res);
		}
	}

}
