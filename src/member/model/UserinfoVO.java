package member.model;

public class UserinfoVO {
	
	private int idx;
	private String userid;
	private String password;
	private String name;
	private String email;
	private String hp1;
	private String hp2;
	private String hp3;
	private int post1;
	private int post2;
	private String addr1;
	private String addr2;
	private String registerday;
	private int point;
	private String quitYN;
	
	public UserinfoVO() {};
	
	public UserinfoVO(int idx, String userid, String password, String name, String email, String hp1, String hp2,
			String hp3, int post1, int post2, String addr1, String addr2, String registerday, int point, String quitYN) {
		this.idx = idx;
		this.userid = userid;
		this.password = password;
		this.name = name;
		this.email = email;
		this.hp1 = hp1;
		this.hp2 = hp2;
		this.hp3 = hp3;
		this.post1 = post1;
		this.post2 = post2;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.registerday = registerday;
		this.point = point;
		this.quitYN = quitYN;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHp1() {
		return hp1;
	}

	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}

	public String getHp2() {
		return hp2;
	}

	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}

	public String getHp3() {
		return hp3;
	}

	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}

	public int getPost1() {
		return post1;
	}

	public void setPost1(int post1) {
		this.post1 = post1;
	}

	public int getPost2() {
		return post2;
	}

	public void setPost2(int post2) {
		this.post2 = post2;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getQuitYN() {
		return quitYN;
	}

	public void setQuitYN(String quitYN) {
		this.quitYN = quitYN;
	}
	
	public String getAllphone() {
		return hp1 + "-" + hp2 + "-" + hp3;
	}
	
	public String getAlladdr() {
		return addr1 +" "+addr2 +"("+ post1 + post2 +")" ;
	}
	

}
