package board.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import board.model.*;

public class BoardModifyAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		 

		 boolean result = false;
		 
		 int num=Integer.parseInt(req.getParameter("board_qna_num"));
		 
		 BoardDAO bdao=new BoardDAO();
		 QnaVO boarddata=new QnaVO();
		 
		 boolean usercheck = bdao.isBoardWriter(num, req.getParameter("board_qna_pw"));
		 if(usercheck==false){
		   		PrintWriter out=res.getWriter();
		   		out.println("<script>");
		   		out.println("alert('수정할 권한이 없습니다.');");
		   		out.println("location.href='boardList.do';");
		   		out.println("</script>");
		   		out.close();
		   		return;
		 }
		 try{
			 boarddata.setBoard_qna_num(num);
			 boarddata.setBoard_qna_subject(req.getParameter("board_qna_subject"));
			 boarddata.setBoard_qna_content(req.getParameter("board_qna_content"));
			 
			 result = bdao.boardModify(boarddata);
			 if(result==false){
		   		System.out.println("게시판 수정 실패");
		   		return;
		   	 }
		   	 System.out.println("게시판 수정 완료");
		   	 
		   	super.setRedirect(true);
		   	super.setViewPage("boardDetail.do?num="+boarddata.getBoard_qna_num());
	   	 }catch(Exception ex){
	   			ex.printStackTrace();	 
		 }
		 
		 return;
	 }
}