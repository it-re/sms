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

		String entYearStr = req.getParameter("test.student.entYear");
		int entYear = Integer.parseInt(entYearStr);
		String classNumStr = req.getParameter("test.student.classNum");
		String studentNo = req.getParameter("student_no");
		String subjectName = req.getParameter("subject_name");
		String testNoStr = req.getParameter("test_no");
		int testNo = Integer.parseInt(testNoStr);
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		Subject subject = new Subject();


		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		StudentDao studentDao = new StudentDao();

		 List<Student> studentList = studentDao.filter(teacher.getSchool(), entYear, classNum, true);



		if (TestDao.get(student, teacher.getSchool())== null) { // 科目コードが重複している場合
			errors.put("1", "科目が存在していません");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			// subjectに科目情報をセット
			subject.setCd(subject_cd);
			subject.setName(subject_name);
			subject.setSchool(teacher.getSchool());
			// saveメソッドで情報を登録
			subjectDao.delete(subject);
		}



	}

}
