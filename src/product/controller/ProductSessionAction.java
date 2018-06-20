package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import util.my.MyUtil;

public class ProductSessionAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		
		String method = req.getMethod();
		
		if(!method.equalsIgnoreCase("post")) {
			super.alertMsg(req, "잘못된 접근방법입니다.", "javascript:history.back();");
			return;
		}
		UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
		
		if(loginuser == null) {
			MyUtil.alertMsg(req, this, "로그인후 이용해주세요", "javascript:history.back();");
			return;
		}
		
		String[] pcodeArr = req.getParameterValues("fk_pcode");			// 제품번호 	// 여러개 체크된것을 받아와야 되니까. forEach라 파라미터값은 똑같으니까 배열로 받아오자(getParameterValues). 
		String[] oqtyArr = req.getParameterValues("poqty");				// 주문량 받아오자
		String[] salepriceArr = req.getParameterValues("saleprice");	// 주문할 그때 그당시의 단가를 받아오자.
		String[] cartnoArr =req.getParameterValues("cartno");
		
		String str_sumtotalprice = req.getParameter("sumtotalprice");
		String str_sumtotalpoint = req.getParameter("sumtotalpoint");
		
		
		
		session.setAttribute("pcodeArr", pcodeArr);
		session.setAttribute("oqtyArr", oqtyArr);
		session.setAttribute("salepriceArr", salepriceArr);
		session.setAttribute("cartnoArr", cartnoArr);
		session.setAttribute("str_sumtotalprice", str_sumtotalprice);
		session.setAttribute("str_sumtotalpoint", str_sumtotalpoint);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/myshop/paymentGateway.jsp");
		
	}

}
