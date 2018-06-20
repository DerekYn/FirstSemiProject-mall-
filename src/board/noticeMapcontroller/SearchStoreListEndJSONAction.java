package board.noticeMapcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.StoremapVO;
import common.controller.AbstractController;

public class SearchStoreListEndJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String start = req.getParameter("start");  // 1
		String len = req.getParameter("len");      // 3
		
		if(start == null || start.trim().isEmpty()) {
			start = "1";
		}
		
		if(len == null || len.trim().isEmpty()) {
			len = "5";
		}
		
		
		System.out.println("==> 확인용 DisplayJSONAction.java  start : " + start); 
		System.out.println("==> 확인용 DisplayJSONAction.java  len : " + len);
		
		int startRno = Integer.parseInt(start);
		// 시작행번호                1            6            11
		
		int endRno = startRno+Integer.parseInt(len)-1;
		// 끝행번호  !!공식!!  1+5-1(==5)   6+5-1(==10)   11+5-1(==15)
		
		InterBoardDAO sdao = new BoardDAO();
		
		List<StoremapVO> selectedStoremapList = sdao.getStoremapVOList(startRno, endRno);
		JSONArray jsonArr = new JSONArray();
		
		if(selectedStoremapList != null && selectedStoremapList.size() > 0) {
			
			for(StoremapVO vo : selectedStoremapList) {
				JSONObject obj = new JSONObject();
				
				obj.put("storeno", vo.getStoreno());
				obj.put("storectg", vo.getStorectg());
				obj.put("storeName", vo.getStoreName());
				obj.put("latitude", vo.getLatitude());
				obj.put("longitude", vo.getLongitude());
				obj.put("zindex", vo.getZindex());
				obj.put("tel", vo.getTel());
				obj.put("addr", vo.getAddr());
				obj.put("sopen", vo.getSopen());
				obj.put("sclose", vo.getSclose());
				
				jsonArr.add(obj);
				
			}
			
		}
		
		String str_jsonArr = jsonArr.toString();
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/storeListJSON.jsp"); 
		
	}

}
