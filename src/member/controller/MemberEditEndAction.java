package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.UserinfoDAO;
import member.model.UserinfoVO;
import common.controller.AbstractController;

public class MemberEditEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		
		String method = req.getMethod();
	
		
		
		if(!"post".equalsIgnoreCase(method)) {
			String msg = "비정상적인 경로로 들어왔습니다.";
		    String loc = "javascript:history.back();";
		    
		    req.setAttribute("msg", msg);
		    req.setAttribute("loc", loc);
		    
		    super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		    return;
		}
		
				
		String idx = req.getParameter("idx");
		String goBackURL = req.getParameter("goBackURL");
		
		String name = req.getParameter("name");
		String userid = req.getParameter("userid");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");
		String post = req.getParameter("post"); // 115-22
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2"); 
		
		
		System.out.println("확인용 : "+post);
		
		String post1 = post.substring(0,3);//115
		System.out.println("확인용1 : "+post1);
		
		String post2 = post.substring(4);//115-22
		System.out.println("확인용1 : "+post2);
		
		
		System.out.println("확인용2 : "+post1+ " :    "+post2);
		
		UserinfoVO userinfovo = new UserinfoVO();
		
		userinfovo.setIdx(Integer.parseInt(idx));
		userinfovo.setName(name);
		userinfovo.setUserid(userid);
		userinfovo.setPassword(password);
		userinfovo.setEmail(email);
		userinfovo.setHp1(hp1);
		userinfovo.setHp2(hp2);
		userinfovo.setHp3(hp3);
		userinfovo.setPost1(Integer.parseInt(post1));
		userinfovo.setPost2(Integer.parseInt(post2));
		userinfovo.setAddr1(addr1);
		userinfovo.setAddr2(addr2);
		
		UserinfoDAO userinfoDao = new UserinfoDAO();
		
		System.out.println("2");
		int n = userinfoDao.updateMember(userinfovo); 
		System.out.println("3");
		// 로그인한 회원이 관리자가 아닌 일반회원일 경우 나의정보를 클릭해서 자신의 회원정보를 수정한 이후에
		// 로그인 되어진 이름이 바로 변경되어지도록
		// 세션에 저장된 loginuser 의 정보값을 수정한 membervo 로 변경시킨다.
		HttpSession ses = req.getSession();
		UserinfoVO loginuser = (UserinfoVO)ses.getAttribute("loginuser");
		String loginuserid = loginuser.getUserid();
		
		if(!"admin".equals(loginuserid) && n>0 ) { // 로그인 userid가 관리자가아니고 
			ses.setAttribute("loginuser", userinfovo);
		}
		
		String msg = (n>0)?"회원정보 수정 성공!!":"회원정보 수정 실패!!";
		String loc = "main.do";
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
				
		this.setRedirect(false);
		this.setViewPage("/WEB-INF/msg.jsp");
		
	}

}
