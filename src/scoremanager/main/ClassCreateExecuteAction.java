package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ
		ClassNumDao classnumDao = new ClassNumDao();

		String class_numStr = req.getParameter("class_num");
		ClassNum classNum = new ClassNum();



		if (class_numStr.length() != 3) {
			errors.put("1", "クラス番号は3文字で入力してください。");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {

			if (classnumDao.get(class_numStr, teacher.getSchool())!= null) { // クラスが重複している場合
				errors.put("2", "クラス番号が重複しています");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
			} else {
				// classNumに科目情報をセット
				classNum.setClass_num(class_numStr);
				classNum.setSchool(teacher.getSchool());
				// saveメソッドで情報を登録
				classnumDao.save(classNum);

				//リクエストに値をセット
				req.setAttribute("class_num", class_numStr);

				// JSPへフォワード
				if (errors.isEmpty()) { // エラーメッセージがない場合
					// 登録完了画面にフォワード
					req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
				} else { // エラーメッセージがある場合
					// 登録画面にフォワード
					req.getRequestDispatcher("class_create.jsp").forward(req, res);
				}
			}
		}
	}

}
