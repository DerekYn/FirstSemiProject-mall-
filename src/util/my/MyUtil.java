package util.my;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import common.controller.AbstractController;

public class MyUtil {
	
	public static void invalidPath(HttpServletRequest request, AbstractController absctrl) {
		
		String msg = "비정상적인 경로로 들어왔습니다.";
		String loc = "javascript:history.back();";
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		absctrl.setRedirect(false);
		absctrl.setViewPage("/WEB-INF/msg.jsp");
		
	}// end of invalidPath(HttpServletRequest req)--------------------------
	
	public static void alertMsg(HttpServletRequest request, AbstractController absctrl, String msg, String loc) {
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		absctrl.setRedirect(false);
		absctrl.setViewPage("/WEB-INF/msg.jsp");
		
	}// end of invalidPath(HttpServletRequest req)--------------------------
	
	// *** 숫자를 입력받아서 세자리마다 콤마(,)를 찍어서 리턴시켜주는 메소드 *** // 
	public static String getMoney(long number) {
		
		// 숫자로 되어진 데이터를 세자리마다 콤마(,)를 찍어주는 객체 생성
		DecimalFormat df = new DecimalFormat("#,###");
		
		String result = df.format(number);
		
		return result;
		
	}// end of static String getMoney(long number)--------
	
	// ===== *** 페이지바 만들기 *** ======= 검색이없는 페이지바 //
	public static String getPageBar(String url
			                      , int currentShowPageNo
			                      , int sizePerPage
			                      , int totalPage
			                      , int blockSize) {
		
		String pageBar = "";
	    
		int pageNo = 1;
		int loop = 1;
		
		pageNo = ((currentShowPageNo - 1) / blockSize) * blockSize + 1;
		// 공식임.
		
		//     currentShowPageNo      pageNo
		//    -------------------------------
		//           1                  1
		//           2                  1
		//          ..                 ..
		//          10                  1
		//          
		//          11                 11
		//          12                 11
		//          ..                 ..
		//          20                 11
		//          
		//          21                 21                 
		//          22                 21 
		//          ..                 ..
		//          30                 21 
		
		if(pageNo == 1) {
			pageBar += "";
		}
		else {
			pageBar += "&nbsp;<a href=\""+url+"?currentShowPageNo="+(pageNo-1)+"&sizePerPage="+sizePerPage+"\">[이전]</a>";
		}
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == currentShowPageNo) {
				pageBar += "&nbsp;<span style=\"color: red; font-size: 13pt; font-weight: bold; text-decoration: underline;\">"+pageNo+"</span>&nbsp;";
			}
			else {
				pageBar += "&nbsp;<a href=\""+url+"?currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"\">"+pageNo+"</a>&nbsp;";
			}
			
			pageNo++;
			loop++;
		}// end of while-------------------------
		
		if(pageNo > totalPage) {
			pageBar += "";
		}
		else {
			pageBar += "&nbsp;<a href=\""+url+"?currentShowPageNo="+pageNo+"&sizePerPage="+sizePerPage+"\">[다음]</a>";
		}

		return pageBar;
		
	}// end of getPageBar(String url, int currentShowPageNo, int sizePerPage, int totalPage, int blockSize)-------------------	
	
	
	public static String getCurrentURL(HttpServletRequest request) {
		
		   String currentURL = request.getRequestURL().toString();                 
		   // http://localhost:9090/MyWeb/member/memberList.jsp
			
		   String queryString = request.getQueryString();
		   // currentShowPageNo=9&sizePerPage=10
				   
		   currentURL += "?" + queryString;
		   // http://localhost:9090/MyWeb/member/memberList.jsp?currentShowPageNo=9&sizePerPage=10 
		   
		   String ctxName = request.getContextPath(); 
		   //   /MyWeb 
		   
		   int index = currentURL.indexOf(ctxName); 
		   //   21 
		   
		   int beginIndex = index + ctxName.length(); 
		   //  21 + 6 ==> 27 
		   
		   currentURL = currentURL.substring(beginIndex+1);		   
		  // member/memberList.jsp?currentShowPageNo=9&sizePerPage=10	
			
		   return currentURL;
		}// end of String getCurrentURL()---------------

}
