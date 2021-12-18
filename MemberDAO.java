import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDAO {

	// DAO ->DataBase Access Object
	// ������ ���̽��� �����ϱ����� ��ü�� ����� �ִ� Ŭ����
	private Connection conn; // ������ �Ǽ����� 0�� �⺻��
	private PreparedStatement psmt; // ���۷������� null�� �⺻��
	private ResultSet rs; // �Ҹ��� false�� �⺻��

	// ����̺� �ε��� Ŀ�ؼ� Ĵü�� �������� �޼ҵ�
	private void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";
			conn = DriverManager.getConnection(db_url, db_id, db_pw);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// DataBase�� ������ �����ִ� �޼ҵ�
	private void close() {
		// 4. Java�� DataBase���� ������ �����ش�.
		try {
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// �α��� ���
	public String login(String id, String pw) {
		getConnection();

		String nick = null;

		try {

			// 3. SQL�� �ۼ� �� ����
			String sql = "select * from bigmember where id=? and pw=?"; // 1:? 2:?

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				nick = rs.getString("nick");
				// or System.out.println(rs.getString("nick") + "�� ȯ���մϴ�");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();

		}
		return nick;
	}

	public int join(String id, String pw, String nick) {
		getConnection();
		int cnt = 0;

		try {

			// 3. SQL�� �ۼ� �� ����
			String sql = "insert into bigmember values(?,?,?)"; // 1:? 2:? 3:?
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id); // set:������ �Է� //get: ������ �ҷ�����
			psmt.setString(2, pw);
			psmt.setString(3, nick);

			// ���̺�ȿ� �����Ͱ� ��ȭ�� �Ͼ�°��
			cnt = psmt.executeUpdate();

			// �׳� ���� �����ͼ� ���°��

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;

	}

	public int update(String nick, String id) {
		getConnection();
		int cnt = 0;

		try {

			// 3. SQL�� �ۼ� �� ����
			String sql = "update bigmember set nick=? where id=?"; // 1:? 2:?

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, nick);
			psmt.setString(2, id);

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public ArrayList<MemberDTO> selectAll() {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();

		getConnection();

		String sql = "select * from bigMember";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {

				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String nick = rs.getString("nick");
				MemberDTO m = new MemberDTO(id, pw, nick);
				list.add(m);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;

	}

	public int delete(String id, String pw) {
		int cnt = 0;
		getConnection();
		String sql = "delete from bigMember where id=? and pw=?  ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			cnt = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;

	}

	public int adminUpdate(String change_id, String change_nick) {
		int cnt = 0;
		getConnection();
		String sql = "update bigMember set nick=? where id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, change_nick);
			psmt.setString(2, change_id);
			cnt = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}

	public int adminDelete(String delete_id, String delete_pw) {
		int cnt = 0;
		getConnection();
		String sql = "delete from bigMember where id=? and pw=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, delete_id);
			psmt.setString(2, delete_pw);
			cnt = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}

	public int deleteId(String removeId) {
		int result = 0;
		getConnection();
		String sql = "delete from bigMember where id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, removeId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

}
