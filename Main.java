import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

public class Main {

	public static void main(String[] args) {

		// 1.로그인 2. 회원가입 3.회원목록보기 4.회원정보수정 5.회원탈퇴 6.종료

		// 0 드라이버경로파일에 설치파일 넣어놓기.
		// 1.드라이버 로딩 -> Class.forname("oracle.jdbc.driver.OracleDriver"); -> 트라이 캐치
		// 2. Connection 연결
		// 3. sql 문 작성 및 실행
		// update 테이블명 set 컬럼명 = 바꾸고싶은값 where 조건
		// executeQuery -> 테이블에 데이터가 변함 없을때 (select만->executeQuery )
		// 반환 -> ResultSet
		// executeUpdate -> 테이블의 내용이 변경될때
		// 반환 -> int타입으로 반환 ->실행된 sql문의 수
		// 4. finally
		// conn 객체, psmt, rs -> 닫아주는 순서: rs -> psmt -> conn (열린순으로 먼저 닫음)

		Scanner sc = new Scanner(System.in);
		System.out.println("\t" + "\t" + "====회원관리 시스템====");
		MemberDAO dao = new MemberDAO();

		while (true) {
			System.out.print("|| 1.로그인 2. 회원가입 3.회원목록보기 4.회원정보수정 5.회원탈퇴 6.종료 >>> ||");
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.println("\t" + "\t" + "====로그인====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				String nick = dao.login(id, pw);

				if (nick != null) {
					System.out.println(nick + "님 환영합니다...");

					if (id.equals("admin")) {
						System.out.print("1. 회원정보수정 2.회원삭제");
						choice = sc.nextInt();
						if (choice == 1) {
							// 회원정보 수정
							System.out.println("==== 관리자 회원정보수정====");
							System.out.print("아이디 입력 : ");
							String change_id = sc.next();
							System.out.print("변경할 닉네임 입력 : ");
							String change_nick = sc.next();

							int cnt = dao.adminUpdate(change_id, change_nick);
							if (cnt > 0) {
								System.out.println("회원정보 수정 완료...");
							} else {
								System.out.println("회원정보 수정 실패...");
							}

						} else if (choice == 2) {
							// 회원 삭제
							// 문제1. 회원의 아이디만 콘솔에 전부 출력
							// ex) 1. pgh
							// 2.csm
							// 3.lsh
							// 4.admin
							System.out.println("==== 관리자 회원삭제====");
							ArrayList<MemberDTO> list = dao.selectAll();
							int cnt = 1;
							for (int i = 0; i < list.size(); i++) {
								MemberDTO m = list.get(i);
								if (!m.getId().equals("admin")) {
									System.out.println(cnt + "." + list.get(i).getId());
									cnt++;
								}

							}
							System.out.print("삭제할 아이디 입력 : ");
							String removeId = sc.next();
							int result = dao.deleteId(removeId);
							if (result > 0) {
								System.out.println("아이디 삭제 완료...");
							} else {
								System.out.println("아이디 삭제 실패...");
							}

						}
					}
				} else {
					System.out.println("로그인 실패...");
				}

			} else if (choice == 2) {
				System.out.println("\t" + "\t" + "====회원가입====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				System.out.print("닉네임 입력 : ");
				String nick = sc.next();

				int cnt = dao.join(id, pw, nick);
				if (cnt > 0) {
					System.out.println("회원가입 성공...");
				} else {
					System.out.println("회원가입 실패...");
				}

			} else if (choice == 3) {
				// 회원 목록 보기
				System.out.println("\t" + "\t" + "====회원목록 보기====");
				ArrayList<MemberDTO> list = dao.selectAll();
				for (int i = 0; i < list.size(); i++) {
					MemberDTO m = list.get(i);
					System.out.println(list.get(i));

				}
				// ex) csm - 1234 -죄수민

				System.out.println();
			} else if (choice == 4) {

				// 회원 정보 수정

				// id-> pgh인 회원의 닉네임을
				// '킹건하' 으로 바꾸기

				System.out.println("\t" + "\t" + "====회원정보 수정====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();
				System.out.print("변경 할 닉네임 입력 : ");
				String nick = sc.next();

				int cnt = dao.update(nick, id);

				if (cnt > 0) {
					System.out.println("회원정보 수정 완료...");
				} else {
					System.out.println("회원정보 수정 실패...");
				}

			} else if (choice == 5) {
				// 회원 탈퇴
				System.out.println("\t" + "\t" + "====회원탈퇴====");
				System.out.print("아이디 입력 : ");
				String id = sc.next();
				System.out.print("비밀번호 입력 : ");
				String pw = sc.next();

				int cnt = dao.delete(id, pw);
				if (cnt > 0) {
					System.out.println("회원삭제 완료...");
				} else {
					System.out.println("회원삭제 실패...");
				}

			} else if (choice == 6) {
				System.out.println("프로그램 종료...");
				break;
			} else {
				System.out.println("메뉴 다시입력...");
			}
		}
	}

}
