package product.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;
import util.my.MyUtil;

public class ChartListAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

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
		
		InterproductDAO dao = new ProductDAO();
		List<HashMap<String, String>> maplist = dao.getLikeDislikeCnt();
		req.setAttribute("maplist", maplist);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/chartList.jsp");
		
	}

}
