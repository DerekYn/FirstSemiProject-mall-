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

public class NoticeModifyEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
				
	   	boolean result = false;
	   	   	
	   	InterBoardDAO bdao = new BoardDAO();
	   	
	   	String num = req.getParameter("noticeno");
	   	String noticename = req.getParameter("noticename");
		String noticeContent = req.getParameter("noticeContent");
		
	   	result = bdao.noticeModify(num, noticename, noticeContent);
	   	
		if(result==false){
			 super.alertMsg(req, "공지사항 수정 실패", "javascript:history.back();");
			 return;
	   	}
		 
	   	super.setRedirect(false);
		
		super.alertMsg(req, "공지사항 수정 성공", "noticeList.do");

	}

}
