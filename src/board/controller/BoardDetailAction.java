package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import board.model.*;

public class BoardDetailAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 
		
   		
		BoardDAO bdao = new BoardDAO();
		QnaVO boarddata = new QnaVO();
	   	
		int num=Integer.parseInt(req.getParameter("num"));
		bdao.setReadCountUpdate(num);
	   	boarddata = bdao.getDetail(num);
	   	
	   	if(boarddata==null){
	   		super.alertMsg(req, "상세보기 실패", "javascript:history.back();");
	   		return;
	   	}
	   	System.out.println("상세보기 성공");
	   	
	   	req.setAttribute("boarddata", boarddata);
	   	
	   	super.setRedirect(false);
	   	super.setViewPage("/WEB-INF/board/qna_board_view.jsp");
	   	

	 }
}