package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {


	//getメソッド - 科目コードと学校コードから一件の科目データを取得
	//引数1 cd … 科目コードを指定
	//引数2 school … 学校Beanを指定
	public Subject get(String cd, School school) throws Exception {

		// 科目Beanを宣言
		Subject subject = new Subject();

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?, CD = ?");
			// プリペアードステートメントに学校コードと科目コードをバインド
			statement.setString(1, school.getCd());
			statement.setString(2, cd);

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを宣言
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 科目Beanに検索結果をセット
				subject.setCd(resultSet.getString("CD"));
				subject.setName(resultSet.getString("NAME"));
				subject.setSchool(schoolDao.get(resultSet.getString("SCHOOL_CD")));
			} else {
				// リザルトセットが存在しない場合nullをセット
				subject = null;
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

		return subject;

	}

	//filterメソッド - 科目一覧を学校コードで絞り込む
	//引数1 school - 学校Beanを指定
	public List<Subject> filter(School school) throws Exception {
		// リストを宣言
		List<Subject> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを宣言
			SchoolDao schoolDao = new SchoolDao();

			// リザルトセットを全件走査
			while (resultSet.next()) {
				// 科目Beanを宣言
				Subject subject = new Subject();

				// 科目Beanに検索結果をセット
				subject.setCd(resultSet.getString("CD"));
				subject.setName(resultSet.getString("NAME"));
				subject.setSchool(schoolDao.get(resultSet.getString("SCHOOL_CD")));

				//listに追加
				list.add(subject);
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

	//saveメソッド - 科目情報を新規登録または更新
	//引数1 subject - 科目Beanを指定 渡すBeanには追加/変更したい科目のデータ(学校, 科目コード, 名前)が設定されている必要がある
	public boolean save(Subject subject) throws Exception {

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		// 学校Daoを宣言
		SchoolDao schoolDao = new SchoolDao();
		try {
			// データベースから科目を取得
			Subject old = get(subject.getCd(), schoolDao.get(subject.getCd()));

			if (old == null) {
				// 科目が存在しなかった場合、科目を新規作成
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("INSERT INTO SUBJECT(SCHOOL_CD, CD, NAME) VALUES(?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			} else {
				// 科目が存在した場合、科目名を変更
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("UPDATE SUBJECT NAME = ? WHERE SCHOOL_CD = ? AND CD = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getSchool().getCd());
				statement.setString(3, subject.getCd());
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

	//deleteメソッド - 科目情報を削除
	//引数1 subject - 科目Beanを指定 渡すBeanにはデータ(学校, 科目コード)が設定されている必要がある
	public boolean delete(Subject subject) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		// 学校Daoを宣言
		SchoolDao schoolDao = new SchoolDao();

		try {
			// データベースから科目を取得
			Subject old = get(subject.getCd(), schoolDao.get(subject.getCd()));

			if (old != null) {
				// 科目が存在した場合、科目を消す
				// プリペアードステートメントにDELETE文をセット
				statement = connection.prepareStatement("DELETE FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
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
}
