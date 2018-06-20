package board.noticeMapcontroller;


import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.NoticeVO;
import common.controller.AbstractController;
import member.model.UserinfoVO;

public class NoticeDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
	   	boolean result=false;
	   	
	   	int num = Integer.parseInt(req.getParameter("noticeno"));
	   	
	   	InterBoardDAO bdao = new BoardDAO();
	   	
	   	result = bdao.noticeDelete(num);
	   	
	   	if(result==false){
	   		super.alertMsg(req, "공지사항 삭제 실패", "javascript:history.back();");
	   		return;
	   	}
	   	
	   	
	   	super.setRedirect(false);
		
		super.alertMsg(req, "공지사항 삭제 성공", "noticeList.do");

	}

}
