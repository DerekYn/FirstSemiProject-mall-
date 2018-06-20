package product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;
import util.my.MyUtil;
import product.model.CartVO;

public class CartListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		UserinfoVO loginuser = super.getMemberLogin(req);
		
					
		if(loginuser != null) {
		
			ProductDAO pdao = new ProductDAO();	
			
			
			String str_currentShowPageNo = req.getParameter("currentShowPageNo");
			int currentShowPageNo = 0;
			
			if(str_currentShowPageNo == null) {
				currentShowPageNo = 1;
			}
			else {
				try {
					currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
					
					if(currentShowPageNo < 1) {
					   currentShowPageNo = 1;
					}
					
				} catch(NumberFormatException e) {
					currentShowPageNo = 1;
				}
			}// end of if~else------------------------------	
			
			int sizePerPage = 3;
			int blockSize = 2;
		//	String userid = "leess";
			
			List<CartVO> cartList = pdao.getCartList(loginuser.getUserid()  , currentShowPageNo, sizePerPage);	// loginuser.getUserid() 
		//	int totalcartListCount = pdao.totalcartListCount();
			
			
			int totalCountCart = pdao.getTotalCartCount(loginuser.getUserid() ); //loginuser.getUserid()
			int totalPage = (int)Math.ceil( (double)totalCountCart/sizePerPage );
			
			String pageBar = MyUtil.getPageBar("cartList.do", currentShowPageNo, sizePerPage, totalPage, blockSize);
					
			req.setAttribute("cartList", cartList);
			req.setAttribute("pageBar", pageBar);
			
			
			String currentURL = MyUtil.getCurrentURL(req);
			req.setAttribute("currentURL", currentURL);
			
			req.setAttribute("totalCountCart", totalCountCart);
			super.setRedirect(false);
	
			super.setViewPage("/WEB-INF/myshop/cartList.jsp");
			
			}
		
		}
	
}
