package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import member.model.InteruserinfoDAO;
import member.model.UserinfoDAO;

public class IdCheckJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		
		String userid = req.getParameter("userid");
		InteruserinfoDAO dao = new UserinfoDAO();
		
		int result = dao.useridCheck(userid);
		System.out.println(result);
		JSONObject json = new JSONObject();
		
		json.put("result", result);
		String str_result = json.toString();
		
		req.setAttribute("str_result", str_result);

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/idCheckJSON.jsp");
		
	}

}
