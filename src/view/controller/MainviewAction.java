package view.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.ProductDAO;

public class MainviewAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/index.jsp");
		
	}
	

}
