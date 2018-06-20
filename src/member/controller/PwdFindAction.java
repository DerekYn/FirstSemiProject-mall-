package member.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.UserinfoDAO;

public class PwdFindAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();
		System.out.println("===> 확인용 pwdFind.do 의 method : " + method);
		
		req.setAttribute("method", method);
		
		if("post".equalsIgnoreCase(method)) {
			// 비밀번호 찾기 모달창에서 찾기 버튼을 클릭했을 경우
			String userid = req.getParameter("userid");
			String email = req.getParameter("email");
			
			UserinfoDAO userinfoDao = new UserinfoDAO();
			
			int n = userinfoDao.isUserExists(userid, email); 
			
			if(n==1) {
				
				GoogleMail mail = new GoogleMail();
				
				Random rnd = new Random();
				
				String certificationCode = "";
				// certificationCode ==> "ewtyq0452029"
				
				char randchar = ' ';
				for(int i=0; i<5; i++) {
					// min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면
					// int rndnum = rnd.nextInt(max - min + 1) + min;  
					randchar = (char)(rnd.nextInt('z' - 'a' + 1) + 'a');
					certificationCode += randchar;
				}
				
				int randnum = 0;
				for(int i=0; i<7; i++) {
					randnum = (int)(rnd.nextInt(10-0+1)+0);
					certificationCode += randnum;
				}
				
				try {
					mail.sendmail(email, certificationCode);
					req.setAttribute("certificationCode", certificationCode);
					
				} catch(Exception e) {
					e.printStackTrace();
					
					
					
					req.setAttribute("sendFailmsg", "메일발송에 실패했습니다.");
					n = -1;
				}
				
			}
			
			req.setAttribute("n", n);  
			// n이 0이면 존재하지 않은 userid 또는 email 인 경우
			// n이 1이면 userid 와 email 존재하면서 메일발송이 성공한 경우
			// n이 -1이면 userid 와 email 존재하는데 메일발송이 실패한 경우
			
			req.setAttribute("userid", userid);
			req.setAttribute("email", email);
		}
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/modal/pwdFind.jsp");

	}

}

