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

public class TestDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		/* セッションからユーザーデータを取得 */
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr = req.getParameter("entYear");
		int entYear = Integer.parseInt(entYearStr);
		String classNum = req.getParameter("classNum");
		String studentNo = req.getParameter("student_no");
		String subjectCd = req.getParameter("subject_cd");
		String testNoStr = req.getParameter("test_no");
		int testNo = Integer.parseInt(testNoStr);
		String testPointStr = req.getParameter("test_point");
		int testPoint = Integer.parseInt(testPointStr);
		//Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		Subject subject = new Subject();
		Test test = new Test();
		Student student = new Student();


		//デバック
		System.out.println("入学年度文字列" + entYearStr);
		System.out.println("入学年度"+ entYear);


		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		StudentDao studentDao = new StudentDao();

		 List<Student> studentList = studentDao.filter(teacher.getSchool(), entYear, classNum, true);
		 studentList.addAll(studentDao.filter(teacher.getSchool(), entYear, classNum, false));

		 subject = subjectDao.get(subjectCd, teacher.getSchool());
		 student = studentDao.get(studentNo);


		// testに科目情報をセット
		test.setStudent(student);
		test.setClassNum(classNum);
		test.setSubject(subject);
		test.setSchool(teacher.getSchool());
		test.setNo(testNo);
		test.setPoint(testPoint);
		// deleteメソッドで情報を削除
		testDao.delete(test);

		//リクエストに値をセット
		//req.setAttribute(, arg1);

		// 登録画面にフォワード
		req.getRequestDispatcher("test_delete_done.jsp")
			.forward(req, res);
	}

}
