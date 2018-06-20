package member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.InteruserinfoDAO;
import member.model.UserinfoDAO;
import member.model.UserinfoVO;
import util.my.MyUtil;

public class MemberListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		InteruserinfoDAO dao = new UserinfoDAO();
		
		
		
		// 관리자만 볼수있게 if문 추가하기
		HttpSession session = req.getSession();
		UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
		
		if(loginuser == null) {
			MyUtil.alertMsg(req, this, "관리자 로그인후 이용해주세요", "javascript:history.back();");
			return;
		}
		else if(loginuser != null && !"admin".equals(loginuser.getUserid())) {
			MyUtil.alertMsg(req, this, "관리자 로그인후 이용해주세요", "javascript:history.back();");
			return;
		}
		
		List<UserinfoVO> userList = dao.getMemberList(); 
		
		req.setAttribute("userList", userList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/userList.jsp");
	}

}
