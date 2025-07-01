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
import bean.Test;
import dao.ClassNumDao;
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
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		//String subject = "";
		List<Test> test = null;
		Map<String ,String> errors = new HashMap<>();

		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		ClassNumDao classNumDao = new ClassNumDao();



		// 科目コードから 科目情報を取得
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}



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

		Subject subjectObj = null;
		if (subject != null && !subject.equals("0") && !subject.isEmpty()) {
			subjectObj = subjectDao.get(subject, teacher.getSchool());
		}


		//DBからデータを取得
		//ログインユーザの学校コードをもとに科目番号の一覧を取得
//		List<Test> list = testDao.filter(entYear, classNum, subjectObj, count, teacher.getSchool());

		//レスポンス値をセット

		/* debug */
//		System.out.println("classNum:" + classNum);
		System.out.println("subject:" + subject);
		System.out.println("SubjectObj:" + subjectObj);

		if(entYear == 0 && classNum == null && subject == null && count == 0){
			//何もしない

		}else if (entYear != 0 && !classNum.equals("0") && !subject.equals("0") && count != 0){
			//DBからリストを取得
			//検索後のリスト
			test = testDao.filter(entYear, classNum, subjectObj, count,teacher.getSchool());

		}else {
			//エラーメッセージ
			errors.put("e1","全部の項目を指定してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);

		}



		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set",list);
		req.setAttribute("subject_cd_set", subjectList);
		//req.setAttribute("count_set", count);

		// subjectListの中身は存在するのか？
		/* 確認用debug code */
//		for (Subject s : subjectList) {
//			System.out.println("debug:" + s.getCd() + ":" + s.getName());
//		}
//
//		System.out.println("debug:" + teacher.getSchool().getCd());
		System.out.println("test:" + test);
		System.out.println("count" + count);

		req.setAttribute("f1", entYear);// 入学年度
		req.setAttribute("f2", classNum);// クラス
		req.setAttribute("f3", subject);// 科目コード
		req.setAttribute("f4", count);// 回数



		if (subjectObj != null) {
			req.setAttribute("subject_name", subjectObj.getName());
		}


		req.setAttribute("test_no", count);

		req.setAttribute("test",test);

		req.setAttribute("subject", subject);


		@SuppressWarnings("unchecked")
		Map<String, String> mis = (Map<String, String>) req.getAttribute("mis");

		// JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

}
