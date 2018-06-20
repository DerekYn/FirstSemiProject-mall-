package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;

public class CoinPurchaseEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
	 	UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
	 	
	 			
	 	super.setRedirect(false);
	 	super.setViewPage("/WEB-INF/myshop/paymentGateway.jsp");
				
	}

}
