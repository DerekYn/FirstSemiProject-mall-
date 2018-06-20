package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.InteruserinfoDAO;
import member.model.UserinfoDAO;
import member.model.UserinfoVO;
import util.my.MyUtil;

public class MemberAddAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();
		
		if(!method.equals("POST")) {
			MyUtil.invalidPath(req, this);
			return;
		}
		
		String userid = req.getParameter("userid");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String post = req.getParameter("post");
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2");
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");
		
		// 우편번호 나누기
		String post1 = post.substring(0, 3);
		String post2 = post.substring(4);

		InteruserinfoDAO dao = new UserinfoDAO();
		int n = dao.userAdd(userid, password, name, email, post1, post2, addr1, addr2, hp1, hp2, hp3);
		
		if(n == 0) {
			MyUtil.alertMsg(req, this, "회원가입 실패!!", "main.do");
		}else {
			MyUtil.alertMsg(req, this, "회원가입 성공", "main.do");
		}
		
		
		
	}

}
