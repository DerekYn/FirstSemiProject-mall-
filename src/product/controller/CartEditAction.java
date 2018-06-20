package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class CartEditAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		String method = req.getMethod();		// POST 방식이여야 하니까
		
		if(!"post".equalsIgnoreCase(method)) {
			// POST 방식이 아니라면
			super.invalidPath(req);
			return;
		}
		else {
			
	  		// POST 방식이라면
			UserinfoVO loginuser = super.getMemberLogin(req);		// 로그인 여부 검사
	
			if(loginuser == null) {
				// POST 방식이지만 로그인을 안했다면
				return;		
			}
			else {
				// POST 방식이면서 로그인을 한 경우	
				  									
	
				InterproductDAO pdao = new ProductDAO();	
				
				String poqty = req.getParameter("poqty");
				String cartno = req.getParameter("cartno");
										
				int n = pdao.editCart(poqty,cartno);
						
				String msg = (n>0)?"제품의 수량이 변경되었습니다.":"제품의 수량이 변경 실패."; 
				String loc = "cartList.do";
				
				super.alertMsg(req, msg, loc);
			
				
			} // end of if ~ else ------------
						
			
		
		}
		
	
	}
}	

