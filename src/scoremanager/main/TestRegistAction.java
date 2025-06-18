package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
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
		List<Test> test = null;
		Student student = new Student();
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();//クラス番号DAOの宣言
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();


		// 科目コードから Subject オブジェクトを取得
		Subject subjectObj = subjectDao.get(subject, teacher.getSchool());


		//リクエストパラメーターの取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subject = req.getParameter("f3");
		countStr= req.getParameter("f4");

		//ビジネスロック
		if(entYearStr != null){
			entYear = Integer.parseInt(entYearStr);
		}
		if(countStr != null){
			count = Integer.parseInt(countStr);
		}


		//DBからデータを取得
		//ログインユーザの学校コードをもとに科目番号の一覧を取得
		List<Test> list = testDao.filter();

		if (entYear != 0 && !classNum.equals("0")) {
			// 入学年度とクラス番号を指定
			test = testDao.filter(entYear, classNum, subjectObj, count, teacher.getSchool());



		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
			// 全学生情報を取得
			students = studentDao.filter(teacher.getSchool(), isAttend);
		}



	}

}
