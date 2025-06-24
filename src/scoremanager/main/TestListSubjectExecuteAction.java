package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String subjectStr = ""; // 入力された科目
		List<TestListSubject> list = new ArrayList<>();
		int entYear = 0; // 入学年度
		Subject subject = new Subject();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ


		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得

		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		SubjectDao subjectDao = new SubjectDao(); // クラス番号Daoを初期化
		TestListSubjectDao testListSubjectDao=new TestListSubjectDao();

		// リクエストパラメーターの取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");



		entYear = Integer.parseInt(entYearStr);
		subject = subjectDao.get(subjectStr, teacher.getSchool());

//		System.out.println(entYear);
//		System.out.println(classNum);
//		System.out.println(subject);
//		System.out.println(teacher.getSchool().getCd());

		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectStr);

		if(!entYearStr.equals("0") && !classNum.equals("0") && !subjectStr.equals("0")) {
			list=testListSubjectDao.filter(entYear,classNum,subject,teacher.getSchool());
		} else {
			errors.put("1", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("TestList.action").forward(req, res);

		}

		// ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

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


		// リクエストにデータをセット
		req.setAttribute("class_num_set", class_num_set);
		req.setAttribute("subjects", subjects);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject", subject);
		req.setAttribute("list", list);

//		System.out.println(list.get(0).getPoints());

		// JSPへフォワード 7

		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);


	}

}
