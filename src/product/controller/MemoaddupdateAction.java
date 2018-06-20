package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class MemoaddupdateAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		String comments = req.getParameter("comments");
		String userid = req.getParameter("userid");
		String pcode = req.getParameter("pcode");
		String num = req.getParameter("num");
		String msg="";

		InterproductDAO pdao = new ProductDAO();

		int memo = pdao.getupdatememo(userid, pcode, comments, num);
		
		if(memo<2) {
			msg = (memo == 1)?"한줄평 저장완료!!":"한줄평은 한번만 작성됩니다.!!"; 
		}else {
			msg = (memo == 2)?"한줄평 삭제완료!!":"한줄평이 삭제되지 않았습니다.!!"; 
		}
	
		
		String loc = "prodView.do?pcode="+pcode;
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/msg.jsp");

	}

}
