package board.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


public class BoardDAO implements InterBoardDAO {

	private DataSource ds = null;
	// 객체변수 ds 는 아파치 톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/* === ProductDAO 생성자에서 해야할 일은 ===
	     아파치 톰캣이 제공하는 DBCP(DB Connection Pool) 객체인 ds 를 얻어오는 것이다.
	*/
	public BoardDAO() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");
		} catch(NamingException e) {
			e.printStackTrace();
		}
		
	}// end of MemoDAO() 생성자------------------
	
	
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
				
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of close()-------------------

	
	// *** tbl_storemap 테이블의 모든 정보를 조회해주는 메소드 생성하기 *** //
	@Override
	public List<StoremapVO> getStoreList() throws SQLException {
		
		List<StoremapVO> storemapList = null;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select * "
					   + " from "
					   + " ( "
					   + "   select row_number() over(order by storeno ASC) AS rno, "
					   + " 			storeno, storectg, storeName, latitude, longitude, zindex, tel, addr, sopen, sclose "
					   + "   from tbl_storemap "
					   + " ) V ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				if(cnt==1)
					storemapList = new ArrayList<StoremapVO>();
				
				int storeno = rs.getInt("storeno");
				String storectg = rs.getString("storectg");
				String storeName = rs.getString("storeName");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int zindex = rs.getInt("zindex");
				String tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String sopen = rs.getString("sopen");
				String sclose = rs.getString("sclose");
								
				StoremapVO mapvo = new StoremapVO(storeno, storectg, storeName, latitude, longitude, zindex, tel, addr, sopen, sclose);
				
				storemapList.add(mapvo);
				
			}// end of while---------------------
			
		 } finally{
			close();
		 }
		
		return storemapList;
		
	}// end of List<StoremapVO> getStoreMap()---------------------
	
	
	// *** tbl_storemap 상점 개수를 조회해주는 추상 메소드 *** //
	@Override
	public int getTotalStoreCount() throws SQLException {
		
		int cnt = 0;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select count(*) AS CNT "
					   + " from tbl_storemap ";
			
			pstmt = conn.prepareStatement(sql);
			 
			rs = pstmt.executeQuery();
			 
			rs.next();
			 
			cnt = rs.getInt("CNT");
			
		 } finally{
			close();
		 }
		
		return cnt;
	
	}// end of getTotalStoreCount()---------------------------------------------------------

		
	// *** tbl_storemap 테이블의 구분별 상점 리스트를 조회해주는 추상 메소드 *** //
	@Override
	public List<StoremapVO> getSelectedStoreList(String shopTypeCd, String storeAreaCd, int startRno, int endRno) throws SQLException {
		
		List<StoremapVO> selectedStoremapList = null;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select * "
					   + " from "
					   + " ( "
					   + "   select row_number() over(order by storeno DESC) AS rno, "
					   + " 			storeno, storectg, storeName, latitude, longitude, zindex, tel, addr, sopen, sclose "
					   + "   from tbl_storemap "
					   + "   where storectg like '%'|| ? || '%' and addr like '%'|| ? || '%' "
					   + " ) V "
					   + " where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, shopTypeCd);
			pstmt.setString(2, storeAreaCd);
			pstmt.setInt(3, startRno);
			pstmt.setInt(4	, endRno);
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				if(cnt==1)
					selectedStoremapList = new ArrayList<StoremapVO>();
				
				int storeno = rs.getInt("storeno");
				String storectg = rs.getString("storectg");
				String storeName = rs.getString("storeName");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int zindex = rs.getInt("zindex");
				String tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String sopen = rs.getString("sopen");
				String sclose = rs.getString("sclose");
								
				StoremapVO mapvo = new StoremapVO(storeno, storectg, storeName, latitude, longitude, zindex, tel, addr, sopen, sclose);
				
				selectedStoremapList.add(mapvo);
				
			}// end of while---------------------
			
		 } finally{
			close();
		 }
		
		return selectedStoremapList;		
		
	}// end of getSelectedStoreList(String shopTypeCd, String storeAreaCd)
	
	
	// *** tbl_storemap 상점 개수를 더보기 처리하기 위해 row을 이용해 조회해주는 추상메소드 *** //
	@Override
	public List<StoremapVO> getStoremapVOList(int startRno, int endRno) throws SQLException {
		
		List<StoremapVO> storemapList = null;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select * "
					   + " from "
					   + " ( "
					   + "   select row_number() over(order by storeno DESC) AS rno, "
					   + " 			storeno, storectg, storeName, latitude, longitude, zindex, tel, addr, sopen, sclose "
					   + "   from tbl_storemap "
					   + " ) V "
					   + " where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRno);
			pstmt.setInt(2, endRno);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				if(cnt==1)
					storemapList = new ArrayList<StoremapVO>();
				
				int storeno = rs.getInt("storeno");
				String storectg = rs.getString("storectg");
				String storeName = rs.getString("storeName");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int zindex = rs.getInt("zindex");
				String tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String sopen = rs.getString("sopen");
				String sclose = rs.getString("sclose");
								
				StoremapVO mapvo = new StoremapVO(storeno, storectg, storeName, latitude, longitude, zindex, tel, addr, sopen, sclose);
				
				storemapList.add(mapvo);
				
			}// end of while---------------------
			
		 } finally{
			close();
		 }
		
		return storemapList;
		
	} // end of getStoremapVOList(int startRno, int endRno)--------------------------------------------

	
	// *** tbl_storemap 테이블의 위도 ,경도를 조회해주는 추상 메소드 *** //
	@Override
	public double[] getLatitudeLongitude(int storeno) throws SQLException {
		
		double[] LatitudeLongitude = null;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select latitude, longitude "
					   + " from tbl_storemap "
					   + " where storeno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, storeno);
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				if(cnt==1)
					LatitudeLongitude = new double[2];
				
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
								
				LatitudeLongitude[0] = latitude;
				LatitudeLongitude[1] = longitude;
				
			}// end of while---------------------
			
		 } finally{
			close();
		 }
		
		return LatitudeLongitude;
		
	}// end of getLatitudeLongitude(int storeno)------------------------------------

	
	// *** tbl_notice 공지 개수를 조회해주는 추상 메소드 *** //
	@Override
	public int getTotalNoticeCount() throws SQLException {

		int cnt = 0;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select count(*) AS CNT "
					   + " from tbl_notice ";
			

			pstmt = conn.prepareStatement(sql);
			 
			rs = pstmt.executeQuery();
			 
			rs.next();
			 
			cnt = rs.getInt("CNT");
			
		 } finally{
			close();
		 }
		
		return cnt;
		
	}// end of getTotalNoticeCount()-----------------------------------------------


	
	// *** tbl_notice 공지 개수를 더보기 처리하기 위해 row을 이용해 조회해주는 추상메소드 *** //
	@Override
	public List<NoticeVO> getNoticeVOList(int startRno, int endRno) throws SQLException {
		
		List<NoticeVO> noticeList = null;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select * "
					   + " from "
					   + " ( "
					   + "   select row_number() over(order by noticeno DESC) AS rno, "
					   + " 			noticeno, userid, noticeName, noticeContent, to_char(noticeDate, 'yyyy-mm-dd') AS noticeDate, views "
					   + "   from tbl_notice "
					   + " ) V "
					   + " where rno between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRno);
			pstmt.setInt(2, endRno);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				if(cnt==1)
					noticeList = new ArrayList<NoticeVO>();
				
				int noticeno = rs.getInt("noticeno");
				String userid = rs.getString("userid");
				String noticeName = rs.getString("noticeName");
				String noticeContent = rs.getString("noticeContent");
				String noticeDate = rs.getString("noticeDate");
				int views = rs.getInt("views");
								
				NoticeVO noticevo = new NoticeVO(noticeno, userid, noticeName, noticeContent, noticeDate, views);
				
				noticeList.add(noticevo);
				
			}// end of while---------------------
			
		 } finally{
			close();
		 }
		
		return noticeList;

	}// end of getNoticeVOList(int startRno, int endRno)---------------------------------------
	
	
	// 새 공지 입력
	@Override
	public boolean noticeInsert(String noticename, String noticeContent) throws SQLException {
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql="";
		
			int result=0;
						
			sql = " insert into tbl_notice(noticeno, userid, noticename, noticeContent, noticeDate, views) "
				+ " values(seq_tbl_notice.nextval, 'admin', ?, ?, sysdate, 0)";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticename);
			pstmt.setString(2, noticeContent);
	
			
			result=pstmt.executeUpdate();
			if(result==0)return false;
			
			return true;
		}catch(Exception ex){
			System.out.println("noticeInsert 에러 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
		
	}// noticeInsert(QnaVO qna_board)----------------------------------------
	
	
	// 공지사항 삭제
	@Override
	public boolean noticeDelete(int num) throws SQLException {
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String notice_delete_sql="delete from tbl_notice where noticeno=?";
			
			int result=0;

			pstmt=conn.prepareStatement(notice_delete_sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			if(result==0)return false;
			
			return true;
		}catch(Exception ex){
			System.out.println("noticeDelete 에러 : "+ex);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception ex) {}
		}
		
		return false;
		
	}// noticeDelete(int num)------------------------------------------
	
	
	// 공지사항 수정.
	@Override
	public boolean noticeModify(String num, String noticename, String noticeContent) throws SQLException {
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql="update tbl_notice set noticename=?, noticeContent=? "
					+ "where noticeno=?";
		
			
			System.out.println("num1" + num);

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, noticename);
			pstmt.setString(2, noticeContent);
			pstmt.setInt(3, Integer.parseInt(num));
			
			System.out.println("num2" + num);
			
			pstmt.executeUpdate();
			
			return true;
			
		}catch(Exception ex){
			System.out.println("noticeModify 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			}
		
		return false;
		
	}// noticeModify(int num)-------------------------------------------


	//글의 개수 구하기.
	@Override
	public int getTotalQnaCount() throws SQLException {
		
		int cnt = 0;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			pstmt = conn.prepareStatement("select count(*) from board_qna");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				cnt = rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("getTotalQnaCount 에러: " + ex);			
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return cnt;
		
	}// getTotalQnaCount()--------------------------------------------------


	//글 목록 보기.	
	@Override
	public List<QnaVO> getBoardList(int page, int limit) throws SQLException {

		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql = " select * "
					   + " from "
				       + "( "
				       + "   select rownum as rnum, board_qna_num, board_qna_userid, board_qna_subject, "
				       + "          board_qna_content, board_qna_file, board_qna_re_ref, board_qna_re_lev, "
				       + "          board_qna_re_seq, board_qna_readcount, to_char(board_qna_date, 'yyyy-mm-dd') AS board_qna_date "
				       + "   from (select *"
				       + "         from board_qna"
				       + "         order by board_qna_re_ref desc,"
				       + "                  board_qna_re_seq asc)"
				       + ") "
				       + " where rnum >= ? and rnum <= ? ";
			
			List<QnaVO> list = new ArrayList<QnaVO>();
			
			int startrow = (page-1) * 5 + 1; //읽기 시작할 row 번호.
			int endrow = startrow + limit - 1; //읽을 마지막 row 번호.		
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				QnaVO board = new QnaVO();
				board = new QnaVO();
				board.setBoard_qna_num(rs.getInt("board_qna_num"));
				board.setBoard_qna_userid(rs.getString("board_qna_userid"));
				board.setBoard_qna_subject(rs.getString("board_qna_subject"));
				board.setBoard_qna_content(rs.getString("board_qna_content"));
				board.setBoard_qna_file(rs.getString("board_qna_file"));
				board.setBoard_qna_re_ref(rs.getInt("board_qna_re_ref"));
				board.setBoard_qna_re_lev(rs.getInt("board_qna_re_lev"));
				board.setBoard_qna_re_seq(rs.getInt("board_qna_re_seq"));
				board.setBoard_qna_readcount(rs.getInt("board_qna_readcount"));
				board.setBoard_qna_date(rs.getString("board_qna_date"));
				list.add(board);
			}
			
			return list;
			
		}catch(Exception ex){
			System.out.println("getBoardList 에러 : " + ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return null;
		
	}// getBoardList(int page, int limit)------------------------------------

	//글 내용 보기.
	@Override
	public QnaVO getDetail(int board_qna_num) throws SQLException {
		
		QnaVO board = null;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String sql = " select board_qna_num, board_qna_userid, board_qna_pw, board_qna_subject, "
				       + "        board_qna_content, board_qna_file, board_qna_re_ref, board_qna_re_lev, "
				       + "        board_qna_re_seq, board_qna_readcount, to_char(board_qna_date, 'yyyy-mm-dd') AS board_qna_date "
				       + " from board_qna where board_qna_num = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_qna_num);
			
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				board = new QnaVO();
				board.setBoard_qna_num(rs.getInt("board_qna_num"));
				board.setBoard_qna_userid(rs.getString("board_qna_userid"));
				board.setBoard_qna_pw(rs.getString("board_qna_pw"));
				board.setBoard_qna_subject(rs.getString("board_qna_subject"));
				board.setBoard_qna_content(rs.getString("board_qna_content"));
				board.setBoard_qna_file(rs.getString("board_qna_file"));
				board.setBoard_qna_re_ref(rs.getInt("board_qna_re_ref"));
				board.setBoard_qna_re_lev(rs.getInt("board_qna_re_lev"));
				board.setBoard_qna_re_seq(rs.getInt("board_qna_re_seq"));
				board.setBoard_qna_readcount(rs.getInt("board_qna_readcount"));
				board.setBoard_qna_date(rs.getString("board_qna_date"));
			}
			
			return board;
			
		}catch(Exception ex){
			System.out.println("getDetail 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt !=null)try{pstmt.close();}catch(SQLException ex){}
		}
		return null;
	
	}// getDetail(int board_qna_num)----------------------------------


	//글 등록.
	@Override
	public boolean boardInsert(QnaVO qna_board) throws SQLException {

		int num =0;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql="";
		
			int result=0;
			
			if(qna_board.getBoard_qna_subject() == null || qna_board.getBoard_qna_subject().equals("")
					|| qna_board.getBoard_qna_content() == null || qna_board.getBoard_qna_content().equals("")
					|| qna_board.getBoard_qna_content() == null || qna_board.getBoard_qna_pw().equals("")) {
				return false;
			}
		
			pstmt=conn.prepareStatement("select max(board_qna_num) from board_qna");
			rs = pstmt.executeQuery();
			
			if(rs.next())
				num =rs.getInt(1)+1;
			else
				num=1;
			
			sql="insert into board_qna (board_qna_num, board_qna_userid, board_qna_pw, board_qna_subject,"
					+"board_qna_content, board_qna_file, board_qna_re_ref, board_qna_re_lev, board_qna_re_seq,"
					+"board_qna_readcount, board_qna_date) values(?,?,?,?,?,?,?,?,?,?,sysdate)";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, qna_board.getBoard_qna_userid());
			pstmt.setString(3, qna_board.getBoard_qna_pw());
			pstmt.setString(4, qna_board.getBoard_qna_subject());
			pstmt.setString(5, qna_board.getBoard_qna_content());
			pstmt.setString(6, qna_board.getBoard_qna_file()); //답장에는 파일을 업로드하지 않음.
			pstmt.setInt(7, num);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, 0);
			pstmt.setInt(10, 0);
			
			result=pstmt.executeUpdate();
			if(result==0)return false;
			
			return true;
		}catch(Exception ex){
			System.out.println("boardInsert 에러 : "+ex);
		}finally{
			if(rs!=null) try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
		}
		return false;
		
	}// boardInsert(QnaVO qna_board)--------------------------------


	
	//글 답변.
	@Override
	public int boardReply(QnaVO qna_board) throws SQLException {
		
		String board_max_sql="select max(board_qna_num) from board_qna";
		
		if(qna_board.getBoard_qna_subject() == null || qna_board.getBoard_qna_subject().equals("")
				|| qna_board.getBoard_qna_content() == null || qna_board.getBoard_qna_content().equals("")) {
			return 0;
		}
		
		String sql="";
		int num=0;
		int result=0;
		
		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			int re_ref = qna_board.getBoard_qna_re_ref();
			int re_lev = qna_board.getBoard_qna_re_lev();
			int re_seq = qna_board.getBoard_qna_re_seq();
		
			pstmt=conn.prepareStatement(board_max_sql);
			rs = pstmt.executeQuery();
			if(rs.next())
				num = rs.getInt(1)+1;
			else num = 1;
			
			sql="update board_qna set board_qna_re_seq=board_qna_re_seq+1 "
			   + "where board_qna_re_ref=? "
			   +"and board_qna_re_seq>?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,re_ref);
			pstmt.setInt(2,re_seq);
			result = pstmt.executeUpdate();
			
			re_seq = re_seq + 1;
			re_lev = re_lev+1;
			
			sql="insert into board_qna (board_qna_num, board_qna_userid, board_qna_pw, board_qna_subject,"
			+"board_qna_content, board_qna_file, board_qna_re_ref, board_qna_re_lev, board_qna_re_seq,"
			+"board_qna_readcount, board_qna_date) values(?,?,?,?,?,?,?,?,?,?,sysdate)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, qna_board.getBoard_qna_userid());
			pstmt.setString(3, "9999");
			pstmt.setString(4, qna_board.getBoard_qna_subject());
			pstmt.setString(5, qna_board.getBoard_qna_content());
			pstmt.setString(6, ""); //답장에는 파일을 업로드하지 않음.
			pstmt.setInt(7, re_ref);
			pstmt.setInt(8, re_lev);
			pstmt.setInt(9, re_seq);
			pstmt.setInt(10, 0);
			
			pstmt.executeUpdate();
			
			return num;
			
		}catch(SQLException ex){
			System.out.println("boardReply 에러 : "+ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
		}
		return 0;
		
	}// boardReply(BoardBean board)----------------------------------------


	//글 수정.
	@Override
	public boolean boardModify(QnaVO modifyboard) throws SQLException {

		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql="update board_qna set board_qna_subject=?,board_qna_content=? "
					+ "where board_qna_num=?";
		

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, modifyboard.getBoard_qna_subject());
			pstmt.setString(2, modifyboard.getBoard_qna_content());
			pstmt.setInt(3, modifyboard.getBoard_qna_num());
			pstmt.executeUpdate();
			return true;
		}catch(Exception ex){
			System.out.println("boardModify 에러 : " + ex);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			}
		return false;
		
	}// boardModify(QnaVO modifyboard)--------------------------------------


	//글 삭제.
	@Override
	public boolean boardDelete(int board_qna_num) throws SQLException {

		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			String board_delete_sql="delete from board_qna where board_qna_num=?";
			
			int result=0;

			pstmt=conn.prepareStatement(board_delete_sql);
			pstmt.setInt(1, board_qna_num);
			result=pstmt.executeUpdate();
			if(result==0)return false;
			
			return true;
		}catch(Exception ex){
			System.out.println("boardDelete 에러 : "+ex);
		}finally{
			try{
				if(pstmt!=null)pstmt.close();
			}catch(Exception ex) {}
		}
		
		return false;
		
	}// boardDelete(int board_qna_num)---------------------------------


	//조회수 업데이트.
	@Override
	public void setReadCountUpdate(int board_qna_num) throws SQLException {

		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql="update board_qna set board_qna_readcount = "+
				"board_qna_readcount+1 where board_qna_num = "+board_qna_num;

				pstmt=conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}catch(SQLException ex){
				System.out.println("setReadCountUpdate 에러 : "+ex);
			}
		
	}// setReadCountUpdate(int board_qna_num)------------------------------


	
	//글쓴이인지 확인.
	@Override
	public boolean isBoardWriter(int board_qna_num, String board_qna_pw) throws SQLException {

		try{
			conn = ds.getConnection();
			// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
		
			String sql = " select * "
					   + " from board_qna"
					   + " where board_qna_num=? ";
		
	
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, board_qna_num);
			rs=pstmt.executeQuery();
			rs.next();
			
			if(board_qna_pw.equals(rs.getString("board_qna_pw"))){
				return true;
			}
		}catch(SQLException ex){
			System.out.println("isBoardWriter 에러 : "+ex);
		}
		return false;
		
	}// isBoardWriter(int board_qna_num, String board_qna_pw)-------------


	


	


	



	
}



