package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import board.model.*;

public class BoardModifyView extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		 	
		 	
		 	
		 	String userid = req.getParameter("userid");
		 	
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
			
			if (!userid.equals(loginuser.getUserid()) && !"admin".equals(loginuser.getUserid())) {
				
				String msg = "작성자 또는 관리자로 로그인 후 이용해주세요.";
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
		   		super.alertMsg(req, "(수정)상세보기 실패", "javascript:history.back();");
		   		return;
		   	}
		   	System.out.println("(수정)상세보기 성공");
		   	
		   	req.setAttribute("boarddata", boarddata);
		   	super.setRedirect(false);
		   	super.setViewPage("/WEB-INF/board/qna_board_modify.jsp");
	   		
	 }
}