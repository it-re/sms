package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Charge;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ChargeDao;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TeacherSubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/*セッションの取得*/
		HttpSession session =req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//ローカル変数の宣言
		String teacherSet = "";
		List<Test> test = null;
		Map<String ,String> errors = new HashMap<>();

		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		ClassNumDao classNumDao = new ClassNumDao();
		ChargeDao chargeDao = new ChargeDao();



		// 科目コードから 科目情報を取得
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());
		List<Charge> teacherList = chargeDao.filter(teacher.getSchool());
		List<Charge> teacherSubject = null;
		//リクエストパラメーターの取得

		teacherSet = req.getParameter("f1");


		//ビジネスロック
		/*
		Subject subjectObj = null;
		if (subject != null && !subject.equals("0") && !subject.isEmpty()) {
			subjectObj = subjectDao.get(subject, teacher.getSchool());
		}
		*/

		/* debug */
//		System.out.println("classNum:" + classNum);

		if(teacherSet == null){
			//エラーメッセージ
			errors.put("e1","担当教師を指定してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);

		}else{
			//DBからリストを取得
			//検索後のリスト
			teacherSubject = chargeDao.filter(teacher);

		}


		req.setAttribute("f1", teacher);

		req.setAttribute("teacher_set", teacherList);

		// JSPへフォワード 7
		req.getRequestDispatcher("teacher_subject_list.jsp")
			.forward(req, res);
	}

}
