package scoremanager.main;

import java.sql.Connection;
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
		Map<String ,String> mis = new HashMap<>();

		//DAOを初期化
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
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
		 studentList.addAll(studentDao.filter(teacher.getSchool(), entYear, classNum, false));




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
		                 mis.put(student.getNo(), "0～100の範囲で入力してください");
		                 continue; // エラーがある場合は保存しない
		             }else{
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

		             }
		         }
		     }


		     if (mis.isEmpty()) {
		         req.getRequestDispatcher("test_regist_done.jsp"
		        		).forward(req, res);
		     } else {
		         req.setAttribute("mis", mis);
		         req.setAttribute("f1", entYearStr);
		         req.setAttribute("f2", classNum);
		         req.setAttribute("f3", subjectStr);
		         req.setAttribute("f4", countStr);
		         req.getRequestDispatcher("TestRegist.action")
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