package common.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import member.model.UserinfoVO;
import product.model.CategoryVO;
import product.model.ProductDAO;

public abstract class AbstractController implements Command {

	
	private boolean isRedirect = false;
	/*
	  	view단 페이지(.jsp 페이지)로 이동시
	  	redirect 방법(데이터 전달은 못하고 단순히 페이지 이동만 하는것)으로
	  	이동시키고자 한다라면  isRedirect 변수의 값을 true 로 해주면 된다.
	  	
	  	view단 페이지(.jsp 페이지)로 이동시
	  	forward(dispatcher) 방법(데이터 전달을 하면서 페이지 이동을 하는것)으로
	  	이동시키고자 한다라면 isRedirect 변수의 값을 false로 해주면 된다.
	  	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
	 */
	
	private String viewPage;
	// view단 페이지(.jsp 페이지)의 경로명으로 사용되어지는 변수이다.

	public boolean isRedirect() {
		return isRedirect;
		// 리턴타입이 boolean 이라면 get이 아니라 is로 나타난다.
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
	// 로그인 여부 검사 //
	public UserinfoVO getMemberLogin(HttpServletRequest req) {
		HttpSession session = req.getSession();
		UserinfoVO loginuser = null;
		loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
		if(loginuser == null) {
			// 로그인을 하지 않은 경우
			String msg = "로그인이 필요한 서비스입니다.";
			String loc = "javascript:history.back();";
			
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
						
			setRedirect(false);
			setViewPage("/WEB-INF/msg.jsp");
		}
		
		return loginuser;
	}
	
	// 비정상 경로 에러 //
	public void invalidPath(HttpServletRequest req) {
		String msg = "비정상적인 경로로 들어왔습니다.";
		String loc = "javascript:history.back();";
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		
		setRedirect(false);
		setViewPage("/WEB-INF/msg.jsp");
	}
	
	// 일반 에러 //
	public void alertMsg(HttpServletRequest req, String msg, String loc) {
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		
		setRedirect(false);
		setViewPage("/WEB-INF/msg.jsp");
	}
	
	public void getCategoryList(HttpServletRequest request) throws SQLException {
		
		ProductDAO pdao = new ProductDAO();
    	List<CategoryVO> categoryList = pdao.getCategoryList();
    	
    	request.setAttribute("categoryList", categoryList);
		
	}
	
	
	
}
