package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		 // セッションから教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

		//ローカル変数の指定
		String entYearStr = "";
		int entYear = 0;
		String classNum = "";
		String subjectStr = "";
		Subject subject = null;
		String countStr = "";
		int count = 0;

		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		StudentDao studentDao = new StudentDao();




		//リクエストパラメーターの取得
		String studentNo = req.getParameter("student_no");
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");
		countStr= req.getParameter("f4");


		//ビジネスロック
		entYear = Integer.parseInt(entYearStr);
		count = Integer.parseInt(countStr);
		subject = subjectDao.get(subjectStr, teacher.getSchool());

		// 生徒一覧を取得
		 List<Student> studentList = studentDao.filter(teacher.getSchool(), entYear, classNum, true);
		 studentList.addAll(studentDao.filter(teacher.getSchool(), entYear, classNum, false));
		 List<Test> test = testDao.filter(entYear, classNum, subject, count,teacher.getSchool());


		Test targetTest = null;
		for (Test t : test) {
			if (t.getStudent().getNo().equals(studentNo)) {
				targetTest = t;
				break;
			}
		}

		if (targetTest != null) {
			req.setAttribute("test", targetTest);
		}else {
			// エラー処理
		}




		 // JSPに科目情報をセット
	        req.setAttribute("subject", subject);
	       // req.setAttribute("test",test);


		// JSPへフォワード 7
		req.getRequestDispatcher("test_delete.jsp").forward(req, res);

	}

}
