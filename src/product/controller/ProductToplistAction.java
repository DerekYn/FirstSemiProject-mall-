package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class ProductToplistAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		String category = req.getParameter("category");
		if(category.equals("")) {
			// 널값일때
			category = "suit";
		}else if( !category.equals("top") && !category.equals("bottom") && !category.equals("outer") && !category.equals("acc") && !category.equals("outer")) {
			// 잘못된 카테고리값일때
			category = "suit";
		}
		
		req.setAttribute("category", category);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/productlist.jsp");
		
	}

}
