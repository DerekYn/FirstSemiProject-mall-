package product.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.ProductDAO;


public class DislikeAddAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String userid = req.getParameter("userid");
		String pcode = req.getParameter("pcode");
		String comments = req.getParameter("comments");
		
		JSONObject jsonObj = new JSONObject();
		
		if("".equals(userid)) {
			jsonObj.put("msg", "로그인을 하신후 \n싫어요를 클릭하세요");
			
			
		} else {
			InterproductDAO pdao = new ProductDAO();
			
			try {
				int n = pdao.insertDislike(userid, pcode, comments);
				
				if(n > 0) {
					jsonObj.put("msg", "해당제품에 \n싫어요를 클릭하셨습니다.");
				}
				
			} catch (SQLException e) {
				jsonObj.put("msg", "이미 해당제품에 \n싫어요를 클릭하셨습니다.");
				e.printStackTrace();
			}
			
		
		}// end of if~else ------------------------------------------
		
		String str_jsonObj = jsonObj.toString();
		
		req.setAttribute("str_jsonObj", str_jsonObj);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/dislikeAdd.jsp");
		
		
	}

}
