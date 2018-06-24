<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<jsp:include page="../../header.jsp" />


<style type="text/css" >
	
	#accordion td { font-size: 10pt; font-family: 나눔고딕; }
	#accordion th { font-size: 10pt; font-family: 나눔고딕; }
    

</style>

<script type="text/javascript">
/*
	$(document).ready(function(){
		
		 $("#sizePerPage").bind("change", function(){
			var frm = document.sizePerPageFrm;
			frm.sizePerPage.value = document.getElementById("sizePerPage").value;
			frm.method = "get";
		//	frm.action = "orderList.do";
		//	frm.submit();
		});
		
	 	$("#sizePerPage").val("${sizePerPage}");
		// 3 or 5 or 10 이 들어온다.

		$("#btnDeliverStart").click(function(){
			
			var flag = false;
			
			$(".odrcode").prop("disabled", true); // 비활성화
			
			$(".deliverStartPnum").each(function(){ // 선택자 : 배송시작 체크박스
				var bool = $(this).is(":checked");  // 체크박스의 체크유무 검사
				
				if(bool == true) { // 체크박스에 체크가 되어 있으면
					 $(this).next().next().prop("disabled", false); // 활성화
					 flag = true;
				 } 
			}); 
			
			if(flag == false) {
				alert("먼저 하나이상의 배송을 시작할 제품을 선택하셔야 합니다.");
			}
			else {
				frmDeliver.method = "post";
				frmDeliver.action = "deliverStart.do";
				frmDeliver.submit();
			}
			
		});
		
		
		
		$("#btnDeliverEnd").click(function(){
			
			var flag = false;
			
			$(".odrcode").prop("disabled", true); // 비활성화
			
			$(".deliverEndPnum").each(function(){ // 선택자 : 배송완료 체크박스
				var bool = $(this).is(":checked"); // 체크박스의 체크유무 검사
				
				if(bool == true) { // 체크박스에 체크가 되어 있으면
					 $(this).next().next().prop("disabled", false); // 활성화
					 flag = true;
				}
			});
			
			if(flag == false) {
				alert("먼저 하나이상의 배송완료된 제품을 선택하셔야 합니다.");
			}
			else {
				var frm = document.frmDeliver;
				frm.method = "post";
				frm.action = "deliverEnd.do";
				frm.submit();
			}
		});
		
		
	});// end of $(document).ready();-----------------------------
	
*/

	function allCheckBoxStart() { 
	   var bool = document.getElementById("allCheckStart").checked;
	   var deliverStartPnumArr = document.getElementsByName("deliverStartPnum");
	   
	   for(var i=0; i < deliverStartPnumArr.length; i++) { 
		   deliverStartPnumArr[i].checked = bool;
	   } 
	   
	} // end of allCheckBoxStart()----------------------------
	
	
	function allCheckBoxEnd() { 
		   var bool = document.getElementById("allCheckEnd").checked; 
		   var deliverEndPnumArr = document.getElementsByName("deliverEndPnum"); 
		   
		   for(var i=0; i < deliverEndPnumArr.length; i++) { 
			   deliverEndPnumArr[i].checked = bool;
		   } 
		   
	} // end of allCheckBoxEnd()----------------------------
	
	
	function openMember(odrcode) {
		var url = "memberInfo.do?odrcode="+odrcode;
		
		//팝업창 띄우기
		window.open(url, "memberInfo", 
				    "width=550, height=600, top=50, left=100, resizable=no, status=no, scrollbars=yes, menubar=no");
		// "memberInfo"은 이 팝업창에 대한 참조값의 이름이다.
		
	}// end of openMember(odrcode)--------------------------------- 
	
</script>

<c:set var="userid" value="${(sessionScope.loginuser).userid}" />	
<c:set var= "loginuser" value="${(sessionScope.loginuser)}"/>

<c:if test='${userid != null && userid ne "admin"}'>
	<span style="font-size: 13pt; font-weight: bold; margin: 20px;">[ ${(sessionScope.loginuser).name} ]님 주문내역</span> <%--${(sessionScope.loginuser).name}님 [${userid}] 주문내역 목록 --%>
</c:if>

<form name="sizePerPageFrm">
	<input type="hidden" name="sizePerPage" />
</form>


<form name="frmDeliver">

<div id="accordion" role="tablist" style="margin-top: 20px;">
  <div class="card">
    <div class="card-header" role="tab" id="headingOne">
      <h5 class="mb-0">
        <a data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
        	 <span style="font-size: 12pt; font-weight: bold; color:black;">주문정보</span>  <!-- 주문번호, 주문일자, 주문자, 주문처리상태 -->
        </a>
      </h5>
    </div>

    <div id="collapseOne" class="collapse show" role="tabpanel" aria-labelledby="headingOne" data-parent="#accordion">
      <div class="card-body">
      
		<table class="table" >
		  <thead>
		    <tr style="text-align: center;">
		      <th scope="col" width="40%">주문번호</th>
		      <th scope="col" width="35%">주문일자</th>
		      <th scope="col" width="25%">주문자</th>		      
		    </tr>
		  </thead>
		  
		  <c:if test="${orderList==null || empty orderList}" > 
			<tr>
				<td colspan="4" align="center">
				<span style="color: red; font-weight: bold;">주문내역이 없습니다.</span>
			</tr>
		  </c:if>
		  
		  <tbody>
		    
	<%-- ============================================ --%>
		<c:if test="${orderList != null && not empty orderList }">
			<c:forEach var="ordermap" items="${orderList}" varStatus="status">
			<tr style="text-align: center;">
		      <th scope="row">${ordermap.odrcode}</th>			<!-- 주문코드 -->
		      <td>${ordermap.odrdate}</td>						<!-- 주문일자 -->
		      <td>${(sessionScope.loginuser).name}</td>			<!-- 주문자 -->		     
		    </tr>
		    		    
		   </c:forEach>
		</c:if>
		  </tbody>
		</table>      
       </div>
    </div>
    
    
  </div>
  <div class="card">
    <div class="card-header" role="tab" id="headingTwo">
      <h5 class="mb-0">
        <a class="collapsed" data-toggle="collapse" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
            <span style="font-size: 12pt; font-weight: bold; color:black;">주문상품</span> <!-- 이미지, 상품명, 금액, 수량   -->
        </a>
      </h5>
    </div>
    <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo" data-parent="#accordion">
      <div class="card-body">
                 
		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">이미지</th>
		      <th scope="col">상품정보</th>
		    </tr>		    
		  </thead>
		  <tbody>
		    
	    <c:if test="${orderList != null && not empty orderList }">
		<c:forEach var="ordermap" items="${orderList}" varStatus="status">
		    
		    <tr>
		      <th scope="row"><img src="img/${ordermap.pimage} " width="130" height="100" /> </th><!-- 제품이미지 -->
		      <td><span style="font-size: 10pt; font-weight: bold;">${ordermap.pname}</span><br/>			<!-- 상품명 -->
		      	 <fmt:formatNumber value="${ordermap.saleprice}" pattern="###,###" /> 원<br/>		<!-- 금액 -->
		       <!-- 색상 -->
		       <c:if test="${ordermap.pcolor == 1}">
		       WHITE
		       </c:if>
		       <c:if test="${ordermap.pcolor == 2}">
		       NAVY
		       </c:if>
		       <c:if test="${ordermap.pcolor == 3}">
		       BLACK
		       </c:if>
		       <c:if test="${ordermap.pcolor == 4}">
		       GRAY
		       </c:if>
		       <!-- 사이즈 -->
		       ${ordermap.psize} size<br/>
		       ${ordermap.oqty} 개<br/>			<!-- 수량 -->
		      	   <!-- 배송상태 -->
		      	   <c:choose>
						<c:when test="${ordermap.deliverstatus == '주문완료'}">
							<span style="color: green; font-weight: bold; font-size: 10pt;">주문완료</span><br/>
						</c:when>
						<c:when test="${ordermap.deliverstatus == '배송시작'}">
							<span style="color: green; font-weight: bold; font-size: 10pt;">배송시작</span><br/>
						</c:when>
						<c:when test="${ordermap.deliverstatus == '배송완료'}">
							<span style="color: green; font-weight: bold; font-size: 10pt;">배송완료</span><br/>
						</c:when>
		      	   </c:choose>
		      	   		      	   
		      </td>		
		      
		    </tr>		   
		    
		    
	    </c:forEach>
	    </c:if>
	  </tbody>
	</table>
            
      
      </div>
    </div>
  </div>
  <div class="card">
    <div class="card-header" role="tab" id="headingThree">
      <h5 class="mb-0">
        <a class="collapsed" data-toggle="collapse" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
          	<span style="font-size: 12pt; font-weight: bold; color:black;">결제정보</span> <!-- 총결제금액  총누적포인트 -->
        </a>
      </h5>
    </div>
    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree" data-parent="#accordion">
      <div class="card-body">
      
   		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">총결제금액</th>
		      <th scope="col">총누적포인트</th>		      	     
		    </tr>
		  </thead>
		  <tbody>
		  
			<c:if test="${orderList != null && not empty orderList }">
			<c:forEach var="ordermap" items="${orderList}" varStatus="status">
		   
		    <tr>
		      <th scope="row">
		      	<c:set var="su" value="${ordermap.oqty}" />
				<c:set var="danga" value="${ordermap.saleprice}" />
				<c:set var="totalmoney" value="${su * danga}" />
				     
				<fmt:formatNumber value="${totalmoney}" pattern="###,###" /> 원
		      
		      </th>
		      
		      <td>
				 <c:set var="point" value="${ordermap.point}" />
				 <c:set var="totalpoint" value="${su * point}" />
				 <fmt:formatNumber value="${totalpoint}" pattern="###,###" /> point
			  </td>
			  
		    </tr>
		    
			</c:forEach>
			</c:if>
		  
		  </tbody>
		</table>
      
      </div>
    </div>
  </div>
  
  <div class="card">
    <div class="card-header" role="tab" id="headingThree">
      <h5 class="mb-0">
        <a class="collapsed" data-toggle="collapse" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
          	<span style="font-size: 12pt; font-weight: bold; color:black;">배송지정보</span> <!-- 받는사람, 주소, 연락처  불러올때 loginuser-->
        </a>
      </h5>
    </div>
    <div id="collapseFour" class="collapse" role="tabpanel" aria-labelledby="collapseFour" data-parent="#accordion">
      <div class="card-body">
      
   		<table class="table">
		  <thead>
		    <tr>
		      <th scope="col" width="45%">받는이 (연락처)</th>
		      <th scope="col" width="55%">주소</th>			    	     
		    </tr>
		  </thead>
		  <tbody>
		  	<c:if test="${orderList != null && not empty orderList }">
			<c:forEach var="ordermap" items="${orderList}" varStatus="status">
		  
		    <tr>
		      <th scope="row"> ${loginuser.name}<br/>
		     (${loginuser.hp1}-${loginuser.hp2}-${loginuser.hp3})
		      
		      </th>
		      <td>
				${loginuser.addr1} <br/>
				${loginuser.addr2}
		      </td>	<!-- 주소 -->
		      
		
		    </tr>
		    		    
		    </c:forEach>
		    </c:if>
		    
		  </tbody>
		</table>
      
      </div>
    </div>
  </div>
    
  
</div>
</form>


<%-- ////////////////////////////////////////////////////////////////////////////////////////// --%>

<div align="center" style="margin-top: 20px; margin-bottom: 40px;">
	${pageBar}
</div>


<jsp:include page="../../footer.jsp" />