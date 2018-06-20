package board.noticeMapcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.StoremapVO;
import common.controller.AbstractController;

public class SearchStoreListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		InterBoardDAO sdao = new BoardDAO(); 
		    
		List<StoremapVO> storemapList = sdao.getStoreList();
		
		int totalStoreCount = sdao.getTotalStoreCount();
		
		req.setAttribute("storemapList", storemapList);
		req.setAttribute("totalStoreCount", totalStoreCount);
			
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/storeList.jsp");
		
	}

}
