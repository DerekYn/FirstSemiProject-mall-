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

public class NoticeModifyAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
				
		String num = req.getParameter("noticeno");
		String noticename = req.getParameter("noticename");
		String noticeContent = req.getParameter("noticeContent");
		
		System.out.println("noticeno" + num);
		
		req.setAttribute("noticeno", num);
		req.setAttribute("noticename", noticename);
		req.setAttribute("noticeContent", noticeContent);

		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/noticeModify.jsp");

	}

}
