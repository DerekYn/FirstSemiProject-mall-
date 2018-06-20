package product.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.controller.GoogleMail;
import member.model.InteruserinfoDAO;
import member.model.UserinfoDAO;
import member.model.UserinfoVO;
import product.model.InterproductDAO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class OrderaddAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// 로그인 유무 검사 먼저
		HttpSession session = req.getSession();	// 세션을 하나만든다.
		UserinfoVO loginuser = (UserinfoVO)session.getAttribute("loginuser");
		
	//	String userid = "leess";
		if(loginuser == null) {		// loginuser == null
			// 로그인을 하지 않은 상태에서 바로주문하기를 한 경우라면
			String returnPage = req.getParameter("currentURL");		// 히든타입으로 돌아갈페이지가 있다.currentURL // loginEndAction 참조
			req.setAttribute("returnPage", returnPage); 	// 키값 returnPage 만 치면 currentURL을 받아온다.	// session.setAttribute("returnPage", returnPage);
									
			super.alertMsg(req, "주문하기를 하려면 먼저 로그인 하세요.", "javascript:history.back();");				
			
			return;	// 종료							
		}				
		
		Object pcodeObj = session.getAttribute("pcodeArr");
		
		String[] pcodeArr = (String[]) session.getAttribute("pcodeArr");		// 제품번호 	// 여러개 체크된것을 받아와야 되니까. forEach라 파라미터값은 똑같으니까 배열로 받아오자(getParameterValues). 
		String[] oqtyArr = (String[]) session.getAttribute("oqtyArr");				// 주문량 받아오자
		String[] salepriceArr = (String[]) session.getAttribute("salepriceArr");	// 주문할 그때 그당시의 단가를 받아오자.
		String[] cartnoArr = (String[]) session.getAttribute("cartnoArr");
		
		
		if(cartnoArr == null) {
			super.alertMsg(req, "잘못된 접근방법입니다.", "javascript:history.back();");				
			
			return;	// 종료
		}
			
		
		String str_sumtotalprice = (String) session.getAttribute("str_sumtotalprice");	// 주문총액	// 합이니까 하나. (getParameter). 추후에 int로 바꿔야되니까 str_을 붙여주자
		String str_sumtotalpoint = (String) session.getAttribute("str_sumtotalpoint");	// 주문총포인트
		
				
	//	int usercoin = loginuser.getCoin();		// 코인이 부족하면 안되니까 코인을 비교해보자. 코인은 세션에 있다.
		// 만약 내 코인이 10000 이고 		
		int sumtotalprice = Integer.parseInt(str_sumtotalprice);
		// 내가 살 물건이 13000원이라면 3000원이 부족.
		
		int sumtotalpoint = Integer.parseInt(str_sumtotalpoint);
		// 총포인트
		
	//	if(usercoin < sumtotalprice) {	// 유저가 갖고있는 코인이 주문총액보다 작은 경우
	//		int minusmoney = sumtotalprice - usercoin;	// 부족한 금액
			
	//		DecimalFormat df = new DecimalFormat("#,###");	// 숫자로 되어진 데이터를 3자리마다 콤마(,)를 찍어주는 객체를 생성하겠다.
	//		String money = df.format(minusmoney);			
			
	//		super.alertMsg(req, "코인액이 "+ money +"원 부족하여 주문이 불가합니다.", "javascript:histroy.back();");
	//		return;	// 종료
	//	}
		
		// *** 이 아래의 내용은 코인액이 주문이 가능한 만큼 있으므로, 정상적으로 주문을 받도록 처리하는 것이다. *** //		
		
		ProductDAO pdao = new ProductDAO();
			
		
		// == 주문량의 갯수가 제품의 잔고량(pqty)보다 크면 주문을 받지 않도록 한다. == //
		List<ProductVO> lackProductList = new ArrayList<ProductVO>();	// 부족한 제품이 뭐냐
		
		for(int i=0; i<oqtyArr.length; i++) {
			
			ProductVO pvo = pdao.getProductOneByPcode(pcodeArr[i]);	// 이 메소드 안에 pqty도 포함되어 있다.
			int pqty = pvo.getPqty();	// 해당 제품의 잔고량을 얻어왔다.
						
			if(pqty < Integer.parseInt(oqtyArr[i])) {	
				// 특정제품의 잔고량이 주문량보다 적더라면 
				lackProductList.add(pvo);	// 부족한 것들을 모아서 List에 담아 정보를 뿌려줄거다.				
			}			
		}// end of for----------------
		
		if(lackProductList.size() > 0) {	
			// 잔고량을 초과한 주문제품이 있는 경우라면
			
			StringBuffer sbPname = new StringBuffer();
			StringBuffer sbPqty = new StringBuffer();
						
		/*
		<문자결합시>
			String namestr ="";	// 객체생성
			namestr += "이순신,"; // 객체생성
			namestr += "안중근";  // 객체생성
			//==> 객체 3개 생성. 매번 객체생성하는거다.
			System.out.println(namestr);
			// 이순신,안중근
			: 메모리 낭비가 심하다. 문자열 결합이 많을때는 StringBuffer,StringBuilder를 쓰면된다.
			    문자열 결합이 얼마 되지 않으면 위의 방법을 쓴다.
			
			StringBuffer namesb = new StringBuffer();	// StringBuilder와는 단일스레드 멀티스레드 차이. 빌더 써도 된다.
			// ==> 객체 1개만 생성
			namesb.append("이순신,");	// append 로 넣어주면된다.
			namesb.append("안중근");
			
			System.out.println(namesb.toString());
			// 이순신,안중근			
		*/
			for(int i=0; i<lackProductList.size(); i++) {
				sbPname.append(lackProductList.get(i).getPname());	// 리스트에서 끄집어내는거 get. i => ProductVO
				sbPqty.append(lackProductList.get(i).getPqty());	
					
				if(i < lackProductList.size()-1) {	// i가 매번 ++인데 꼴찌보다 앞에꺼. 그럼 맨뒤에꺼는 ,가 붙지않는다.
					sbPname.append(", "); 			// 콤마를 붙이자.
					sbPqty.append(", "); 			// 콤마를 붙이자.
				}
			}// end of for----------------
			
			String message = "잔고량이 부족합니다. 제품명 "+sbPname.toString()+"의 잔고량은 각각 "+sbPqty.toString()+"개 입니다.";
			String location = "javascript:history.back();";
			super.alertMsg(req, message, location);
			
			return;		// 종료
		}// end of if---------------		
		
		// ==== 이 아래 부터 주문량의 갯수가 제품의 잔고량보다 같거나 적으므로 주문을 받도록 한다.(모든것이 정상일 때) ==== //
		/*
			==== *** 트랜잭션 Transaction 처리하기 *** ==== (모든게 성공이면 commit; 하나라도 실패하면 rollback;)
			1.	주문개요 테이블(jsp_order)에 입력(insert)
			2.	주문상세 테이블(jsp_order_detail)에 입력(insert)
			3.	구매하는 사용자의 coin 컬럼의 값을 구매한 가격만큼 감하고,
				point 컬럼의 값은 구매한 포인트만큼 증가하며(update),
			4.	주문한 제품의 잔고량은 주문량만큼 감해야 하고(update),
			5.	장바구니에서 주문을 한 경우라면 장바구니 비우기(status 컬럼을 0으로 변경하는 update) - 실제로는 delete를 사용한다.
			
			를 해주는 DAO에서 만든 메소드를 호출하기
		*/
		
		String odrcode = getOdrcode();	// 메소드를 하나 만들어서 DB에서 불러와야한다.
		// 주문코드(명세서번호) 가져오기  // 주문코드형식 : 이니셜+날짜+-+sequence => s20180430-1 ...
	
		int n = pdao.add_Order_OrderDetail(odrcode				// jsp_order 주문코드(명세서번호)
										, loginuser.getUserid()	// 사용자 아이디 loginuser.getUserid()
										, sumtotalprice			// 주문총액
										, sumtotalpoint			// 주문총포인트.	// odrdate 는 디폴트값이 있으니 안해도 된다.
										, pcodeArr				// jsp_order_detail 제품번호 배열
										, oqtyArr				// 주문량 배열
										, salepriceArr			// 주문할  당시의 해당제품의 판매단가 배열										
										, cartnoArr				// 장바구니번호 배열(장바구니 비우기 해야하므로)
										);
				
	/*
		== 위에서 리턴 되어지는 n의 값은 0 또는 1 인데 1값이 리턴되어지면
		   jsp_member 테이블에서 해당 userid의 행에는 코인액이 주문총액만큼 감해졌고,
		      포인트는 주문총포인트 만큼 더해져서 변경이 되어진 상태다.(DB는)
		      그러므로 세션에 저장된 기존의 (변경전) loginuser 의 값을 jsp_member 테이블에서 
		      갱신된 (변경되어진) user로 새로이 갱신하여 웹페이지상에 감소된 코인액, 증가된 포인트를 보여주도록 한다.
	*/
	//	System.out.println("odrcode 확인 : "+ odrcode);
	//	System.out.println("n 확인 : "+ n);
		
	//	String idx = "1";
		if(n==1) {
			InteruserinfoDAO userdao = new UserinfoDAO();		
			
		//	userid = userdao.getMemberOneByIdx(idx).getUserid();	// 위에 있는 세션(로그인한유저)의 Idx, String.valueOf(int->string)으로 바뀐다.	// 파라미터 String.valueOf(userid.getIdx())
		
		//	req.setAttribute("userid", userid);
		//	session.setAttribute("loginuser", loginuser); 	// 세션 값을 바꿔줘야 한다. 위에 session 이 있다. 변경되어진 loginuser로 넣어준다.
			
			loginuser = userdao.getMemberOneByIdx(String.valueOf(loginuser.getIdx()));		// 위에 있는 세션(로그인한유저)의 Idx, String.valueOf(int->string)으로 바뀐다.
			
			session.setAttribute("loginuser", loginuser); 	// 세션 값을 바꿔줘야 한다. 위에 session 이 있다. 변경되어진 loginuser로 넣어준다.
			
			
			// **** 주문이 완료되었다라는 email을 보내기 시작 **** // 
				GoogleMail mail = new GoogleMail();
				
				int length = pcodeArr.length; // 주문한 제품의 갯수
				
				StringBuilder sb = new StringBuilder();	 // 멀티스레드 지원 (웹은 단일스레드. : stringBuffer)
				
				for(int i=0; i<length; i++) {
					sb.append("\'"+pcodeArr[i]+"\',");	// \' => '		// 이렇게 계속 더해라 라는 뜻
					// jsp_product 테이블에서 select시
					// where 절에 in() 속에 들어가므로 홑따옴표(')가 필요한 경우 
					// 위와 같이 넣어줘야 한다.					
				}// end of for ---------------
					String pcodes = sb.toString().trim();
					
					pcodes = pcodes.substring(0, pcodes.length()-1);		// 맨 뒤에 있는 콤마(,)를 제거하기 위함.
			//		System.out.println("확인용 pnums : "+ pnums);
			// 		확인용 pnums : '1','2','4'
					
					List<ProductVO>JumunfinishProductList = pdao.getJumunfinishProductList(pcodes);		
					// 주문완료한 제품번호들에 해당되는 제품목록을 얻어오는 것.
				
					sb.setLength(0); // StringBuilder sb 초기화 하기
					
					// 아래부터 메일로 간다.
					sb.append("주문코드번호 : <span style='color: blue; font-weight: bold;'>"+ odrcode+"</span><br/><br/>");
					sb.append("<주문상품><br/>");
																	
					for(int i=0; i<JumunfinishProductList.size(); i++) {	// 주문량은 위에서 받아온 oqtyArr에 있다. 
						sb.append(JumunfinishProductList.get(i).getPname()+"&nbsp;" + oqtyArr[i]+"개&nbsp;&nbsp;"); 		// JumunfinishProductList.get() : 끄집어온다. 이게 <ProductVO>						
					//	sb.append("<img src='http://localhost:9090/MyMVC/images/"+JumunfinishProductList.get(i).getPimage()+"' />");
					//	sb.append("<img src='https://dthumb-phinf.pstatic.net/?src=%22https%3A%2F%2Fshop-phinf.pstatic.net%2F20180322_158%2Fytown_152168562147881Lj3_JPEG%2F4404480322999218_80398500.jpg%3Ftype%3Dm450%22&type=ff120' />");
						sb.append("<br/>");
						// 테이블 태그로 다 하면 예쁘게 메일을 보낼 수있다. 함해봐라~~
					}// end of for----------
					
					sb.append("<br/>이용해 주셔서 감사합니다.");
					
					String emailContents = sb.toString();	// 다 받은 것을 문자로 바꿔주기. 메일에 들어갈 내용들
					
					String email ="woong110791@naver.com";
					mail.sendmail_OrderFinish(email, emailContents); 	// (받는사람, 이메일내용 )	: (로그인한사람의 이메일, 이메일내용)	// 	loginuser.getEmail()
			
				// **** 주문이 완료되었다라는 email을 보내기 끝 **** //
						
			String msg = "주문이 정상적으로 처리되었습니다.";
			String loc = "orderList.do";		// 주문 목록보기로 넘어가겠다. 생성해주자.
			
			super.alertMsg(req, msg, loc);
			session.removeAttribute("pcodeArr");
			session.removeAttribute("oqtyArr");
			session.removeAttribute("salepriceArr");
			session.removeAttribute("cartnoArr");
			session.removeAttribute("str_sumtotalprice");
			session.removeAttribute("str_sumtotalpoint");
		}
		else {
			String msg = "주문 처리가 실패되었습니다.";
			String loc = "javascript:history.back();";		
			
			super.alertMsg(req, msg, loc);
		}
		
		
	}// end of execute(HttpServletRequest req, HttpServletResponse res)-----------
		
	
	
	
	private String getOdrcode() {
		// *** 주문코드 생성하기 *** //	
		// 주문코드형식 : 이니셜+날짜+-+sequence => s20180430-1 ...
		
		// 날짜 생성
		Date now = new Date();
		SimpleDateFormat smdatefm = new SimpleDateFormat("yyyyMMdd");
		String today = smdatefm.format(now);
		
		// 시퀀스 가져오기. DAO 필요. 메소드가 다르기때문에 새로 생성		
		ProductDAO pdao = new ProductDAO();
		int seq_jsp_order = 0;
		
		try {
			seq_jsp_order = pdao.getSeq_order_detail();	
			// pdao.getSeq_order_detail();는 주문코드 시퀀스 seq_jsp_order 값을 따오는 것	
		} catch (SQLException e) {			
			e.printStackTrace();
		}			
				
		return "s"+today+"-"+seq_jsp_order;						
	}// end of getOdrcode() ---------
	

}
