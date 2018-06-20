package product.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.controller.AbstractController;
import product.model.ProductDAO;

public class BestlistJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String start = req.getParameter("start");  // 1
		String len = req.getParameter("len");      // 3
		
		if(start == null || start.trim().isEmpty()) {
			start = "1";
		}
		
		if(len == null || len.trim().isEmpty()) {
			len = "3";
		}
		
		int startRno = Integer.parseInt(start);
		// 시작행번호                1            4            7
		
		int endRno = startRno + Integer.parseInt(len) - 1;
		// 끝행번호  !!공식!!  1+3-1(==3)   4+3-1(==6)   7+3-1(==9)
		
		// --------------------------------------------------------------------------------------- //
		
		ProductDAO pdao = new ProductDAO();
		
		String category = req.getParameter("category");
		System.out.println("category : " + category);
		
		List<HashMap<String, String>> bestlist = pdao.getHitList(category);

		JSONArray jsonArray = new JSONArray();
		
		if(bestlist != null && bestlist.size() > 0) {
		
			for(HashMap<String, String> map : bestlist) {
				
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("pcode", map.get("pcode"));
				jsonObj.put("pname", map.get("pname"));
				jsonObj.put("pcategory_fk", map.get("fk_pcategory"));
				jsonObj.put("pcolor", map.get("pcolor"));
				jsonObj.put("psize", map.get("psize"));
				jsonObj.put("pimage", map.get("pimage"));
				jsonObj.put("pqty", map.get("pqty"));
				jsonObj.put("price", map.get("price"));
				jsonObj.put("saleprice", map.get("saleprice"));
				jsonObj.put("pstatus", map.get("pstatus"));
				jsonObj.put("point", map.get("point"));
				jsonObj.put("pcontent", map.get("pcontent"));
				jsonObj.put("pinputdate", map.get("pinputdate"));
				
				String starpoint = pdao.getStarpoint(map.get("pname"));
				if(starpoint.equals("0")) {
					starpoint = "25";
				}
				
				jsonObj.put("starpoint", starpoint);
				
				jsonArray.add(jsonObj);
				
			}
			
		}
		
		String str_jsonArray = jsonArray.toString();
		req.setAttribute("str_jsonarray", str_jsonArray);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/bestlistJSON.jsp");
		
	}

}
