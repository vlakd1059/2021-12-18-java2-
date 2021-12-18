
public class MemberDTO {
	// �����ͺ��̽��� ǥ���ϱ����� ����� ������ VOŬ���� �̴�.
	// VO Ŭ����-> Value Object(����� ������ Ŭ����)

	private String id;
	private String pw;
	private String nick;

	public MemberDTO(String id, String pw, String nick) {
		this.id = id;
		this.pw = pw;
		this.nick = nick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public String toString() {
		return "[" + id + "-" + pw + "-" + nick + "]";
	}

}
