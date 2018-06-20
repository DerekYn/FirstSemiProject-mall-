package product.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class LikedislikeVO {

	private int fk_pcode;
	private String fk_userid;
	private int likedislike;
	private String comments;
	
	public LikedislikeVO() {}
	
	public LikedislikeVO(int fk_pcode, String fk_userid, int likedislike, String comments) {
		this.fk_pcode = fk_pcode;
		this.fk_userid = fk_userid;
		this.likedislike = likedislike;
		this.comments = comments;
	}

	public int getFk_pcode() {
		return fk_pcode;
	}

	public void setFk_pcode(int fk_pcode) {
		this.fk_pcode = fk_pcode;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public int getLikedislike() {
		return likedislike;
	}

	public void setLikedislike(int likedislike) {
		this.likedislike = likedislike;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
