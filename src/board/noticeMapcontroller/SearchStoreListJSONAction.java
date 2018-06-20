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

public class SearchStoreListJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		InterBoardDAO sdao = new BoardDAO(); 
		
		String shopTypeCd = req.getParameter("shopTypeCd");
		String storeAreaCd = req.getParameter("storeAreaCd");
		
		if("매장전체".equals(shopTypeCd)) {
			shopTypeCd = "";
		}
		if("지역전체".equals(storeAreaCd)) {
			storeAreaCd = "";
		}
		
		int start = 1;
		int startRno = start;
		// 시작행번호                1            6            11
		
		int len = 5;
		int endRno = startRno+len-1;
		// 끝행번호  !!공식!!  1+5-1(==5)   6+5-1(==10)   11+5-1(==15)
		
	    
		List<StoremapVO> selectedStoremapList = sdao.getSelectedStoreList(shopTypeCd, storeAreaCd, startRno, endRno);
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
		System.out.println("===> 확인용 JSON 형태의 str_jsonArr 의 값 : " + str_jsonArr);
		req.setAttribute("str_jsonArr", str_jsonArr);
		
			
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/storeListJSON.jsp");
		
	}

}
