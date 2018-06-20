package board.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.UserinfoVO;

public class BoardDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		
		HttpSession session = req.getSession();
	    UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
	   	boolean result=false;
	   	boolean usercheck=false;
	   	
	   	int num = Integer.parseInt(req.getParameter("num"));
	   	
	   	BoardDAO bdao = new BoardDAO();
	   	
	   	usercheck = bdao.isBoardWriter(num, req.getParameter("board_qna_pw"));
	   	
	   	if(usercheck == false && !"admin".equals(loginuser.getUserid())){
	   		PrintWriter out=res.getWriter();
	   		out.println("<script>");
	   		out.println("alert('삭제할 권한이 없습니다.');");
	   		out.println("location.href='boardList.do';");
	   		out.println("</script>");
	   		out.close();
	   		return;
	   	}
	   	
	   	result = bdao.boardDelete(num);
	   	
	   	if(result==false){
	   		super.alertMsg(req, "게시판 삭제 실패", "javascript:history.back();");
	   		return;
	   	}
	   	
	   	System.out.println("게시판 삭제 성공");
	   	
	   	super.setRedirect(false);
	   	super.setViewPage("boardList.do");
   		
	 }
}