package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

public class LogoutAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession session = req.getSession();
		
		session.removeAttribute("loginuser");
		//세션에 저장된 키 "loginuser" 을 삭제 하는 것이다.
		
		super.setRedirect(true);  // 로그아웃하는순간 
		super.setViewPage("main.do"); //시작페이지로 이동.
		
		
		
		
		
	}

	
	
}
