package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String baseSql = "select * from test where school_cd = ?";

	private List<TestListSubject> postFilter(ResultSet rSet
	)throws Exception{

		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();

		try {
			//リザルトセットを全件走査
			while (rSet.next()){
				//
				TestListSubject testListSubject = new TestListSubject();
				Map<Integer, Integer> points = new HashMap<>();

				//listに検索結果をセット
				testListSubject.setEntYear(rSet.getInt("ent_year"));
				testListSubject.setStudentNo(rSet.getString("student_no"));
				testListSubject.setStudentName(rSet.getString("student_name"));
				testListSubject.setClassNum(rSet.getString("class_num"));

				// subject_no と point をMapに追加
				int subjectNo = rSet.getInt("subject_no");
				int point = rSet.getInt("point");
				points.put(subjectNo, point);

				// Mapをセット
				testListSubject.setPoints(points);

				// リストに追加
				list.add(testListSubject);

			}
		}catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}

		return list;

	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school
	)throws Exception{
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year = ? and class_num = ? and subject_cd = ? ";
		// SQL文のソート
		String order = " order by no asc";

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement
							(baseSql + condition + order);
			// プリペアードステートメントに値をバインド
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3,subject.getCd());

			// プリペアードステートメントを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet);

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
