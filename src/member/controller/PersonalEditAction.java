package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoDAO;
import member.model.UserinfoVO;

public class PersonalEditAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// 개인정보 수정HttpSession ses = req.getSession();
		HttpSession ses = req.getSession();
		UserinfoVO loginuser = (UserinfoVO)ses.getAttribute("loginuser"); 
		
		if(loginuser == null) {
			String msg = "비정상적인 경로로 들어왔습니다.";
		    String loc = "javascript:history.back();";
		    
		    req.setAttribute("msg", msg);
		    req.setAttribute("loc", loc);
		    
		    super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		    
			return;
		}
		
		HttpSession session = req.getSession();
	 	 loginuser = (UserinfoVO)session.getAttribute("loginuser");
	 	
	 
	 	if(loginuser == null) {
	 		// 잘못된경로
	 		return;
	 	}
	 	
	 	int idx = loginuser.getIdx();
 		UserinfoDAO userinfoDao = new UserinfoDAO();
 		UserinfoVO userinfovo = userinfoDao.getMemberOneByIdx(String.valueOf(idx));
 		
 		req.setAttribute("userinfovo", userinfovo);
 		req.setAttribute("idx",idx);
 		
 		super.setRedirect(false);
 		super.setViewPage("/WEB-INF/member/memberEdit.jsp");
 	
	}

}
