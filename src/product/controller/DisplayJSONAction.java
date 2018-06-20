package product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.LikedislikeVO;
import product.model.ProductDAO;
import product.model.ProductVO;



public class DisplayJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String start = req.getParameter("start");	//1
		String len = req.getParameter("len");	//5
		String pcode = req.getParameter("pcode"); //제품코드
		

		if(start == null ||start.trim().isEmpty()) {
			start ="1";
		}
		if(len == null|| len.trim().isEmpty()) {
			len ="5";
		}
		
		int startRno = Integer.parseInt(start);
		//시작행번호
		
		int endRno = startRno+Integer.parseInt(len)-1;
		//끝행번호
		
		InterproductDAO pdao = new ProductDAO();
		
		List<LikedislikeVO> memoList = pdao.getProductVOListByUserid(startRno,endRno,pcode);
		
		JSONArray jsonarray = new JSONArray();
		
		if(memoList != null && memoList.size() > 0) {
		
			for(LikedislikeVO lvo : memoList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("fk_pcode",lvo.getFk_pcode());
				jsonObj.put("fk_userid",lvo.getFk_userid() );
				jsonObj.put("likedislike",lvo.getLikedislike());
				jsonObj.put("comments",lvo.getComments() );
				
				jsonarray.add(jsonObj);
			}//end of for----------------------------
		}
		
		String str_jsonarray = jsonarray.toString();
		req.setAttribute("str_jsonarray", str_jsonarray);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/malldisplayJSON.jsp");

	}

}
