package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import board.model.*;

public class BoardReplyView extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 	
		
		HttpSession session = req.getSession();
	    UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
		if(loginuser == null || !"admin".equals(loginuser.getUserid())) {
	         
	         String msg = "관리자로 로그인 후 이용해주세요.";
	         String loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	         
	      }
		
		
			BoardDAO bdao=new BoardDAO();
			QnaVO boarddata=new QnaVO();
	   		
	   		int num=Integer.parseInt(req.getParameter("num"));
	   		
	   		boarddata = bdao.getDetail(num);
	   		
	   		if(boarddata==null){
	   			super.alertMsg(req, "답장 페이지 이동 실패", "javascript:history.back();");
	   			return;
	   		}
	   		System.out.println("답장 페이지 이동 완료");
	   		
	   		req.setAttribute("boarddata", boarddata);
	   		
	   		super.setRedirect(false);
	   		super.setViewPage("/WEB-INF/board/qna_board_reply.jsp");

	   		
	}
}