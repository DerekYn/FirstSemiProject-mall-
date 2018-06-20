package product.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.ProductDAO;
import product.model.ProductImagefileVO;
import product.model.ProductVO;

public class DetailAction extends AbstractController  {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pcode = req.getParameter("pcode");
		
		ProductDAO dao = new ProductDAO();
		
		ProductVO pvo = null;
		List<ProductImagefileVO> imgFileList = null;  // 복수개 이미지 파일
		
		try {
		     pvo = dao.getProductOneByPnum(pcode);
		     
		     imgFileList = dao.getProductImagefileByPnum(pcode); 
		     
		} catch(SQLException e) {
			String msg = "비정상적인 경로로 들어왔습니다.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
		if(pvo == null) {
	
			super.invalidPath(req);
			
			return;
		}
		else {
			req.setAttribute("pvo", pvo);
			
			req.setAttribute("imgFileList", imgFileList); 
			
			// **** 로그인을 하지 않은 상태에서 
			// "장바구니담기" 또는 "바로주문하기"를 하면 로그인부터 하라고
			// 말이 나온다. 그러면 사용자가 로그인을 한 이후에는 
			// 특정 상품보기 페이지로 다시 돌아가야 한다.
			// 그래서 현재 특정 상품보기 페이지의 경로를 알아서 뷰단으로 넘겨주어야 한다.
			String currentURL = req.getRequestURL().toString();                 
		   // http://localhost:9090/MyWeb/member/memberList.jsp
			   
		   String queryString = req.getQueryString();
		   	   
		   currentURL += "?" + queryString;
		   
		   String ctxName = req.getContextPath(); 
		  
		   int index = currentURL.indexOf(ctxName); 
		   
		   int beginIndex = index + ctxName.length(); 
		   
		   currentURL = currentURL.substring(beginIndex+1);		   
		   
		   req.setAttribute("currentURL", currentURL);
			
		   int totalMemoCount = dao.totalMemoCount(pcode);
			
		   req.setAttribute("totalMemoCount", totalMemoCount);
			
			
			this.setRedirect(false);
			this.setViewPage("/WEB-INF/product/prodDetail.jsp"); 
		}

		
	}

}
