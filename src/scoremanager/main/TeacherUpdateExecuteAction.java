package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー


		/** ローカル変数の指定 **/
		String id = "";//教師ID
		String name = "";//教師名
		String password = "";//パスワード
		String isAdminStr = "";//管理者権限String型(True or False)
		boolean isAdmin = false;//管理者権限
		Teacher teacherData = new Teacher();//bean_Teacher
		TeacherDao teacherDao = new TeacherDao();//TeacherDao

		/** リクエストパラメーターの取得 **/
		id = req.getParameter("id");//jspから取得
		name = req.getParameter("name");//jspから取得 ;
		password = req.getParameter("password");
		isAdminStr = req.getParameter("is_admin");//jspから取得


		/** ビジネスロジック **/
		if (isAdminStr != null) {
			isAdmin = true;
		}
		/** teacherDateに教師情報をセット **/
		teacherData.setId(id);//教師ID
		teacherData.setName(name);//教師名
		teacherData.setPassword(password);//パスワード
		teacherData.setSchool(teacher.getSchool());//在籍学校コード
		teacherData.setAdmin(isAdmin);//管理者権限
		/** 変更内容を保存 **/
		/** セットした教師情報をTeacherDaoのセーブクラスを使って保存 **/
		teacherDao.save(teacherData);

		/** JSPへフォワード **/
		req.getRequestDispatcher("teacher_update_done.jsp")
			.forward(req, res);
	}
}
