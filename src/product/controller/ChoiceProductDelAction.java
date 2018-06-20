package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.ProductDAO;

public class ChoiceProductDelAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String[] delChkboxArr = req.getParameterValues("fk_pcode");
		//  req.getParameterValues("name"); 는
		//	타입이 checkbox 인데 체크가 되어진 것만 받아온다. 
		System.out.println("delChkboxArr 길이 : "+ delChkboxArr.length);		
		
		StringBuilder sb = new StringBuilder();		// StringBuilder : 차곡차곡 쌓아준다. 웹은 단일스레드 // StringBuffer -- 멀티스레드
		 
		for(int i=0; i<delChkboxArr.length; i++) {
			sb.append(delChkboxArr[i]+",");		
		//	System.out.println("sb : " +sb);
		
		}// end of for-----------------
		
		
		String str = sb.toString();	// 쌓아둔 것을 끄집어내자. ","는 필요없다
				
		str = str.substring(0, str.length()-1);		// 맨뒤의 콤마를 지운다
				
		System.out.println(">>> 확인용 str : "+ str);		// 이것을 in() 안에 넣어주기 위함이다. update할 떄 하나하나 하기 힘드니까
		
		InterproductDAO pdao = new ProductDAO();	
		
		int n = pdao.deleteProduct(str);	// str을 보내야한다.
		
		System.out.println("확인용 n : "+n);
		System.out.println("확인용 길이" + delChkboxArr.length);
		
		String msg = (n == delChkboxArr.length)?"선택한 제품이 삭제되었습니다.":"선택한 제품이 삭제 실패 했습니다.";
		String loc ="cartList.do";
		
		req.setAttribute("msg", msg);
		req.setAttribute("loc", loc);	
		
		super.setRedirect(false);	// 안써도 false
		super.setViewPage("/WEB-INF/msg.jsp");	 // 여기로 보내주겠다.
		

	}

}
