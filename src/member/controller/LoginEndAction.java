package member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoDAO;
import member.model.UserinfoVO;


public class LoginEndAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();
		
		if(!"post".equalsIgnoreCase(method)) {
		// POST 방식으로 넘어온 것이 아니라면
	
		
		String msg = "비정상적인 경로로 들어왔습니다."; // 주소창에 ?를 입력하면 뜹니다.
		String loc = "javascript:history.back();";
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/msg.jsp");
		
		return;
		}
		else {
			// POST 방식으로 넘어온 것이라면
			String userid = req.getParameter("userid");
			String password = req.getParameter("password");
			System.out.println("확인용 userid : "+userid);
			
			System.out.println("확인용 password : "+password);
			String goBackURL = req.getParameter("goBackURL");
			String saveid = req.getParameter("saveid");
			// checkbox 에 value 값이 없을 경우
			// 체크가 된 상태로 넘어오면 "on" 으로 받아오고
			// 체크가 안된 상태로 넘어오면 null 로 받아온다.!!!!
		//	System.out.println("===> 확인용 saveid : " + saveid); 
			
			// 사용자로 부터 입력받은 userid 와 pwd 값이
			// jsp_member 테이블에 존재하는지 여부를 알아야 한다.
			UserinfoDAO userinfodao = new UserinfoDAO();
			
			UserinfoVO userinfovo = userinfodao.loginOKmemberInfo(userid, password);
			
			if(userinfovo == null) {
				// 로그인 실패한 경우!!
				String msg = "아이디 또는 비밀번호를 다시 확인하세요.";
				String loc = "javascript:history.back();";
				
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				return;		
			}
			else {
				// 로그인 성공한 경우!!
				
				String msg = "로그인에 성공하셨습니다.";
				
				// 로그인 되어진 사용자의 정보(membervo)를 세션(session)에 저장하도록 한다.
				// *** 세션(session) *** 
				// 세션(session)은 WAS(톰캣서버)의 메모리(RAM)의 일부분을 사용하는 저장공간이다.
				// 세션(session)에 저장된 데이터는 모든 파일(*.do, *.jsp)에서
				// 사용할 수 있도록 접근이 가능하다.
				
				HttpSession session = req.getSession();
				session.setAttribute("loginuser", userinfovo);
				
				// *** 사용자가 로그인시 아이디저장 체크박스에 체크를 했을 경우와 체크를 해제 했을 경우
				//     쿠키저장 또는 쿠키삭제를 하도록 한다.
				// *** 쿠키(cookie) ***
				// WAS 컴퓨터가 아닌 사용자 컴퓨터의 디스크 공간을 저장공간으로 사용하는 기법을 말하면 
				// 쿠키(cookie)에 저장되는 정보는 보안성이 떨어지는 데이터 이어야 한다.
				
				Cookie cookie = new Cookie("saveid", userinfovo.getUserid());
				// 로그인하는 사용자의 아이디값을 saveid 라는 이름의 키값으로 쿠키객체를 생성한다.
				
				if(saveid != null) {
					// 40번 라인에서 받은 것이 null 이 아닌 "on" 이라면
					cookie.setMaxAge(7*24*60*60); // 쿠키의 생존기간을 7일(단위 초)로 설정한다. 쿠키저장          
				}
				else {
					// 40번 라인에서 받은 것이 null 이라면
					cookie.setMaxAge(0); // 쿠키의 생존기간을 0초로 한다. 즉, 이것인 쿠키 삭제이다.
				}
				
				cookie.setPath("/");
				/*
				   쿠키가 사용되어질 디렉토리 정보 설정.
				  cookie.setPath("사용되어질 경로"); 
				   만일 "사용되어질 경로" 가 "/" 일 경우
				   해당 도메인(예 iei.or.kr)의 모든 페이지에서 사용가능하다. 
				 */
				
				res.addCookie(cookie);
				/*
				     사용자 컴퓨터의 웹브라우저로 쿠키를 전송시킨다.
				   7일간 사용가능한 쿠키를 전송하든지
				     아니면 0초 짜리 사용가능한 쿠키(쿠키삭제)를     
				     사용자 컴퓨터의 웹브라우저로 전송시킨다.    
				*/
				
				super.setRedirect(true); // redirect 방식으로 페이지 이동만 한다. 
				
				
				String returnPage = (String)session.getAttribute("returnPage");
				
				super.setViewPage("main.do");
				/*  super.setViewPage("memoVOList.do?currentShowPageNo=2&sizePerPage=3"); */
				
				if(returnPage == null) {
					
					// 그냥 로그인을 한것
					//	super.setViewPage(goBackURL);
					super.setViewPage("main.do");
				}
					/*	else {
						// 로그인을 하지 않은 상태에서 
						// 장바구니담기 또는 장바구니담기를 시도한 경우
						//	super.setViewPage("malldisplay.do");
							super.setViewPage(returnPage);
					
						session.removeAttribute("returnPage"); //꼭 써야한다?
							}
					 */
				
						}// end of if~else-----------------
			
			
	
		}
	}
	
	
}
