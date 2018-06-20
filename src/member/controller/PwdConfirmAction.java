package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.UserinfoDAO;

public class PwdConfirmAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method  = req.getMethod();
		req.setAttribute("method", method);

		String userid = req.getParameter("userid");
		req.setAttribute("userid", userid);
		
		if("POST".equalsIgnoreCase(method)) {
			String password = req.getParameter("password");
			
			UserinfoDAO userinfoDao = new UserinfoDAO();
			
			
			int n = 0;
	    	if(userid != null && password != null) {
	    		n = userinfoDao.updatePwdUser(userid, password);
	    	}
	    	
	    	req.setAttribute("n", n);
	    }
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/modal/pwdConfirm.jsp");

	}

}
