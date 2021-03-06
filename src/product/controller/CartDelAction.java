package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;
import util.my.MyUtil;

public class CartDelAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();
		
		if(!"post".equalsIgnoreCase(method)) {
			// POST 방식이 아니라면
			MyUtil.invalidPath(req, this);
			return;
		}
		else {
			// POST 방식이라면			
			UserinfoVO loginuser = super.getMemberLogin(req);
			// 세션에서 로그인 되어진 정보를 얻어온다.
			
			if(loginuser != null) {
				// 로그인이 되었더라면
				
				// 장바구니 삭제는 자신의 장바구니만 삭제가 되어야지 다른 사용자의 장바구니 목록이 삭제되면 안되도록 해야 한다. 
				String cartno = req.getParameter("cartno");
				
				InterproductDAO pdao = new ProductDAO();
				String userid = pdao.getUseridByCartno(cartno); 
				
				if(!loginuser.getUserid().equals(userid)) {
					// 로그인 되어진 사용자의 userid 와 
					// 장바구니목록에서 삭제하려는 장바구니제품의 소유주(userid)가 다른 경우이라면
					MyUtil.invalidPath(req, this);
					return;
				}
				else {
					// 로그인 되어진 사용자의 userid 와 
					// 장바구니목록에서 삭제하려는 장바구니제품의 소유주(userid)가 같은 경우이라면
					int n = pdao.deleteCartno(cartno);
					
					String goBackURL = req.getParameter("goBackURL");
					// 장바구니 삭제후 돌아갈 페이지를 받아옴.
					
					String msg = (n>0)?"제품목록에서 삭제되었습니다.":"제품목록에서 삭제 실패."; 
					String loc = "cartList.do";	// goBackURL
					
					MyUtil.alertMsg(req, this, msg, loc);
				}
				
			}// end of if----------------------
			
		}// end of if~else---------------------

	}

}
