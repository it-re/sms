package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao {

	public String baseSql = "SELECT student.no as student_no, student.name, test.subject_cd, student.school_cd, test.no, test.point, student.class_num, student.ent_year FROM student left join (select * from test where subject_cd = ? and no = ?) as test on student.no = test.student_no";

	public Test get(Student student, Subject subject, School school, int no) throws Exception {

		//testインスタンス初期化
		Test test = new Test();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;


		try{
			//プリペアードステートメントにSQL分をセット
			statement = connection.prepareStatement
				("select * from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ? ");
			//プリペアードステートメントに値をバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			//DAOを初期化
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()){
				//リザルトセットが存在する場合
				//テストインスタンスに検索結果をセット
				test.setStudent(studentDao.get(resultSet.getString("student_no")));
				test.setSubject(subjectDao.get(resultSet.getString("subject_cd"), school));
				test.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			}else{
				//リザルトセットが存在しない場合
				//テストインスタンスにnullをセット
				test = null;
			}

		}catch (Exception e){
			throw e;
		}finally{
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

		return test;
	}

	private List<Test> postFilter(ResultSet rSet, School school
		)throws Exception{

		//リストを初期化
		List<Test> list = new ArrayList<>();

		try {
			//リザルトセットを全件走査
			while (rSet.next()){
				//testインスタンスを初期化
				Test test = new Test();

				//DAOを初期化
				StudentDao studentDao = new StudentDao();
				SubjectDao subjectDao = new SubjectDao();
				SchoolDao schoolDao = new SchoolDao();


				//testインスタンスに検索結果をセット
				//STUDENT_No, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				//リストに追加
				list.add(test);
			}
		}catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}

		return list;
	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school
	)throws Exception{

		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の条件
		String condition = " where student.ent_year = ? and student.class_num = ? and student.school_cd = ?";
		// SQL文のソート
		String order = " order by student.no asc";

		try{
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement
					(baseSql + condition + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, subject.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, num);
			// プリペアードステートメントにクラス番号をバインド
			statement.setInt(3, entYear);
			//科目をバインド
			statement.setString(4,classNum);
			//回数をバインド
			statement.setString(5,school.getCd());

			// プリペアードステートメントを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);

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

	public boolean save(List<Test> list)throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try{
			//プリペアードステートメントにinsert文をセット
			statement = connection.prepareStatement
					( "insert into test (student_no, subject_cd, school_cd, no, point, class_num) values (?, ?, ?, ?, ?, ?)");

			for (Test test : list) {
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
				count += statement.executeUpdate();
			}
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

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	public boolean save(Test test, Connection connection) throws Exception {


		// プリペアードステートメント
		PreparedStatement statement = null;
	    int count = 0;

	    try {
	        // 既存データの確認
	        Test existing = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());

	        if (existing == null) {
	            // INSERT
	            statement = connection.prepareStatement(
	                "insert into test (student_no, subject_cd,school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)"
	            );
	            statement.setString(1, test.getStudent().getNo());
	            statement.setString(2, test.getSubject().getCd());
	            statement.setString(3, test.getSchool().getCd());
	            statement.setInt(4, test.getNo());
	            statement.setInt(5, test.getPoint());
	            statement.setString(6, test.getClassNum());
	        } else {
	            // UPDATE
	            statement = connection.prepareStatement(
	                "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?"
	            );
	            statement.setInt(1, test.getPoint());
	            statement.setString(2, test.getClassNum());
	            statement.setString(3, test.getStudent().getNo());
	            statement.setString(4, test.getSubject().getCd());
	            statement.setString(5, test.getSchool().getCd());
	            statement.setInt(6, test.getNo());
	        }

	        count = statement.executeUpdate();
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

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}
