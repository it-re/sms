package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		List<TestListStudent> testliststudent = new ArrayList<>();
		String student_no = ""; // 入力された生徒番号
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		SubjectDao subjectDao = new SubjectDao(); // クラス番号Daoを初期化
		StudentDao studentDao = new StudentDao();
		Student student = new Student();
		TestListStudentDao testliststudentDao = new TestListStudentDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得 2
		student_no = req.getParameter("f4");
		student = studentDao.get(student_no);



		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から現在まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> class_num_set = classNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目名を取得
		List<Subject> subjects = subjectDao.filter(teacher.getSchool());

		// レスポンス値をセット 6
		// リクエストに入力されたデータをセット
		req.setAttribute("f4", student_no);

		if (student == null) { // 学生情報が存在しない場合
			errors.put("1", "学生情報が存在しませんでした");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			testliststudent = testliststudentDao.filter(student);
			if (testliststudent.size() == 0) { // 学生番号に該当する成績情報が存在しない場合
				errors.put("1", "成績情報が存在しませんでした");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
		}
		}


		// リクエストにデータをセット
		req.setAttribute("class_num_set", class_num_set);
		req.setAttribute("subjects", subjects);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("test_students", testliststudent);
		req.setAttribute("student", student);

		// JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);

	}

}
