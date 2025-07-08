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

public class ClassCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(); // セッション
        Teacher teacher = (Teacher) session.getAttribute("user");

        String teacher_id = "";
        Teacher chargeteacher = new Teacher();
        TeacherDao teacherDao = new TeacherDao();

        Map<String, String> errors = new HashMap<>();
        ClassNumDao classnumDao = new ClassNumDao();

        String class_numStr = req.getParameter("class_num");

        // 入力値をリクエストに保持（JSPに渡す用）
        req.setAttribute("class_num", class_numStr);
        teacher_id = req.getParameter("teacher");

        // バリデーション（1）3文字でなければエラー
        if (class_numStr == null || class_numStr.length() != 3) {
            errors.put("1", "クラス番号は3文字で入力してください。");
        }

        // バリデーション（2）重複チェック
        if (errors.isEmpty() && classnumDao.get(class_numStr, teacher.getSchool()) != null) {
            errors.put("2", "クラス番号が重複しています。");
        }

        // バリデーション（3）数字のみ
		try {
			Integer.parseInt(class_numStr);

		} catch(Exception e) {
			errors.put("3", "クラス番号は数値で入力してください");
			req.setAttribute("errors", errors);
		}

		if (teacher_id.equals("")){
			errors.put("4", "担任を入力してください");
		}
		 chargeteacher = teacherDao.get(teacher_id);

		if (classnumDao.filter(chargeteacher).size() != 0) {
			errors.put("5", "この担任はすでに登録されています");
		}


        // エラーがある場合は入力画面に戻る
        if (!errors.isEmpty()) {
        	req.setAttribute("class_num", class_numStr);
        	req.setAttribute("chargeteacher", chargeteacher);

            req.setAttribute("errors", errors);
            req.getRequestDispatcher("ClassCreate.action").forward(req, res);
            return;
        }

        // 登録処理（問題ない場合）
        ClassNum classNum = new ClassNum();

        classNum.setClass_num(class_numStr);
        classNum.setSchool(teacher.getSchool());
        classNum.setTeacher(chargeteacher);
        classnumDao.save(classNum);

        // 完了画面へ
        req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
    }
}
