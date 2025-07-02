package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Charge;
import bean.School;
import bean.Subject;
import bean.Teacher;

public class ChargeDao extends Dao {

	// get - 科目から担当教師情報を取得(1つの科目に対応する教師は1人のみ) 科目名と教師名も取得する
	// 引数1 subject - 情報を取得したい科目を指定
	// 引数2 teacher - 検索したい学校に所属する教師 直接呼び出す際はログイン中のユーザーを指定
	public Charge get(Subject subject, Teacher teacher) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//Chargeを宣言
		Charge charge = new Charge();

		// DAOを宣言
		TeacherDao teacherDao = new TeacherDao();
		SubjectDao subjectDao = new SubjectDao();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM CHARGE WHERE SUBJECT_CD = ?");
			// 科目コードをバインド
			statement.setString(1, subject.getCd());

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 担当Beanに検索結果をセット
				charge.setTeacher(teacherDao.get(resultSet.getString("TEACHER_ID")));
				charge.setSubject(subjectDao.get(resultSet.getString("SUBJECT_CD"), teacher.getSchool()));
			} else {
				// リザルトセットが存在しない場合nullをセット
				charge = null;
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

		return charge;
	}

	// filter - 学校から、その学校の担当科目データのリストを取得
	// 引数1 school - 情報を取得したい学校を指定
	public List<Charge> filter(School school) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//リストを宣言
		List<Charge> list = new ArrayList<>();

		// DAOを宣言
		TeacherDao teacherDao = new TeacherDao();
		SubjectDao subjectDao = new SubjectDao();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT CD, TEACHER_ID, SUBJECT_CD FROM SCHOOL LEFT JOIN CHARGE WHERE CD = 'oom';");
			// 科目コードをバインド
			statement.setString(1, school.getCd());

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Charge charge = new Charge();

				charge.setTeacher(teacherDao.get(resultSet.getString("TEACHER_ID")));
				charge.setSubject(subjectDao.get(resultSet.getString("SUBJECT_CD"), school));

				list.add(charge);
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

		return list;
	}

	// filter - 教師から、その教師の担当科目のリストを取得
	// 引数1 teacher - 情報を取得したい教師を指定
	public List<Charge> filter(Teacher teacher) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//リストを宣言
		List<Charge> list = new ArrayList<>();

		// DAOを宣言
		TeacherDao teacherDao = new TeacherDao();
		SubjectDao subjectDao = new SubjectDao();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM CHARGE WHERE TEACHER_ID = ?");
			// 科目コードをバインド
			statement.setString(1, teacher.getId());

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Charge charge = new Charge();

				charge.setTeacher(teacherDao.get(resultSet.getString("TEACHER_ID")));
				charge.setSubject(subjectDao.get(resultSet.getString("SUBJECT_CD"), teacher.getSchool()));

				list.add(charge);
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

		return list;
	}

	// save - 担当教師情報を登録
	// 引数1 subject - 情報を登録したい科目
	// 引数2 teacher - 情報を登録したい教師
	public boolean save(Subject subject, Teacher teacher) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {
			// データベースから科目を取得
			Charge old = get(subject, teacher);

			if (old == null) {
				// データが存在しなかった場合、データを新規作成
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("INSERT INTO CHARGE(TEACHER_ID, SUBJECT_CD) VALUES(?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, teacher.getId());
				statement.setString(2, subject.getCd());
			} else {
				// データが存在した場合、担当教師を更新
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("UPDATE INTO SET TEACHER_ID = ? WHERE SUBJECT_ID = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, teacher.getId());
				statement.setString(2, subject.getCd());
			}

			// プリペアードステートメントを実行
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

	// delete - 担当教師情報を削除
	// 引数1 subject - 情報を削除したい科目
	// 引数2 teacher - 情報を削除したい教師
	public boolean delete(Subject subject, Teacher teacher) throws Exception {

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {
			// データベースから担当教師情報を取得
			Charge old = get(subject, teacher);

			if (old != null) {
				// データが存在した場合、データを削除
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("DELETE FROM CHARGE WHERE TEACHER_ID = ? AND SUBJECT_CD = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, teacher.getId());
				statement.setString(2, subject.getCd());
			}

			// プリペアードステートメントを実行
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
