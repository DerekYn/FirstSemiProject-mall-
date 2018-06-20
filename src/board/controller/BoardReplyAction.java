package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import board.model.*;

public class BoardReplyAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		 	
		 	
			BoardDAO bdao=new BoardDAO();
			QnaVO boarddata=new QnaVO();
	   		int result=0;
	   		
	   		boarddata.setBoard_qna_num(Integer.parseInt(req.getParameter("board_qna_num")));
	   		boarddata.setBoard_qna_userid(req.getParameter("board_qna_userid"));
	   		boarddata.setBoard_qna_subject(req.getParameter("board_qna_subject"));
	   		boarddata.setBoard_qna_content(req.getParameter("board_qna_content"));
	   		boarddata.setBoard_qna_re_ref(Integer.parseInt(req.getParameter("board_qna_re_ref")));
	   		boarddata.setBoard_qna_re_lev(Integer.parseInt(req.getParameter("board_qna_re_lev")));
	   		boarddata.setBoard_qna_re_seq(Integer.parseInt(req.getParameter("board_qna_re_seq")));
	   		
	   		result = bdao.boardReply(boarddata);
	   		if(result == 0){
	   			super.alertMsg(req, "답장 실패", "javascript:history.back();");
	   			return;
	   		}
	   		System.out.println("답장 완료");
	   		
	   		super.setRedirect(true);
	   		super.setViewPage("boardDetail.do?num="+result);
	}  	
}