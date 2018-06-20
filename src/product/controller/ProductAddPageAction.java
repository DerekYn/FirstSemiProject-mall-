package product.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.UserinfoVO;
import product.model.ProductDAO;
import util.my.MyUtil;

public class ProductAddPageAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
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
		
		ProductDAO pdao = new ProductDAO();
		List<HashMap<String, String>> plist = pdao.getAllProduct();
		
		req.setAttribute("plist", plist);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/product/productadd.jsp");
		
	}

}
