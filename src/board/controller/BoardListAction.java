package board.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import board.model.*;

public class BoardListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		BoardDAO bdao=new BoardDAO();  //DB연결
		List boardlist=new ArrayList();
		
	  	int page = 1;
		int limit = 10;
		
		if(req.getParameter("page")!=null){
			page=Integer.parseInt(req.getParameter("page"));
		}
		
		int listcount = bdao.getTotalQnaCount(); //총 리스트 수를 받아옴.
		boardlist = bdao.getBoardList(page, limit); //리스트를 받아옴.
		
		//총 페이지 수.
   		int maxpage = (int)((double)listcount / limit + 0.95); //0.95를 더해서 올림 처리.
   		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
   		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
   		//현재 페이지에 보여줄 마지막 페이지 수.(10, 20, 30 등...)
   		int endpage = maxpage;
   		
   		if (endpage > startpage + 10 - 1) endpage=startpage + 10 - 1;
   		
   		req.setAttribute("page", page);		  //현재 페이지 수.
   		req.setAttribute("maxpage", maxpage); //최대 페이지 수.
   		req.setAttribute("startpage", startpage); //현재 페이지에 표시할 첫 페이지 수.
   		req.setAttribute("endpage", endpage);     //현재 페이지에 표시할 끝 페이지 수.
   		req.setAttribute("listcount",listcount); //글 수.
   		req.setAttribute("boardlist", boardlist); //게시판 값 .
		
		
   		super.setRedirect(false);
	   	super.setViewPage("/WEB-INF/board/qna_board_list.jsp");
   		
   		
	 }
 }