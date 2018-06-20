package board.noticeMapcontroller;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.NoticeVO;
import common.controller.AbstractController;

public class NoticeWriteEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
				
		InterBoardDAO ndao = new BoardDAO(); 
		
		String noticename = req.getParameter("noticename");
		String noticeContent = req.getParameter("noticeContent");
		
		System.out.println("noticename = " + noticename);
		System.out.println("noticeContent = " + noticeContent);
		
		boolean result = ndao.noticeInsert(noticename, noticeContent);
		
		if(result == false){
   			super.alertMsg(req, "공지사항 등록 실패", "javascript:history.back();");
   			return;
   		}

		this.setRedirect(false);
		
		super.alertMsg(req, "공지사항 등록 성공", "noticeList.do");
		

	}

}
