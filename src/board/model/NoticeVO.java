package board.model;

public class NoticeVO {
	
	private int noticeno;	// 공지사항 no
	private String userid;  // 작성자(admin)
	private String noticeName;	// 공지사항 제목
	private String noticeContent;	// 공지사항 내용
	private String noticeDate;	// 공지 날짜
	private int views;		// 조회수
	
	public NoticeVO() {}
	
	public NoticeVO(int noticeno, String userid, String noticeName, String noticeContent, String noticeDate,
			int views) {
		this.noticeno = noticeno;
		this.userid = userid;
		this.noticeName = noticeName;
		this.noticeContent = noticeContent;
		this.noticeDate = noticeDate;
		this.views = views;
	}

	public int getNoticeno() {
		return noticeno;
	}

	public void setNoticeno(int noticeno) {
		this.noticeno = noticeno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	
	
	

}
