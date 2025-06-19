package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/*セッションの取得*/
		HttpSession session =req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//ローカル変数の宣言
		String entYearStr = "";
		String classNum = "";
		int entYear = 0;
		String subject = "";
		String countStr = "";
		int count = 0;
		String studentNo = "";
		String studentName = "";
		List<Test> test = null;
		Map<String ,String> errors = new HashMap<>();

		//DAOを初期化
		Student student = new Student();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();

		// 科目コードから 科目情報を取得
		Subject subjectObj = subjectDao.get(subject, teacher.getSchool());


		//リクエストパラメーターの取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subject = req.getParameter("f3");
		countStr= req.getParameter("f4");
		studentNo = req.getParameter(student.getNo());
		studentName = req.getParameter(student.getName());

		//ビジネスロック
		if(entYearStr != null){
			entYear = Integer.parseInt(entYearStr);
		}
		if(countStr != null){
			count = Integer.parseInt(countStr);
		}

		//DBからデータを取得
		//ログインユーザの学校コードをもとに科目番号の一覧を取得
//		List<Test> list = testDao.filter(entYear, classNum, subjectObj, count, teacher.getSchool());

		//レスポンス値をセット

		if(entYear == 0 && classNum == null && subject == null && count == 0){

		}else if (entYear != 0 && classNum != null && subject != null && count != 0){
			//DBからリストを取得
			//検索後のリスト
			test = testDao.filter(entYear, classNum, subjectObj, count,teacher.getSchool());

		}else {
			//エラーメッセージ
			errors.put("e1","全部の項目を指定してください");

		}


		req.setAttribute("ent_year_set", entYear);
		req.setAttribute("f2",classNum);
		req.setAttribute("student_no",studentNo);
		req.setAttribute("student",studentName);

		req.setAttribute("test",test);

		// JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

}
