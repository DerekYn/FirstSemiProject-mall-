package product.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class LikeDislikeCountShowAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String pcode = req.getParameter("pcode");
		InterproductDAO pdao = new ProductDAO();
		
		HashMap<String, String> map = pdao.getLikeDislikeCount(pcode);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("likecnt", map.get("likecnt"));
		jsonObj.put("dislikecnt", map.get("dislikecnt"));
		
		req.setAttribute("jsonObj", jsonObj);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/likeDislikeCountShow.jsp");
	}

}
