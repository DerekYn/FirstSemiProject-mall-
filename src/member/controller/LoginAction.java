package member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoDAO;
import member.model.UserinfoVO;

public class LoginAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/loginOK.jsp");
		
		}

	}


