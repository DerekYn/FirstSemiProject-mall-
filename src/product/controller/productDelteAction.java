package product.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import common.controller.AbstractController;
import product.model.ProductDAO;
import util.my.MyUtil;

public class productDelteAction extends AbstractController{


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String method = req.getMethod();
		
		if(!"post".equalsIgnoreCase(method)) {
			MyUtil.alertMsg(req, this, "잘못된 접근입니다.", "javascript:history.back();");
			return;
		}

		String pcode = req.getParameter("pcode");
		
		ProductDAO pdao = new ProductDAO();
		int result = pdao.deleteProducts(pcode);
		
		if(result > 0) {
			MyUtil.alertMsg(req, this, "삭제 되었습니다.", "productlists.do");
			return;
		}
		
		else {
			MyUtil.alertMsg(req, this, "삭제 실패하였습니다.", "productlists.do");
			return;
		}
		
	}

}
