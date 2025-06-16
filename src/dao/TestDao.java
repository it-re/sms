package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;


public class TestDao extends Dao {

	public String baseSql = "select * from test where student_no = ?";

	public Test get(Student student, Subject subject, School school, int no) throws Exception {

		//testインスタンス初期化
		Test test = new test();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// プリペアードステートメントを実行
		ResultSet resultSet = statement.executeQuery();


		try{
			//プリペアードステートメントにSQL分をセット
			statement = connection.prepareStatement
					("select * from test where student_no = ?, subject_cd = ?, school_cd = ?, no = ? ");
			//プリペアードステートメントに値をバインド
			statement.setStudent(1, student);
			statement.setSubject(2, subject);
			statement.setSchool(3, school);
			statement.setInt(4, no);

			//DAOを初期化
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()){
				//リザルトセットが存在する場合
				//テストインスタンスに検索結果をセット
				test.setStudent(studentDao.get(resultSet.getString("student_no")));
				test.setSubject(subjectDao.get(resultSet.getString("subject_cd")));
				test.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			}else{
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
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
				//学生インスタンスを初期化
				Test test = new Test();
				//学生インスタンスに検索結果をセット
				test.setSubject(rSet.getString("subject"));
				test.setEntYear(rSet.getInt("ent_year"));
				test.setString(rSet.getInt("class_num"));
				test.setStudent(rSet.getString("student_name"));
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


	}

}
