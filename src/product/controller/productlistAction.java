package product.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class productlistAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
		
		if(loginuser == null) {
			
			String msg = "관리자 로그인후 이용해주세요.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return;
			
		}
		else if(loginuser != null && !"admin".equals(loginuser.getUserid())) {
			String msg = "관리자 로그인후 이용해주세요.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return;
			
		}
		
		InterproductDAO pdao = new ProductDAO();
		
		List<HashMap<String, String>> produtlists = pdao.getAllProducts();
		
		req.setAttribute("produtlists", produtlists);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/productlists.jsp");

	}

}
