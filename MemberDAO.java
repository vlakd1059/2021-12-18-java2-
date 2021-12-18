import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberDAO {

	// DAO ->DataBase Access Object
	// 데이터 베이스에 접근하기위한 객체를 만들수 있는 클래스
	private Connection conn; // 정수형 실수형은 0이 기본값
	private PreparedStatement psmt; // 래퍼런스형은 null이 기본값
	private ResultSet rs; // 불린은 false이 기본값

	// 드라이브 로딩과 커넥션 캑체를 가져오는 메소드
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

	// DataBase와 연결을 끊어주는 메소드
	private void close() {
		// 4. Java와 DataBase간의 연결을 끊어준다.
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

	// 로그인 기능
	public String login(String id, String pw) {
		getConnection();

		String nick = null;

		try {

			// 3. SQL문 작성 및 실행
			String sql = "select * from bigmember where id=? and pw=?"; // 1:? 2:?

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				nick = rs.getString("nick");
				// or System.out.println(rs.getString("nick") + "님 환영합니다");
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

			// 3. SQL문 작성 및 실행
			String sql = "insert into bigmember values(?,?,?)"; // 1:? 2:? 3:?
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id); // set:데이터 입력 //get: 데이터 불러오기
			psmt.setString(2, pw);
			psmt.setString(3, nick);

			// 테이블안에 데이터가 변화가 일어나는경우
			cnt = psmt.executeUpdate();

			// 그냥 값을 가져와서 보는경우

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

			// 3. SQL문 작성 및 실행
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
