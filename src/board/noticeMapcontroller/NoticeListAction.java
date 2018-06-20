package board.noticeMapcontroller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.NoticeVO;
import common.controller.AbstractController;

public class NoticeListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		InterBoardDAO ndao = new BoardDAO(); 
		
		int totalNoticeCount = ndao.getTotalNoticeCount();
		
		req.setAttribute("totalNoticeCount", totalNoticeCount);
		
		System.out.println(totalNoticeCount);
		
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/notice.jsp");
		

	}

}
