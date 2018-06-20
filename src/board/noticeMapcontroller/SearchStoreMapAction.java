package board.noticeMapcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.StoremapVO;
import common.controller.AbstractController;

public class SearchStoreMapAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String storeno = req.getParameter("storeno");
		int int_storeno = Integer.parseInt(storeno);
		
		InterBoardDAO sdao = new BoardDAO(); 
		
		double[] LatitudeLongitude = sdao.getLatitudeLongitude(int_storeno);

		List<StoremapVO> storemapList = sdao.getStoreList();
		
		req.setAttribute("storemapList", storemapList);
		req.setAttribute("LatitudeLongitude", LatitudeLongitude);
		
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/storeGoogleMap.jsp");
		
	}


}
