package board.model;

import java.sql.SQLException;
import java.util.*;

public interface InterBoardDAO {
	
	// 지도
	
	// *** tbl_storemap 테이블의 상점 리스트를 조회해주는 추상 메소드 *** //
	List<StoremapVO> getStoreList() throws SQLException;
	
	// *** tbl_storemap 상점 개수를 조회해주는 추상 메소드 *** //
	int getTotalStoreCount() throws SQLException;
	
	// *** tbl_storemap 상점 개수를 더보기 처리하기 위해 row을 이용해 조회해주는 추상메소드 *** //
	List<StoremapVO> getStoremapVOList(int startRno, int endRno) throws SQLException;
	
	// *** tbl_storemap 테이블의 구분별 상점 리스트를 조회해주는 추상 메소드 *** //
	List<StoremapVO> getSelectedStoreList(String shopTypeCd, String storeAreaCd, int startRno, int endRno) throws SQLException;
	
	// *** tbl_storemap 테이블의 위도 ,경도를 조회해주는 추상 메소드 *** //
	double[] getLatitudeLongitude(int storeno) throws SQLException;
	
	
	
	
	// 공지사항
		
	// *** tbl_notice 공지 개수를 조회해주는 추상 메소드 *** //
	int getTotalNoticeCount() throws SQLException;
	
	// *** tbl_notice 공지 개수를 더보기 처리하기 위해 row을 이용해 조회해주는 추상메소드 *** //
	List<NoticeVO> getNoticeVOList(int startRno, int endRno) throws SQLException;
	
	// 새 공지사항 등록.
	boolean noticeInsert(String noticename, String noticeContent) throws SQLException;
	
	// 공지사항 삭제.
	boolean noticeDelete(int num) throws SQLException;
	
	// 공지사항 수정.
	boolean noticeModify(String num, String noticename, String noticeContent) throws SQLException;
	
	
	// QnA
	
	// 글의 개수 구하기.
	int getTotalQnaCount() throws SQLException;
	
	//글 목록 보기.	
	List<QnaVO> getBoardList(int page, int limit) throws SQLException;
	
	//글 내용 보기.
	QnaVO getDetail(int board_qna_num) throws SQLException;
	
	//글 등록.
	boolean boardInsert(QnaVO qna_board) throws SQLException;
	
	//글 답변.
	int boardReply(QnaVO qna_board) throws SQLException;
	
	//글 수정.
	boolean boardModify(QnaVO modifyboard) throws SQLException;
	
	//글 삭제.
	boolean boardDelete(int board_qna_num) throws SQLException;
	
	//조회수 업데이트.
	void setReadCountUpdate(int board_qna_num) throws SQLException;
	
	//글쓴이인지 확인.
	boolean isBoardWriter(int board_qna_num, String board_qna_pw) throws SQLException;

}



