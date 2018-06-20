package board.noticeMapcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.NoticeVO;
import board.model.StoremapVO;
import common.controller.AbstractController;

public class NoticeListEndJSONAction extends AbstractController {

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
		
		InterBoardDAO ndao = new BoardDAO();
		
		List<NoticeVO> noticeList = ndao.getNoticeVOList(startRno, endRno);
		
		JSONArray jsonArr = new JSONArray();
		
		if(noticeList != null && noticeList.size() > 0) {
			
			for(NoticeVO vo : noticeList) {
				JSONObject obj = new JSONObject();
				
				obj.put("noticeno", vo.getNoticeno());
				obj.put("userid", vo.getUserid());
				obj.put("noticeName", vo.getNoticeName());
				obj.put("noticeContent", vo.getNoticeContent());
				obj.put("noticeDate", vo.getNoticeDate());
				obj.put("views", vo.getViews());
				
				jsonArr.add(obj);
				
			}
			
		}
		
		String str_jsonArr = jsonArr.toString();
		req.setAttribute("str_jsonArr", str_jsonArr);
		
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/board/noticeListJSON.jsp"); 
		
	}

}
