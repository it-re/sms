package scoremanager.main;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Charge;
import bean.Subject;
import bean.Teacher;
import dao.ChargeDao;
import dao.SubjectDao;
import dao.TeacherDao;
import tool.Action;

public class TeacherSubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		/*セッションの取得*/
		HttpSession session =req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//ローカル変数の宣言
		String teacherId = "";
		Map<String ,String> errors = new HashMap<>();

		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		ChargeDao chargeDao = new ChargeDao();
		TeacherDao teacherDao = new TeacherDao();



		// 科目コードから 科目情報を取得
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとに一覧を取得
		List<Charge> teacherSubject = null;
		//リクエストパラメーターの取得

		teacherId = req.getParameter("f1");




		// Chargeのリストを取得（担当教科ごとの情報）
		List<Charge> chargeList = chargeDao.filter(teacher.getSchool());

		// 重複のない教師リストを作成
		Set<Teacher> uniqueTeachers = new LinkedHashSet<>();
		for (Charge charge : chargeList) {
		    uniqueTeachers.add(charge.getTeacher());
		}





		if(teacherId == null || teacherId.equals("0")){
			//エラーメッセージ
			errors.put("e1","担当教師を指定してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);

		}else{
			//DBからリストを取得
			//検索後のリスト
			Teacher selectedTeacher = teacherDao.get(teacherId); // 選択された教師を取得
			teacherSubject = chargeDao.filter(selectedTeacher);
			req.setAttribute("teacher", selectedTeacher);// JSPで表示するためにセット

			//デバック
			System.out.println(teacherSubject);
		}





		//レスポンスをセット
		req.setAttribute("f1", teacherId);

		req.setAttribute("isAdmin", teacher.isAdmin());


		// JSPに渡す
		req.setAttribute("teacher_set", uniqueTeachers);

		req.setAttribute("teacherSubject", teacherSubject);


		// JSPへフォワード 7
		req.getRequestDispatcher("teacher_subject_list.jsp")
			.forward(req, res);
	}

}
