package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Charge;
import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {
	/**
	 * getメソッド 教員IDを指定して教員インスタンスを1件取得する
	 *
	 * @param id:String
	 *            教員ID
	 * @return 教員クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Teacher get(String id) throws Exception {
		// 教員インスタンスを初期化
		Teacher teacher = new Teacher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT TEACHER.ID, TEACHER.PASSWORD, TEACHER.NAME, TEACHER.SCHOOL_CD, TEACHER.ISADMIN, CLASS_NUM FROM TEACHER "
					+ "JOIN CLASS_NUM ON TEACHER.ID = CLASS_NUM.TEACHER_ID WHERE TEACHER.ID = ?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();
			ClassNumDao classNumDao = new ClassNumDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				teacher.setId(resultSet.getString("id"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setName(resultSet.getString("name"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));
				//ログインしているユーザーが権限を持っているか
				teacher.setAdmin(resultSet.getBoolean("isAdmin"));
				// 担任クラス番号
				teacher.setClassNum(resultSet.getString("class_num"));
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				teacher = null;
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

		return teacher;
	}

	/**
	 * loginメソッド 教員IDとパスワードで認証する
	 *
	 * @param id:String
	 *            教員ID
	 * @param password:String
	 *            パスワード
	 * @return 認証成功:教員クラスのインスタンス, 認証失敗:null
	 * @throws Exception
	 */
	public Teacher login(String id, String password) throws Exception {
		// 教員クラスのインスタンスを取得
		Teacher teacher = get(id);
		// 教員がnullまたはパスワードが一致しない場合
		if (teacher == null || !teacher.getPassword().equals(password)) {
			return null;
		}
		return teacher;
	}


	// filterメソッド 学校から在職教師一覧を取得
	// 引数1 school - 教師を検索したい学校を指定 想定はログイン中のユーザーの学校
	public List<Teacher> filter(School school) throws Exception {
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// リストを宣言
		List<Teacher> list = new ArrayList<>();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT TEACHER.ID, TEACHER.PASSWORD, TEACHER.NAME, TEACHER.SCHOOL_CD, TEACHER.ISADMIN, CLASS_NUM FROM TEACHER "
					+ "JOIN CLASS_NUM ON TEACHER.ID = CLASS_NUM.TEACHER_ID WHERE TEACHER.SCHOOL_CD = ?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを宣言
			SchoolDao schoolDao = new SchoolDao();

			// リザルトセットを全件走査
			while (resultSet.next()) {
				// 科目Beanを宣言
				Teacher teacher = new Teacher();

				// 科目Beanに検索結果をセット
				teacher.setId(resultSet.getString("ID"));
				teacher.setName(resultSet.getString("NAME"));
				teacher.setSchool(schoolDao.get(resultSet.getString("SCHOOL_CD")));
				teacher.setAdmin(resultSet.getBoolean("isAdmin"));
				// 担任クラス番号
				teacher.setClassNum(resultSet.getString("class_num"));

				//listに追加
				list.add(teacher);
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

	// save - 教師情報を登録または更新
	// 引数1 teacher - 登録したいデータの入った教師beanを指定 全てのデータ（ID, password, 名前, 学校, 管理者権限の有無）が揃っている必要がある
	public boolean save(Teacher teacher) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int count = 0;

		try {
			// データベースから教師を取得
			Teacher old = get(teacher.getId());

			if (old == null) {
				// 教師が存在しなかった場合、教師を新規作成
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("INSERT INTO TEACHER(ID, PASSWORD, NAME, SCHOOL_CD, ISADMIN) VALUES(?, ?, ?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, teacher.getId());
				statement.setString(2, teacher.getPassword());
				statement.setString(3, teacher.getName());
				statement.setString(4, teacher.getSchool().getCd());
				statement.setBoolean(5, teacher.isAdmin());

			} else {
				// 教師が存在した場合、情報を更新
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("UPDATE TEACHER SET PASSWORD = ?, NAME = ?, SCHOOL_CD = ?, ISADMIN = ? WHERE ID = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, teacher.getPassword());
				statement.setString(2, teacher.getName());
				statement.setString(3, teacher.getSchool().getCd());
				statement.setBoolean(4, teacher.isAdmin());
				statement.setString(5, teacher.getId());
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

		if (count == 1) {
			// 実行件数1件の場合
			return true;
		} else {
			// 実行件数がそれ以外の場合
			return false;
		}
	}


	// delete - 教師情報を削除する 紐づいた担当科目情報が存在する場合、同時に削除
	// 引数1 teacher - 成績を削除したい教師beanを指定
	public boolean delete(Teacher teacher) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		// 実行件数
		int countTeacher = 0;
		int countCharge = 0;

		// 担当教師の削除が成功したかどうか
		boolean isSuccess = false;

		// DAOを宣言
		ChargeDao chargeDao = new ChargeDao();

		// 削除教師が担当している科目を取得
		List<Charge> chargeList = chargeDao.filter(teacher);

		try {
			// 自動コミット無効化
			connection.setAutoCommit(false);

			// データベースから教師を取得
			Teacher old = get(teacher.getId());

			if (old != null) {
				// 教師が存在した場合、科目を消す

				// 自動コミット無効化
				connection.setAutoCommit(false);

				// 担当教師データが存在した場合、事前に削除
				for(Charge charge : chargeList) {
					if (chargeDao.delete(charge.getSubject(), charge.getTeacher())) {
						countCharge++;
					}
				}

				// プリペアードステートメントにDELETE文をセット
				statement = connection.prepareStatement("DELETE FROM TEACHER WHERE ID = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, teacher.getId());

				countTeacher = statement.executeUpdate();
			}


		} catch (Exception e) {
			throw e;
		} finally {
			// どちらも成功・または教師データの削除が成功し担当教師データが存在しなかった場合はDB更新を確定する
			if (countTeacher == 1 && countCharge == chargeList.size()) {
				connection.commit();
				isSuccess = true;
			} else {
				connection.rollback();
			}

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

		return isSuccess;
	}
}
