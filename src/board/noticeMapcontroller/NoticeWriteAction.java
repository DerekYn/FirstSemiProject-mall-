package board.noticeMapcontroller;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.NoticeVO;
import common.controller.AbstractController;

public class NoticeWriteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		

		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/noticeWrite.jsp");
		

	}

}
