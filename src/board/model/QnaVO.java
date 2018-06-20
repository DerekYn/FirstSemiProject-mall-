package board.model;

import java.sql.Date;

public class QnaVO {
	private int board_qna_num;
	private String board_qna_userid;
	private String board_qna_pw;
	private String board_qna_subject;
	private String board_qna_content;
	private String board_qna_file;
	private int board_qna_re_ref;
	private int board_qna_re_lev;
	private int board_qna_re_seq;
	private int board_qna_readcount;
	private String board_qna_date;
	
	public QnaVO() {}
	
	public QnaVO(int board_qna_num, String board_qna_userid, String board_qna_pw, String board_qna_subject,
			String board_qna_content, String board_qna_file, int board_qna_re_ref, int board_qna_re_lev,
			int board_qna_re_seq, int board_qna_readcount, String board_qna_date) {
		this.board_qna_num = board_qna_num;
		this.board_qna_userid = board_qna_userid;
		this.board_qna_pw = board_qna_pw;
		this.board_qna_subject = board_qna_subject;
		this.board_qna_content = board_qna_content;
		this.board_qna_file = board_qna_file;
		this.board_qna_re_ref = board_qna_re_ref;
		this.board_qna_re_lev = board_qna_re_lev;
		this.board_qna_re_seq = board_qna_re_seq;
		this.board_qna_readcount = board_qna_readcount;
		this.board_qna_date = board_qna_date;
	}

	public int getBoard_qna_num() {
		return board_qna_num;
	}

	public void setBoard_qna_num(int board_qna_num) {
		this.board_qna_num = board_qna_num;
	}

	public String getBoard_qna_userid() {
		return board_qna_userid;
	}

	public void setBoard_qna_userid(String board_qna_userid) {
		this.board_qna_userid = board_qna_userid;
	}

	public String getBoard_qna_pw() {
		return board_qna_pw;
	}

	public void setBoard_qna_pw(String board_qna_pw) {
		this.board_qna_pw = board_qna_pw;
	}

	public String getBoard_qna_subject() {
		return board_qna_subject;
	}

	public void setBoard_qna_subject(String board_qna_subject) {
		this.board_qna_subject = board_qna_subject;
	}

	public String getBoard_qna_content() {
		return board_qna_content;
	}

	public void setBoard_qna_content(String board_qna_content) {
		this.board_qna_content = board_qna_content;
	}

	public String getBoard_qna_file() {
		return board_qna_file;
	}

	public void setBoard_qna_file(String board_qna_file) {
		this.board_qna_file = board_qna_file;
	}

	public int getBoard_qna_re_ref() {
		return board_qna_re_ref;
	}

	public void setBoard_qna_re_ref(int board_qna_re_ref) {
		this.board_qna_re_ref = board_qna_re_ref;
	}

	public int getBoard_qna_re_lev() {
		return board_qna_re_lev;
	}

	public void setBoard_qna_re_lev(int board_qna_re_lev) {
		this.board_qna_re_lev = board_qna_re_lev;
	}

	public int getBoard_qna_re_seq() {
		return board_qna_re_seq;
	}

	public void setBoard_qna_re_seq(int board_qna_re_seq) {
		this.board_qna_re_seq = board_qna_re_seq;
	}

	public int getBoard_qna_readcount() {
		return board_qna_readcount;
	}

	public void setBoard_qna_readcount(int board_qna_readcount) {
		this.board_qna_readcount = board_qna_readcount;
	}

	public String getBoard_qna_date() {
		return board_qna_date;
	}

	public void setBoard_qna_date(String board_qna_date) {
		this.board_qna_date = board_qna_date;
	}
	
	
	
	
	
	
	
}