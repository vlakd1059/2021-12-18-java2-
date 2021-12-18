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

		// 1.�α��� 2. ȸ������ 3.ȸ����Ϻ��� 4.ȸ���������� 5.ȸ��Ż�� 6.����

		// 0 ����̹�������Ͽ� ��ġ���� �־����.
		// 1.����̹� �ε� -> Class.forname("oracle.jdbc.driver.OracleDriver"); -> Ʈ���� ĳġ
		// 2. Connection ����
		// 3. sql �� �ۼ� �� ����
		// update ���̺�� set �÷��� = �ٲٰ������ where ����
		// executeQuery -> ���̺� �����Ͱ� ���� ������ (select��->executeQuery )
		// ��ȯ -> ResultSet
		// executeUpdate -> ���̺��� ������ ����ɶ�
		// ��ȯ -> intŸ������ ��ȯ ->����� sql���� ��
		// 4. finally
		// conn ��ü, psmt, rs -> �ݾ��ִ� ����: rs -> psmt -> conn (���������� ���� ����)

		Scanner sc = new Scanner(System.in);
		System.out.println("\t" + "\t" + "====ȸ������ �ý���====");
		MemberDAO dao = new MemberDAO();

		while (true) {
			System.out.print("|| 1.�α��� 2. ȸ������ 3.ȸ����Ϻ��� 4.ȸ���������� 5.ȸ��Ż�� 6.���� >>> ||");
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.println("\t" + "\t" + "====�α���====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				String nick = dao.login(id, pw);

				if (nick != null) {
					System.out.println(nick + "�� ȯ���մϴ�...");

					if (id.equals("admin")) {
						System.out.print("1. ȸ���������� 2.ȸ������");
						choice = sc.nextInt();
						if (choice == 1) {
							// ȸ������ ����
							System.out.println("==== ������ ȸ����������====");
							System.out.print("���̵� �Է� : ");
							String change_id = sc.next();
							System.out.print("������ �г��� �Է� : ");
							String change_nick = sc.next();

							int cnt = dao.adminUpdate(change_id, change_nick);
							if (cnt > 0) {
								System.out.println("ȸ������ ���� �Ϸ�...");
							} else {
								System.out.println("ȸ������ ���� ����...");
							}

						} else if (choice == 2) {
							// ȸ�� ����
							// ����1. ȸ���� ���̵� �ֿܼ� ���� ���
							// ex) 1. pgh
							// 2.csm
							// 3.lsh
							// 4.admin
							System.out.println("==== ������ ȸ������====");
							ArrayList<MemberDTO> list = dao.selectAll();
							int cnt = 1;
							for (int i = 0; i < list.size(); i++) {
								MemberDTO m = list.get(i);
								if (!m.getId().equals("admin")) {
									System.out.println(cnt + "." + list.get(i).getId());
									cnt++;
								}

							}
							System.out.print("������ ���̵� �Է� : ");
							String removeId = sc.next();
							int result = dao.deleteId(removeId);
							if (result > 0) {
								System.out.println("���̵� ���� �Ϸ�...");
							} else {
								System.out.println("���̵� ���� ����...");
							}

						}
					}
				} else {
					System.out.println("�α��� ����...");
				}

			} else if (choice == 2) {
				System.out.println("\t" + "\t" + "====ȸ������====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				System.out.print("�г��� �Է� : ");
				String nick = sc.next();

				int cnt = dao.join(id, pw, nick);
				if (cnt > 0) {
					System.out.println("ȸ������ ����...");
				} else {
					System.out.println("ȸ������ ����...");
				}

			} else if (choice == 3) {
				// ȸ�� ��� ����
				System.out.println("\t" + "\t" + "====ȸ����� ����====");
				ArrayList<MemberDTO> list = dao.selectAll();
				for (int i = 0; i < list.size(); i++) {
					MemberDTO m = list.get(i);
					System.out.println(list.get(i));

				}
				// ex) csm - 1234 -�˼���

				System.out.println();
			} else if (choice == 4) {

				// ȸ�� ���� ����

				// id-> pgh�� ȸ���� �г�����
				// 'ŷ����' ���� �ٲٱ�

				System.out.println("\t" + "\t" + "====ȸ������ ����====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();
				System.out.print("���� �� �г��� �Է� : ");
				String nick = sc.next();

				int cnt = dao.update(nick, id);

				if (cnt > 0) {
					System.out.println("ȸ������ ���� �Ϸ�...");
				} else {
					System.out.println("ȸ������ ���� ����...");
				}

			} else if (choice == 5) {
				// ȸ�� Ż��
				System.out.println("\t" + "\t" + "====ȸ��Ż��====");
				System.out.print("���̵� �Է� : ");
				String id = sc.next();
				System.out.print("��й�ȣ �Է� : ");
				String pw = sc.next();

				int cnt = dao.delete(id, pw);
				if (cnt > 0) {
					System.out.println("ȸ������ �Ϸ�...");
				} else {
					System.out.println("ȸ������ ����...");
				}

			} else if (choice == 6) {
				System.out.println("���α׷� ����...");
				break;
			} else {
				System.out.println("�޴� �ٽ��Է�...");
			}
		}
	}

}
