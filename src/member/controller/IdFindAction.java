package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.UserinfoDAO;

public class IdFindAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
			
			String method = req.getMethod();
			System.out.println("===> 확인용 idFind.do 의 method : " + method);
			
			req.setAttribute("method", method);
			
			if("post".equalsIgnoreCase(method)) {
				// 아이디 찾기 모달창에서 찾기 버튼을 클릭했을 경우
				String name = req.getParameter("name");
				String mobile = req.getParameter("mobile");
				
				System.out.println(name);
				System.out.println(mobile);
				
				
				UserinfoDAO userinfoDao = new UserinfoDAO();
				
				String userid = userinfoDao.getUserid(name, mobile); 
				
				if(userid != null) {
					req.setAttribute("userid", userid);
				}
				else {
					req.setAttribute("userid", "검색하신 조건의 아이디가 없습니다.");
				}
			
				req.setAttribute("name", name);
				req.setAttribute("mobile", mobile);
			}
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/modal/idFind.jsp");


	}

}
