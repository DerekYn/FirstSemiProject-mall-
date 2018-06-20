package product.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;
import util.my.MyUtil;

public class OrderListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
			
		UserinfoVO loginuser = super.getMemberLogin(req);
		// 로그인 유무 검사해주는 메소드 호출 
	//	String userid = "leess";
		
		if(loginuser != null) {	// loginuser
			// 로그인을 했으면 최근 1년간 주문내역 목록을 조회해주겠다.
			
			InterproductDAO pdao = new ProductDAO();
			
			// *** 페이징 처리하기 *** //
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
			
			String str_sizePerPage = req.getParameter("sizePerPage");
			
			if(str_sizePerPage == null)  // 초기화면
		    	str_sizePerPage = "5";
		    	    
		    int sizePerPage = 0;

		    try{
		    	sizePerPage = Integer.parseInt(str_sizePerPage);
		    			    	
		    } catch(NumberFormatException e){
		    	sizePerPage = 5;
		    }
		    
		    req.setAttribute("sizePerPage", sizePerPage);
			
			int blockSize = 2;
						
			List<HashMap<String, String>> orderList = pdao.getOrderList(loginuser.getUserid(), currentShowPageNo, sizePerPage); // loginuser.getUserid()
			
			// *** 페이지바 만들기 *** //
			int totalCountOrder = pdao.getTotalOrderCount(loginuser.getUserid()); // loginuser.getUserid()
			int totalPage = (int)Math.ceil( (double)totalCountOrder/sizePerPage );
			
			String pageBar = MyUtil.getPageBar("orderList.do", currentShowPageNo, sizePerPage, totalPage, blockSize);
			
			req.setAttribute("orderList", orderList);
			req.setAttribute("pageBar", pageBar);
			
		    super.setRedirect(false);
		    super.setViewPage("/WEB-INF/myshop/orderList.jsp"); 
		}
	}

}
