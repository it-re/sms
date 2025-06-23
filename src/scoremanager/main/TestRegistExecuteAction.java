package scoremanager.main;

import java.sql.Connection;
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
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res
	) throws Exception {

		/*セッションの取得*/
		HttpSession session =req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		//ローカル変数の指定
		String entYearStr = "";
		int entYear = 0;
		String classNum = "";
		String subjectStr = "";
		Subject subject = null;
		String countStr = "";
		int count = 0;
		//String pointStr = "";
		//int point = 0;
		//Student student = new Student();
		List<Test> testList = new ArrayList<>();
		Map<String ,String> errors = new HashMap<>();

		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		ClassNumDao classNumDao = new ClassNumDao();
		StudentDao studentDao = new StudentDao();

		//リクエストパラメーターの取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");
		countStr= req.getParameter("f4");


		//ビジネスロック
		entYear = Integer.parseInt(entYearStr);
		count = Integer.parseInt(countStr);
		subject = subjectDao.get(subjectStr, teacher.getSchool());
		//point = Integer.parseInt(pointStr);

		//デバック
		System.out.println("entYearStr" + entYearStr);

		// 生徒一覧を取得
		 List<Student> studentList = studentDao.filter(teacher.getSchool(), entYear, classNum, true);





		 Connection connection = null;
		 try {
		     connection = testDao.getConnection(); // 1回だけ取得

		     for (Student student : studentList) {
		         String paramName = "point_" + student.getNo();
		         String pointStr = req.getParameter(paramName);

		         int point = 0;
		         boolean valid = true;

		         if (pointStr != null && !pointStr.isEmpty()) {
		             try {
		                 point = Integer.parseInt(pointStr);
		                 if (point < 0 || point > 100) {
		                     valid = false;
		                 }
		             } catch (NumberFormatException e) {
		                 valid = false;
		             }

		             if (!valid) {
		                 errors.put(student.getNo(), "0～100の範囲で入力してください");
		                 continue; // エラーがある場合は保存しない
		             }

		             Test test = new Test();
		             test.setStudent(student);
		             test.setSubject(subject);
		             test.setSchool(teacher.getSchool());
		             test.setClassNum(classNum);
		             test.setNo(count);
		             test.setPoint(point);

		             boolean saved = testDao.save(test, connection);

		           //デバック
		 			System.out.println("point: " + point);
		 			System.out.println("保存成功？: " + saved);
		 			System.out.println("errors: " + errors);


		             if (!saved) {
		                 errors.put(student.getNo(), "保存に失敗しました");
		             }
		         }
		     }


		     if (errors.isEmpty()) {
		         //connection.commit();
		         req.getRequestDispatcher("test_regist_done.jsp"
		        		).forward(req, res);
		     } else {
		         //connection.rollback();
		         req.setAttribute("errors", errors);
		         req.getRequestDispatcher("test_regist.jsp")
		         	.forward(req, res);
		     }




		 } catch (Exception e) {
		     if (connection != null) connection.rollback();
		     throw e;
		 } finally {
		     if (connection != null) connection.close();
		 }

	}
}