package member.model;

import java.sql.SQLException;
import java.util.List;

public interface InteruserinfoDAO {
	
	// 유저 아이디 중복체크 하는 추상 메소드
	int useridCheck(String userid) throws SQLException;

	// 회원가입 추상 메소드
	int userAdd(String userid, String password, String name, String email, String post1, String post2, String addr1, String addr2, String hp1, String hp2, String hp3) throws SQLException;
	
	// 회원테이블의 정보를 가져오는 추상 메소드
	List<UserinfoVO> getMemberList() throws SQLException; 
	
	// *** 로그인 처리 (로그인 성공 : 회원정보를 리턴, 로그인 실패 : null 리턴) 추상 메소드
	UserinfoVO loginOKmemberInfo(String userid,String password)throws SQLException;
	
	// *** 회원 1명에 대한 정보를 보여주는 추상 메소드 
	UserinfoVO  getMemberOneByIdx(String  str_idx)throws SQLException;
	
	// *** ID 찾기를 해주는 추상 메소드 *** //
	String getUserid(String name, String mobile) throws SQLException;
	
	// ***비밀번호 찾기를 위해 먼저 userid 와 email 을 가지는 사용자가 존재하는지 검증해주는 추상 메소드 *** //
	int isUserExists( String userid, String email)throws SQLException; 
	
	// *** 암호를 새암호로 변경하는 추상메소드 *** // 
	int updatePwdUser(String userid,String  password)throws SQLException;
	
	// *** 회원 정보를 변경하는 메소드 생성하기 ***  //
	int updateMember(UserinfoVO userinfovo) throws SQLException; 
		
	// *** 회원을 삭제하는 추상 메소드 생성하기 *** // 
	int deleteMember(String idx) throws SQLException; 
	
}
