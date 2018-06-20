package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class ShowcommentAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userid= req.getParameter("userid");
		String pcode = req.getParameter("pcode");
		
		InterproductDAO pdao = new ProductDAO(); 

		JSONObject jsonObj = new JSONObject();
		
		
		
		String scomment = pdao.getshowcomment(userid, pcode);
		
		jsonObj.put("scomment",scomment);
		
		System.out.println(scomment);
		
		String str_jsonObj = jsonObj.toString();
		req.setAttribute("str_jsonObj", str_jsonObj);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/showcomment.jsp");

	}

}
