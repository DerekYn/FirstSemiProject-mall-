package board.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.UserinfoVO;

public class BoardDeleteView extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	   	
		
		HttpSession session = req.getSession();
	    UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
	      
	      
	      if(loginuser == null) {
	         
	         String msg = "로그인후 이용해주세요.";
	         String loc = "javascript:history.back();";
	         
	         req.setAttribute("msg", msg);
	         req.setAttribute("loc", loc);
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	         return;
	         
	      }
		
		
	   	super.setRedirect(false);
	   	super.setViewPage("/WEB-INF/board/qna_board_delete.jsp");
   		
	 }
}