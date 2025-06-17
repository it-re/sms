package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	private String baseSql = "SELECT * FROM TEST WHERE STUDENT_NO = ?";

	//postFilterメソッド -
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

		// リストを宣言
		List<TestListStudent> list = new ArrayList<>();

		// SchoolDao, SubjectDaoを宣言
		SchoolDao schoolDao = new SchoolDao();
		SubjectDao subjectDao = new SubjectDao();

		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// リザルトセットから学校番号を取得し
				// SchoolDaoのgetメソッドでSchoolを取得
				School school = schoolDao.get(rSet.getString("SCHOOL_CD"));
				System.out.println(rSet.getString("NO") + school);
				// SubjectDaoのgetメソッドの引数にリザルトセットの科目コードと先ほどのSchoolを指定
				// これで科目名を取得する
				String subjectName = subjectDao.get(rSet.getString("SUBJECT_CD"), school).getName();

				// 学生別成績一覧インスタンスを初期化
				TestListStudent testListStudent = new TestListStudent();
				// 学生別成績一覧インスタンスに検索結果をセット
				testListStudent.setSubjectName(subjectName);
				testListStudent.setSubjectCd(rSet.getString("SUBJECT_CD"));
				testListStudent.setNum(rSet.getInt("NO"));
				testListStudent.setPoint(rSet.getInt("POINT"));
				// リストに追加
				list.add(testListStudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;

	}

	//filterメソッド -
	public List<TestListStudent> filter(Student student) throws Exception {
		// リストを宣言
		List<TestListStudent> list = new ArrayList<>();

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql);
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, student.getNo());
			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}
