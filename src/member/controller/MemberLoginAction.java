package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class MemberLoginAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/loginInfo.jsp");
		
	}

}
