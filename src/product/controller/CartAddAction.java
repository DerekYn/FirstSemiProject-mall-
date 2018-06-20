package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class CartAddAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();	// post방식이니까 get 방식이면 못하게
				
		if(!"post".equalsIgnoreCase(method)) {				
			super.invalidPath(req);
			return;							
		}
		else {
		// POST 방식이라면	
			UserinfoVO loginuser = super.getMemberLogin(req);		// 늘 쓰는 것은 부모클래스에 빼둔다.	
			// 로그인 유무 검사하기 
			// ==> 로그인을 안한 경우라면 alert 발생되어지고
			// 	   loginuser 에는 null 값이 리턴되어 진다.
			
			if(loginuser == null) {
				// 로그인을 하지 않은 상태에서 장바구니 담기를 시도한 경우		
				String currentURL = req.getParameter("currentURL");	// form에 있는 hidden 타입의 name	. currentURL: 다시돌아갈페이지. 세션에 올려버리자	
				
				HttpSession session = req.getSession();
				session.setAttribute("returnPage", currentURL);		// 돌아갈 페이지는 세션에 담아두고 
				
				return;												// 끝낸다.
			}
			// *** 정상적으로 로그인을 해서 장바구니에 특정제품을 담는 경우 ***
			String fk_pcode = req.getParameter("fk_pcode"); // input 태그의 name
			String poqty = req.getParameter("oqty"); // input 태그의 name
			
			InterproductDAO pdao = new ProductDAO();		// pnum과 oqty를 얻어오고 DB에 보내줘야 하니 DAO 필요
			int n = pdao.addCart(loginuser.getUserid(), fk_pcode, poqty);	// jsp_cart에 담으려면 userid, pnum,oqty가 필요하다. loginuser를 사용하면 된다. 누가 어떤상품을 몇개  // insert는 int타입
			
			if(n==1) {
				// 장바구니에 올바르게 들어갔을 때
				super.alertMsg(req, "장바구니 담기 성공 !", "cartList.do");		// cartList.do : 장바구니 내역보기
			}
			else {
				super.alertMsg(req, "장바구니 담기 실패 !", "javascript:history.back();");				
			}
			
			
			
		}// end of if~ else ---------

	}

}
