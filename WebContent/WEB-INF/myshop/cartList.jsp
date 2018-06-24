<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<jsp:include page="../../header.jsp" />

<style type="text/css" >   
    
   #cartTBL td { font-size: 10pt; font-family: 나눔고딕; }  
   
       
   .btn { font-size: 13px;
		font-family: 나눔고딕;
		color: white;
		line-height: 20px; 
		text-align: center;
		border-radius: 10px;	
		}
    
</style>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		$("#totalText").val(0);
	});

	function fun_check() {		
		
		var data1 = 0;
		
		var a ="";
		// alert("data 초기값 : " + data);
     
		$('input:checkbox[name="fk_pcode"]').each(function() {
			if ($(this).is(":checked")){
				var $target = $(this);
				 a = $target.next().val();
				 
				 data1 =  data1 + Number(a);
			}
	       /* 
	        alert("확인 a" + a);
		    alert("최종 data = " + data);
		    */
		    $("#totalText").val(data1);
		    
        });
	    
	}// end of fun_check()----------------------------
		
		
	// **** 전체 주문하기 ****//
	function goOrderAll() {
		
		var bool = $("#allCheckOrNone").is(':checked');
		
		$(".chkboxpnum").prop('checked', true);
		
		var fk_pcodeArr = document.getElementsByName("fk_pcode"); 
		
		var cnt = 0;
		var sumtotalprice = 0;
		var sumtotalpoint = 0;
		
		for(var i=0; i<fk_pcodeArr.length; i++){
			// 아래의 if는 주문을 위해 체크한 제품인 경우 주문총액 및 주문총포인트를 구하도록 한다.
			if(fk_pcodeArr[i].checked == true){		// 체크가 되었다면 true
				// 장바구니에서 주문을 위해 체크된 제품이라면 
				cnt ++;		// 몇개가 체크되었는지 카운트해보자.
				
				// **** 주문량 텍스트박스의 값이 숫자로 1개 이상인지 아닌지 검사하기 **** //
				var poqty = document.getElementById("poqty"+i).value;
		//		console.log(poqty);
			
				var regExp = /^[0-9]+$/g;		// 숫자만 체크하는 정규식
				var bool = regExp.test(poqty);
		//		console.log(bool);	
										
				if(!bool){
					// 주문 갯수에 숫자가 아닌 문자가 있다라면
					alert("주문량은 숫자로만 입력이 가능합니다.");
					return;		// 종료
				}
				else if(bool && parseInt(poqty) < 1){
					// 주문 갯수가 숫자지만 1보다 작은 경우
					alert("주문량의 갯수는 1개 이상이여야 합니다.");
					return;		// 종료			
				}
				
				sumtotalprice += parseInt(document.getElementById("totalprice"+i).value);
				sumtotalpoint += parseInt(document.getElementById("totalpoint"+i).value);
				
			}
				
		}// end of for-------------
						
		var yn = confirm("전체 제품을 주문하시겠습니까?");		// bool 값을 받는다. true 확인. false 취소
		//	console.log(yn);
			
			if(yn==false){	// 취소 눌렀을 경우
				return;
			}
			else{	
							
				var frm = document.orderFrm;	// 폼에 담아서 보내줘야한다.
				
				frm.sumtotalprice.value=sumtotalprice;		//frm 안에있는 name(sumtotalprice)의 value에 위에서 구한 sumtotalprice을 넣어주겠다.
				frm.sumtotalpoint.value=sumtotalpoint;
				
				frm.method ="post";
				frm.action ="productsession.do";		// 주문하는거지 주문내역을 보는게 아니다.
				frm.submit(); 
				
			}
		
	
	}// end of goOrderAll()-------------------
	
	// **** 선택 주문하기 *****
	function goOrder() {
		
		var fk_pcodeArr = document.getElementsByName("fk_pcode"); 	// <!-- 체크박스 및 제품번호 --> 의 name 
		
		var cnt = 0;
		var sumtotalprice = 0;
		var sumtotalpoint = 0;
		
		for(var i=0; i<fk_pcodeArr.length; i++){
			// 아래의 if는 주문을 위해 체크한 제품인 경우 주문총액 및 주문총포인트를 구하도록 한다.
			if(fk_pcodeArr[i].checked == true){		// 체크가 되었다면 true
				// 장바구니에서 주문을 위해 체크된 제품이라면 
				cnt ++;		// 몇개가 체크되었는지 카운트해보자.
				
				// **** 주문량 텍스트박스의 값이 숫자로 1개 이상인지 아닌지 검사하기 **** //
				var poqty = document.getElementById("poqty"+i).value;
		//		console.log(poqty);
			
				var regExp = /^[0-9]+$/g;		// 숫자만 체크하는 정규식
				var bool = regExp.test(poqty);
		//		console.log(bool);	
										
				if(!bool){
					// 주문 갯수에 숫자가 아닌 문자가 있다라면
					alert("주문량은 숫자로만 입력이 가능합니다.");
					return;		// 종료
				}
				else if(bool && parseInt(poqty) < 1){
					// 주문 갯수가 숫자지만 1보다 작은 경우
					alert("주문량의 갯수는 1개 이상이여야 합니다.");
					return;		// 종료			
				}
				
				sumtotalprice += parseInt(document.getElementById("totalprice"+i).value);
				sumtotalpoint += parseInt(document.getElementById("totalpoint"+i).value);
				
		//		console.log("sumtotalprice "+sumtotalprice);
		//		console.log("index "+i);
				
			}
			else if(fk_pcodeArr[i].checked == false){ // else만 써도된다. 헷갈리니까 써본다. 
				// !!! *** 중요 *** !!!
				// 장바구니에서 주문을 안하려고 체크하지 않은 제품인 경우
				// 폼전송 대상에서 제외시키기 위해서 비활성화 시킨다.		// 비활성화시킨것은 넘어가지 않는다. 제품번호,수량,총액,총포인트 전부다 비활성화 시킨다.
				document.getElementById("fk_pcode"+i).disabled = true; 	// 체크박스의 id // disabled : 비활성화시킬까요? = true 그래라
				document.getElementById("poqty"+i).disabled = true; 	// 수량의  id 	 // disabled : 비활성화시킬까요? = true 그래라
				document.getElementById("cartno"+i).disabled = true; 
				document.getElementById("totalprice"+i).disabled = true; 
				document.getElementById("totalpoint"+i).disabled = true; 				
			}
						
		}// end of for-------------
		
		//	console.log("누적 총 금액 : "+ sumtotalprice);
		//	console.log("누적 총 포인트 : "+ sumtotalpoint);
			
		if(cnt == 0){// 체크를 안하면 cnt는 그대로 0이다.
			// 주문할 제품에 체크가 한개도 안되어져 있을 경우
			alert("주문할 제품을 하나이상 선택하세요.");	
		
			// alert 나오고 나서 다 비활성화 되어있는 것을 풀어줘야한다.
			// 비활성화 한것을 다시 활성화 시켜라
			for(var i=0; i<fk_pcodeArr.length; i++){
				document.getElementById("fk_pcode"+i).disabled = false; 	// 체크박스의 id // disabled : 비활성화시킬까요? = false 아니오
				document.getElementById("poqty"+i).disabled = false; 	
				document.getElementById("cartno"+i).disabled = false; 
				document.getElementById("totalprice"+i).disabled = false; 
				document.getElementById("totalpoint"+i).disabled = false; 
			}	
		
			return;
		}
		else if(cnt >0 ){
			// 주문할 제품에 체크가 1개이상 되어있고 주문 갯수도 숫자로 1개이상으로 되어진 경우
			var yn = confirm("선택한 제품을 주문하시겠습니까?");		// bool 값을 받는다. true 확인. false 취소
		//	console.log(yn);
			
			if(yn==false){	// 취소 눌렀을 경우
				return;
			}
			else{	
				
				var frm = document.orderFrm;	// 폼에 담아서 보내줘야한다.
				
				frm.sumtotalprice.value=sumtotalprice;		//frm 안에있는 name(sumtotalprice)의 value에 위에서 구한 sumtotalprice을 넣어주겠다.
				frm.sumtotalpoint.value=sumtotalpoint;
				
				/* window.open("coinPurchaseEnd.do", "coinPurchaseEnd",
			    "left=350px, top=100px, width=820px, height=600px"); */
				
				frm.method ="post";
				frm.action ="productsession.do";		// 주문하는거지 주문내역을 보는게 아니다.
				frm.submit(); 
			}
			
		}
	
	}// end of goOrder()-------------------
	/* 
	function orderCheck(){
		
		var frm = document.orderFrm;	// 폼에 담아서 보내줘야한다.
		frm.method ="post";
		frm.action ="orderadd.do";		// 주문하는거지 주문내역을 보는게 아니다.
		frm.submit();  
	
	}
	 */
	
	
	// *** 장바구니 수정 ***//
	function goPoqtyEdit(poqtyID , cartnoID) {
			
		var poqty = $("#"+poqtyID).val();		
			
		var cartno = $("#"+cartnoID).val();
	
//		console.log("주문량 : "+ poqty); 		// 항상 찍어보고 넘어가자.
//		console.log("장바구니번호 : "+cartno);  	// 항상 찍어보고 넘어가자.
		
 		var regExp = /^[0-9]+$/g;		// 숫자만 체크하는 정규식. 유효성검사(꼭해줘야한다.)
		var bool = regExp.test(poqty);
		
		if(!bool || parseInt(poqty)<0 ){ //fasle 라면 || 음수라면
			alert("수정하려는 수량은 0개 이상이어야 합니다.");
			location.href="cartList.do";		
			return;
		} 
		
		
		var frm = document.updateOqtyFrm;
		frm.poqty.value= poqty;	 	// 이 폼안에있는 name이 oqty에 값을 넣어주자.
		frm.cartno.value= cartno;	// 이 폼안에있는 name이 oqty에 값을 넣어주자. 
		frm.goBackURL.value='${currentURL}';
						
		frm.method ="post";
		frm.action ="cartEdit.do";
		frm.submit();
				
	}// end of goOqtyEdit(cartno, oqtyindex, pnum)---------------------
	
	// *** 장바구니 삭제 ***//
	function goDel(cartno) {
		
		var frm = document.deleteFrm;
		frm.cartno.value = cartno;
	//	frm.goBackURL.value = '${currentURL}';
		
		frm.method = "post";
		frm.action = "cartDel.do";
		frm.submit();
				
	}// end of goDel(cartno, pnum)--------------
	
	
	// 체크박스 모두선택 및 모두해제 되기 위한 함수
	function allCheckBox() {
		
		var bool = $("#allCheckOrNone").is(':checked');
		/* $("#allCheckOrNone").is(':checked') 은
	            선택자 $("#allCheckOrNone") 이 체크 되어지면 true 를 나타내고,
	            선택자 $("#allCheckOrNone") 이 체크가 안되어지면 false 를 나타내어주는 것이다.  
	    */		
		$(".chkboxpnum").prop('checked', bool);
		
		fun_check();
		
	}// end of allCheckBox()------------
	
	
	// *** 선택한 상품 삭제하기 *** //
	function prodDel(){
			
		var delChkboxArr = document.getElementsByName("fk_pcode");	// getElementsByName("네임") // 소스보기하면 name이 다 똑같다(foreach라서) 그래서 배열
				
		var count = 0;
		for(var i=0; i<delChkboxArr.length; i++){
			if(delChkboxArr[i].checked){		 // 체크박스에 체크가 되어있으면 true(속성값)
				count++;
			}			
		}// end of for-----------------
		
	//	alert(count);
		
		if(count == 0){
			alert("삭제 할 제품이 선택되지 않았습니다.");	// 확인 밖에 없음
			return;
		}
		else{		// 체크가 되어져 있으면
			var bool = confirm("선택한 제품을 삭제하시겠습니까?");		// 확인 또는 취소가 있다. 확인 true, 취소 false. confirm은 boolean타입이다.
			
			if(bool){	// 확인을 눌렀다면. true 라면				
				var frm = document.orderFrm;
				frm.method="post";			// DML은 post가 좋다.
				frm.action="choiceProductDel.do";
				frm.submit(); 
			}
		}
	}// end of ChoiceDel()--------
	
	
		
</script>
<form name="orderFrm">
<div align="center">
<table class="table table-bordered" id="cartTBL" >
	<tbody>
		<tr>
			<td style="border-right-style: none; vertical-align: middle;" width="60%">	
			<input type="checkbox" id="allCheckOrNone" onClick="allCheckBox();" />
			<span style="font-weight: bold;"><label for="allCheckOrNone">전체선택</label></span>						  
			</td>	
	 
			<td> 
			<span style="font-weight: bold; vertical-align: middle;" onclick="prodDel();">선택삭제</span>
		 <!-- <button type="button" onclick="prodDel();">선택삭제</button> -->
		 	</td>		 	
		</tr>	   
	 
	  
	  
		  <c:if test="${cartList == null || empty cartList}">		
		  <tr>
			<td colspan="2" align="center">
				<span>장바구니에 담긴 상품이 없습니다.</span>
		  </tr>
		  </c:if>
		  
		  <c:if test="${cartList != null ||not empty cartList}">	
	   	  <c:set var="cartTotalPrice" value="0"/> <!-- 장바구니 전체 총금액(누적)변수 및 초기치 선언 -->
	   	  <c:set var="cartTotalPoint" value="0"/> <!-- 장바구니 전체 총포인트(누적)변수 및 초기치 선언 -->
	   	  
	   	  <c:forEach var="cartvo" items="${cartList}" varStatus="status">	   	  	
   	  		<c:set var="cartTotalPrice" value="${cartTotalPrice + cartvo.item.totalPrice}" /> <!-- 누적시킴 -->
   	  		<c:set var="cartTotalPoint" value="${cartTotalPoint + cartvo.item.totalPoint}" /> <!-- 누적시킴 -->
	   	  	
		    <tr>	      
		      <td><!-- 체크박스/ 제품이름 -->
				<input type="checkbox" name="fk_pcode" id="fk_pcode${status.index}" value="${cartvo.fk_pcode}" class="chkboxpnum" onClick="fun_check(this);"  /><%-- &nbsp;${cartvo.fk_pcode} --%>   	<!--  실제로 체크한 것들이 주문으로 넘어가니까 pnum이 넘어가야좋다. 타입은 체크박스지만  체크되면 on받아오고 안되면 null로 받는다.	 -->	
				<input type="hidden" name="Totalsaleprice" id="Totalsaleprice" value="${cartvo.item.totalPrice}" />	<!-- 수량 * 판매가 -->
  	  			<span>${cartvo.item.pname}</span>
			  </td>
			  
		      <td><!-- 삭제 -->
		      	<span class="del" style="cursor: pointer; " onclick="goDel('${cartvo.cartno}');">삭제</span>
		      </td>
		    </tr>
		    
		    <tr>	      
		      <td align="center">
		      	<img src="img/${cartvo.item.pimage}" width="130px;" height="160px;" />
		      </td>
		      <td>
	   	  		<c:if test="${cartvo.item.pcolor == 1}">
	   	  			NAVY&nbsp;/&nbsp;${cartvo.item.psize}size
	   	  		</c:if>
	   	  		<c:if test="${cartvo.item.pcolor == 2}">
	   	  			BLACK&nbsp;/&nbsp;${cartvo.item.psize}size
	   	  		</c:if>
	   	  		<c:if test="${cartvo.item.pcolor == 3}">
	   	  			GRAY&nbsp;/&nbsp;${cartvo.item.psize}size
	   	  		</c:if>
	   	  		<c:if test="${cartvo.item.pcolor == 4}">
	   	  			WHITE&nbsp;/&nbsp;${cartvo.item.psize}size
	   	  		</c:if>	   	  		
				<br/><!-- 실판매단가  및 포인트  -->
				<fmt:formatNumber value="${cartvo.item.saleprice}" pattern="###,###"/> 원 
				<br/>
				<fmt:formatNumber value="${cartvo.item.point}" pattern="###,###"/> Point<br/>
	   	  		<input type="hidden" name="saleprice" id="saleprice${status.index}" value="${cartvo.item.saleprice}" />
	   	  		
	   	  		<br/>
	   	  		<input type="text" name="poqty" id="poqty${status.index}" value="${cartvo.poqty}" size="1" style="text-align: center;" />개
	   	  		<!-- 장바구니 번호 -->
	   	  		<input type="hidden" name="cartno" id="cartno${status.index}" value="${cartvo.cartno}" size="4" /> 	 <%--  forEach하면 반복되기 때문에 고유해야한다. cartno${status.index} 고유할수밖에 없다. 	   --%>
	   	  			<!-- !!! *** 중요함 *** !!!
	   	  			forEach에서 id를 고유하게 사용하는 방법
	   	  			status.index나 status.count를 이용하면된다. -->		   	  		
	   	  		<button type="button" style="font-size: 10px; width:40px; height: 25px;" class="img_button" onclick="goPoqtyEdit('poqty${status.index}','cartno${status.index}');">변경</button>
	   	  		<%-- <span onclick="goPoqtyEdit('poqty${status.index}','cartno${status.index}');">수정</span>	 --%>
	   	  		<!-- <button type="button" class="ordershopping" onClick="goOrder();">바로 주문하기</button>	 -->   
	   	  		<br/><br/><!-- 수량 * 실판매단가 -->
	   	  		<span style="font-size: 12px; font-weight: bold;"><fmt:formatNumber value="${cartvo.item.totalPrice}" pattern="###,###"/></span> 원 
	   	  		
	   	  	  </td>	  	   
		    </tr>
		    
		    <input type="hidden" id="totalprice${status.index}" value="${cartvo.item.totalPrice}" /> <%-- 총주문금액  --%>
			<%-- 주문하기를 했을 경우 주문하려고 체크한 금액을 더해서 sumtotalprice에 누적하기 위한 용도 ↑ --%>
			<input type="hidden" id="totalpoint${status.index}" value="${cartvo.item.totalPoint}" /> 
   	  		<%-- 주문하기를 했을 경우 주문하려고 체크한 포인트를 더해서 sumtotalpoint에 누적하기 위한 용도 ↑ --%>
		   		    
	   	  </c:forEach>		
	   </c:if>		
		
	 	  <tr>
			<td>선택한 상품 금액</td>			
			<td>			
				<input type="text" id="totalText" style="border-style: hidden;font-weight: bold; color:#cc3300;" size="5"/>원	<!-- border-style: hidden =>테두리선 감추기. -->
				
			</td>			
		  </tr>	 
			 
		  <tr>	   	 
		   	  <td>총주문금액</td>  
		   	  <td>
			   	  <fmt:formatNumber value="${cartTotalPrice}" pattern="###,###"/>&nbsp;원
			   	  <input type="hidden" name="sumtotalprice" id="sumtotalprice" />	<!-- 주문하기를 클릭했을 경우 총누적 금액을 orderadd.do로 넘기기 위한 용도 -->
		   	  </td>  	
		  </tr> 
		  
		 <tr>
		  	<td>총포인트</td>
		  	<td>
			  	<fmt:formatNumber value="${cartTotalPoint}" pattern="###,###"/>&nbsp;P
			  	<input type="hidden" name="sumtotalpoint" id="sumtotalpoint" />	<!-- 주문하기를 클릭했을 경우 총누적 포인트를 orderadd.do로 넘기기 위한 용도 -->
		  	</td>
		</tr>
	
	</tbody>
</table>
</div>
</form>


<div align="center">${pageBar}</div>

<div align="center" style="margin-top:20px; margin-bottom:20px; ">
	<button type="button" class="btn btn-dark"  onClick="goOrder();">선택주문하기</button>
	<button type="button" style="margin-left:20px;" class="btn btn-dark" onClick="goOrderAll();">전체주문하기</button>
	
</div>

  
 <%-- 장바구니에 담긴 제품수량을 수정하는 form --%>
 <form name="updateOqtyFrm">
 	<input type="hidden" name="poqty"  />
 	<input type="hidden" name="cartno" />
 	<input type="hidden" name="goBackURL" />
 </form>
 
 <%-- 장바구니에 담긴 제품을 삭제하는 form --%> 
 <form name="deleteFrm">
 	<input type="hidden" name="cartno" />
 	<input type="hidden" name="goBackURL" />
 </form>
 
    
<jsp:include page="../../footer.jsp" />



<%-- /////////////////////////////////////////////////////////////////////////////////////////////////// --%>



    