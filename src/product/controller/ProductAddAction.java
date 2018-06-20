package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.ProductDAO;
import util.my.MyUtil;

public class ProductAddAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		String method = req.getMethod();
		
		if(!"post".equalsIgnoreCase(method)) {
			MyUtil.alertMsg(req, this, "잘못된 접근입니다.", "javascript:history.back();");
			return;
		}
		
		int pcode = Integer.parseInt(req.getParameter("pcode"));
		int pqty = Integer.parseInt(req.getParameter("pqty"));
		
		ProductDAO pdao = new ProductDAO();
		int result = pdao.addProduct(pcode, pqty);
		
		if(result > 0) {
			req.setAttribute("result", "success");
		}
		
		else {
			req.setAttribute("result", "fail");
		}
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/productadd.jsp");
		
	}

}