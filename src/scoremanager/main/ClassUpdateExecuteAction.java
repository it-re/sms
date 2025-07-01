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


public class ClassUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定
		String class_num = ""; //クラス
		String newclassnum = "";
		ClassNum classnum = new ClassNum();
		ClassNumDao classnumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ


		//リクエストパラメーターの取得
		newclassnum = req.getParameter("newclassnum");
		class_num = req.getParameter("classnum");

		try {
			Integer.parseInt(newclassnum);
			if (classnumDao.get(class_num, teacher.getSchool())== null) { // クラスが重複している場合
				errors.put("1", "クラスが存在していません");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
			} else if(classnumDao.get(newclassnum, teacher.getSchool())!= null){
				errors.put("1", "クラス番号が重複しています");
				req.setAttribute("errors", errors);
			} else {
				// subjectに科目情報をセット
				classnum.setClass_num(class_num);
				classnum.setSchool(teacher.getSchool());

				// saveメソッドで情報を登録
				classnumDao.save(classnum, newclassnum);
			}
		} catch(Exception e) {
			errors.put("1", "クラス番号は数値で入力してください");
			req.setAttribute("errors", errors);
		}

		//リクエストに値をセット
		req.setAttribute("newclassnum", newclassnum);
		req.setAttribute("classnum", class_num);

		// JSPへフォワード
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("class_update_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("class_update.jsp").forward(req, res);
		}
	}

}
