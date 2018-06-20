package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserinfoDAO implements InteruserinfoDAO {
	
	private DataSource ds;
	// 객체변수 ds는 아파치 톰캣이 제공하는 DBCP(DB Connection Pool) 이다. (import javax.sql.DataSource)
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/* 
	   === AjaxDAO 생성자에서 해야할 일은 === 
	   아파치 톰캣이 제공하는 DBCP(DB Connection Pool) 객체인 ds 를 빌려오는 것이다.  
	*/
	public UserinfoDAO() {
		try {
			Context initContext = new InitialContext(); // javax.naming 을 import한다.
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");
			
		} catch (NamingException e) {
			e.printStackTrace(); 
		}
		
	}// end of MemoDAO()---------------------------------
	

	// *** 사용한 자원을 반납하는 close() 메소드 생성하기 *** //
	public void close() {
		try {
			
			if(rs != null) {
				rs.close();
				rs = null;
			}
			
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			
			if(conn != null) {
				conn.close();
				conn = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}// end of close()------------------------------------


	// 유저 아이디 중복체크 하는 추상 메소드
	@Override
	public int useridCheck(String userid) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) AS cnt "
						+" from tbl_userinfo "
						+" where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			boolean bool = rs.next();
			
			if(bool) {
				result = rs.getInt("cnt");		
			}
					
					
		} finally {
			close();
		}
		
		return result;
	}// end of useridCheck(String userid) ------------------------------------------


	// 회원가입  메소드
	@Override
	public int userAdd(String userid, String password, String name, String email, String post1, String post2, String addr1,
			String addr2, String hp1, String hp2, String hp3) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_userinfo(idx, userid, password, name, email, hp1, hp2, hp3, post1, post2, addr1, addr2) "
						+" values(seq_tbl_userinfo.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.setString(5, hp1);
			pstmt.setString(6, hp2);
			pstmt.setString(7, hp3);
			pstmt.setString(8, post1);
			pstmt.setString(9, post2);
			pstmt.setString(10, addr1);
			pstmt.setString(11, addr2);
			
			
			result = pstmt.executeUpdate();
					
					
		} finally {
			close();
		}
		
		return result;
		
	}// end of userAdd(String userid, String password, String name, ..., String hp3) ----------------------------


	// 회원테이블의 정보를 가져오는 메소드
	@Override
	public List<UserinfoVO> getMemberList() throws SQLException {
		List<UserinfoVO> userList = null;
		try {
			conn = ds.getConnection();
			String sql = " select idx, userid, password, name, email, hp1, hp2, hp3, post1, post2, addr1, addr2, registerday, point, quitYN "
						+" from tbl_userinfo "
						+" where quitYN = 1 and userid != 'admin' ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt ++;
				
				if(cnt == 1) {
					userList = new ArrayList<UserinfoVO>();
				}
				
				int idx = rs.getInt("idx");
				String userid = rs.getString("userid");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String hp1 = rs.getString("hp1");
				String hp2 = rs.getString("hp2");
				String hp3 = rs.getString("hp3");
				int post1 = rs.getInt("post1");
				int post2 = rs.getInt("post2");
				String addr1 = rs.getString("addr1");
				String addr2 = rs.getString("addr2");
				String registerday = rs.getString("registerday");
				int point = rs.getInt("point");
				String quitYN = rs.getString("quitYN");
				
				
				UserinfoVO vo = new UserinfoVO(idx, userid, password, name, email, hp1, hp2, hp3, post1, post2, addr1, addr2, registerday, point, quitYN);

				
				
				userList.add(vo);
			}
			
		} finally {
			close();
		}
		
		return userList;
	}// end of getMemberList()------------------------------------------------

	// *** 로그인 처리(로그인 성공 : 회원정보를 리턴, 로그인 실패 : null 리턴) 메소드 *** //
	@Override
	public UserinfoVO loginOKmemberInfo(String userid, String password) throws SQLException {
		UserinfoVO userinfovo = null;
		try {
			conn = ds.getConnection();
		 // 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.	
			
			String sql = " select idx, userid, name, password, email, hp1, hp2, hp3 "
					   + "      , post1, post2, addr1, addr2 "
					   + "      , to_char(registerday, 'yyyy-mm-dd') AS registerday  "
					   + "       , point, quitYN "
					   + " from tbl_userinfo "
					   + " where  userid = ? and password = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			boolean bool = rs.next();
			
			if(bool) {
				// 회원이 존재하는 경우
				int idx = rs.getInt("idx");
				userid = rs.getString("userid");
				String name = rs.getString("name");
				password = rs.getString("password");
				String email = rs.getString("email");
				String hp1 = rs.getString("hp1");
				String hp2 = rs.getString("hp2");
				String hp3 = rs.getString("hp3");
				int post1 = rs.getInt("post1");
				int post2 = rs.getInt("post2");
				String addr1 = rs.getString("addr1");
				String addr2 = rs.getString("addr2");
				String registerday = rs.getString("registerday");
				int point = rs.getInt("point");
				String quitYN = rs.getString("quitYN");
				
				userinfovo = new UserinfoVO(idx, userid, password, name, email, hp1, hp2, hp3, post1, post2, addr1, addr2, registerday, point, quitYN);
			}
			else {
				// 회원이 존재하지 않거나 status 컬럼이 값이 0 인 경우.
			}
			
		} finally {
			close();
		}
		return userinfovo;
		
	}// end of loginOKmemberInfo(String userid, String password)-----------------------------

	
	// *** 회원 1명에 대한 정보를 보여주는  메소드 *** //
	@Override
	public UserinfoVO getMemberOneByIdx(String str_idx) throws SQLException {
UserinfoVO userinfovo = null;
		
		try {
			conn = ds.getConnection();
		    // 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.
			
			String sql = "select idx, userid, name, password, email, hp1, hp2, hp3, post1, post2, addr1, addr2 "
		              +	"      , to_char(registerday, 'yyyy-mm-dd') as registerday "
					  +	"      , point, quitYN "
		              +	" from tbl_userinfo "
					  + " where idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, str_idx);
			
			rs = pstmt.executeQuery();
			
			boolean isExists = rs.next();
			
			if(isExists) {
				int idx = rs.getInt("idx");
				String userid = rs.getString("userid");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String hp1 = rs.getString("hp1");
				String hp2 = rs.getString("hp2");
				String hp3 = rs.getString("hp3");
				int post1 = rs.getInt("post1");
				int post2 = rs.getInt("post2");
				String addr1 = rs.getString("addr1");
				String addr2 = rs.getString("addr2");
				String registerday = rs.getString("registerday");
				int point = rs.getInt("point");
				String quitYN = rs.getString("quitYN");
				
				userinfovo = new UserinfoVO(); // 기본생성자
				
				userinfovo.setIdx(idx);
				userinfovo.setUserid(userid);
				userinfovo.setName(name);
				userinfovo.setPassword(password);
				userinfovo.setEmail(email);
				userinfovo.setHp1(hp1);
				userinfovo.setHp2(hp2);
				userinfovo.setHp3(hp3);
				userinfovo.setPost1(post1);
				userinfovo.setPost2(post2);
				userinfovo.setAddr1(addr1);
				userinfovo.setAddr2(addr2);
				userinfovo.setRegisterday(registerday);
				userinfovo.setPoint(point);
				userinfovo.setQuitYN(quitYN);
			}
			
		} finally {
			close();
		}
		return userinfovo;
		
	}//end of getMemberOneByIdx(String str_idx)---------------------------

	// *** ID 찾기를 해주는 메소드  생성하기 *** //
	@Override
	public String getUserid(String name, String mobile) throws SQLException {
		String result = null;
		
		try {
			conn = ds.getConnection();
		    // 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.
			
			String sql = "select userid "
		              +	" from tbl_userinfo "
					  + " where quitYN = 1 and"
					  + "       name = ? and "
					  + "       trim(hp1) || trim(hp2) || trim(hp3) = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, mobile);
			
			rs = pstmt.executeQuery();
			
			boolean isExists = rs.next();
			
			if(isExists) {
				result = rs.getString("userid");
			}
			
		} finally {
			close();
		}		
		
		return result;
		
	}//end of getUserid(String name, String mobile)------------------------------


	// *** 비밀번호 찾기를 위해 먼저 userid 와 email 을 가지는 사용자가 존재하는지 검증해주는 메소드 생성하기 *** //
	@Override
	public int isUserExists(String userid, String email) throws SQLException {
int result = 0;
		
		try {
			conn = ds.getConnection();
		    // 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.
			
			String sql = "select count(*) AS CNT "
		              +	" from tbl_userInfo "
					  + " where quitYN = 1 and"
					  + "       userid = ? and "
					  + "       email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, email);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			result = rs.getInt("CNT");
			
		} finally {
			close();
		}
		
		return result;
		
	}//end of isUserExists(String userid, String email)--------------------------


	@Override
	public int updatePwdUser(String userid, String password) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
		    // 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.
			
			String sql = "update tbl_userinfo set password = ? "
		              + " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
		
	}// end of updatePwdUser(String userid, String password)---------------------------


	// *** 회원 정보를 변경하는 메소드 생성하기 ***  //
	@Override
	public int updateMember(UserinfoVO userinvo) throws SQLException {
	int result = 0;
		
		try {
			conn = ds.getConnection();
			// 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.
			
			String sql = " update tbl_userinfo set name=?, password=?, email=?, hp1=?, hp2=?, hp3=?, post1=?, post2=?, addr1=?, addr2=?  "   		
				       + " where idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			
			pstmt.setString(1, userinvo.getName());
			pstmt.setString(2, userinvo.getPassword());
			pstmt.setString(3, userinvo.getEmail());
			pstmt.setString(4, userinvo.getHp1());
			pstmt.setString(5, userinvo.getHp2());
			pstmt.setString(6, userinvo.getHp3());
			pstmt.setInt(7, userinvo.getPost1());
			pstmt.setInt(8, userinvo.getPost2());
			pstmt.setString(9, userinvo.getAddr1());
			pstmt.setString(10, userinvo.getAddr2());
			pstmt.setInt(11, userinvo.getIdx());
			
			result = pstmt.executeUpdate();
			
		} 
		finally {
			close();
		}	
		
		return result;
	}

	// *** 회원을 삭제하는  메소드 생성하기 *** // 
	@Override
	public int deleteMember(String idx) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			// 객체 ds 를 통해 아파치톰캣이 제공하는 DBCP(DB Connection pool)에서 생성된 커넥션을 빌려온다.
				
			String sql = " update tbl_userinfo set quityn = 0 "   
					   + " where idx = ? ";
				
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, idx);
			
			result = pstmt.executeUpdate();
				
			} 
		
		finally {
			close();
				}	
		
		return result;
	}
	
	
	

}
