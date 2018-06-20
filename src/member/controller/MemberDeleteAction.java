package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.UserinfoDAO;
import member.model.UserinfoVO;
import util.my.MyUtil;
import common.controller.AbstractController;

public class MemberDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
	throws Exception {
		
	String method = req.getMethod();
	
	if(!method.equals("POST")) {
		MyUtil.invalidPath(req, this);
		return;
	}
	
	
	if(!"post".equalsIgnoreCase(method)) {
		String msg = "비정상적인 경로로 들어왔습니다.";
	    String loc = "javascript:history.back();";
	    
	    req.setAttribute("msg", msg);
	    req.setAttribute("loc", loc);
	    
	    super.setRedirect(false);
		super.setViewPage("/WEB-INF/msg.jsp");
	    return;
	}
	/*
		
		if(!"post".equalsIgnoreCase(method)) {
			// POST 방식이 아니라면 회원삭제를 하지 못하도록 한다.
			
			String msg = "비정상적인 경로로 들어왔습니다.";
		    String loc = "javascript:history.back();";
		    
		    req.setAttribute("msg", msg);
		    req.setAttribute("loc", loc);
		    
		    super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		    return;
		}*/
				
		HttpSession ses = req.getSession();
		UserinfoVO loginuser = (UserinfoVO)ses.getAttribute("loginuser"); 
	
		
		/*	
		if(!"admin".equals(loginuser.getUserid()) ) {
			String msg = "관리자가 아닙니다.";
			String loc = "javascript:history.back();";
		    
		    req.setAttribute("msg", msg);
		    req.setAttribute("loc", loc);
		    
		    super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		    return;
		}*/
		String msg= "";
		String idx = req.getParameter("idx");
		String goBackURL = req.getParameter("goBackURL");
		
    	/*System.out.println("==> 확인용 goBackURL : " + goBackURL);*/
	// ==> 확인용 goBackURL : memberList.do?currentShowPageNo=19&sizePerPage=3&searchType=name&searchWord=%EA%B8%B8%EB%8F%99&period=-1
		
		UserinfoDAO userinfoDao = new UserinfoDAO();
		
		int n = userinfoDao.deleteMember(idx); 
		/*	
		
		if(n == 0) {
			MyUtil.alertMsg(req, this, "회원가입 실패!!", "main.do");
		}else {
			MyUtil.alertMsg(req, this, "회원가입 성공", "main.do");
		}
		*/
	    
	//System.out.println("확인용 : "+n);
	   msg = (n>0)?"회원탈퇴 성공!!":"회원탈퇴 실패!!";
	 // System.out.println("확인용 : "+msg);
	   String loc = "main.do";

	   HttpSession session = req.getSession();
		session.setAttribute("loginuser", null);
	   
	    req.setAttribute("msg", msg);
	    req.setAttribute("loc", loc);
		
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/msg.jsp");
		
	}

}
