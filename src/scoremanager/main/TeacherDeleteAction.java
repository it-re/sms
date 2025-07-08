package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Charge;
import bean.Teacher;
import dao.ChargeDao;
import dao.TeacherDao;
import tool.Action;

public class TeacherDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションから教員情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        ChargeDao chargeDao=new ChargeDao();
        List<Charge> chargeList = new ArrayList<>();
        String cauction = "";

        // リクエストから教師IDを取得
        String id = req.getParameter("id");

        // DAOで教師情報を取得
        TeacherDao teacherDao = new TeacherDao();
        Teacher deleteTeacher = teacherDao.get(id);


        chargeList = chargeDao.filter(deleteTeacher);

        if(chargeList.size() > 0) {
        	cauction = "この教師には担当科目が設定しています。"
        			+ "教師を削除すると、自動的に該当科目の担当教師の割り当てが解除されます。";
        }

        // JSPに科目情報をセット
        req.setAttribute("teacher", deleteTeacher);
        req.setAttribute("cauction", cauction);

        // 削除確認画面へフォワード
        req.getRequestDispatcher("teacher_delete.jsp").forward(req, res);
    }
}
