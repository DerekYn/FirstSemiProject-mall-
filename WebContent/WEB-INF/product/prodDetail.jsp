<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../../header.jsp" />

<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
<!-- 순서는 jquery 일반 라이브러리 소스가 머저 나오고 그이후에 jquery ui 소스사용 --> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	
	$(".form-group").hide();
	$("#btnupdate").hide();
	$("#btnadd").hide();

	goLikeDisLikeCountShow();
	
	$('#oqty').keyup(function() { 
		 var oqty = $('#oqty').val();
		 var regExp = /^[0-9]+$/; 
		 var sumtotalprice = parseInt("${pvo.saleprice}")*oqty;
		 
		 if(!regExp.test(oqty)){
		 	$('#oqtyan').val(0);
		 }else{
			 $('#oqtyan').val(sumtotalprice);
		 }
			  
	});
	//한줄평 게시물을 더보기 위하여 더보기.. 버튼 클릭액션에 대한 초기값 호출
	displayMemoAppend("1");
	
	//한줄평게시물의  더보기 .. 버튼을 클릭 이벤트 등록
	
	$("#btnMoreMemo").bind("click", function() {
		
		if($("#btnMoreMemo").text()=="처음으로"){
			$("#displayResult").empty();
			displayMemoAppend("1");
			$(this).text("더보기..");
			
		}else{
			displayMemoAppend($(this).val());	
		}
		
		
	});
	
});//end of ready()----------------------------

var lenMemo = 3;//NEW 상품 더보기.. 클릭에 보여줄 상품의 갯수 단위크기

function displayMemoAppend(start) {
	
	//display할 NEW정보 추가 요청하기(Ajax로 처리함.)
	
	var form_data = {"start": start,
					"len":lenMemo,
					"pcode" : "${pvo.pcode}"};
	
	$.ajax({
		url:"memodisplayJSON.do",
		type: "GET",
		data: form_data,
		dataType: "JSON",
		success: function(data) {
			
			var html ="";
			
			if(data.length == 0 && start == "1"){
				//데이터가 아예없는 경우
				
				html +=	" 상품평이 없습니다. ";
					
					$("#displayResult").html(html);
					//더보기 버튼의 비활성화
					$("#btnMoreMemo").attr("disabled", true);
					$("#btnMoreMemo").css("cursor", "not-allowed");
					
			}
			
			else if(data.length == 0 && start != "1") {

				$("#btnMoreMemo").attr("disabled", true);
				$("#btnMoreMemo").css("cursor", "not-allowed");
			}
			else{
				//데이터가 존재할경우
				var i = 0;
				$.each(data, function(entryIndex, entry) {
				
					
					html += " <tr> <td style='word-break:break-all; font-size:10pt;' >"+entry.fk_userid+"</td> "
							+" <td style='word-break:break-all; font-size:10pt;'>"+entry.comments+"</td> ";
							var userid2 = entry.fk_userid;
						
							 if("${sessionScope.loginuser.userid}" == userid2){
								html += " <td><a id='update"+i+"' style='cursor: pointer; font-size:10pt;' onclick='commentupdate();' >수정</a> | <a id='delete"+i+"' style='cursor: pointer;  font-size:10pt;' onclick='memoaddupdate(3);' >삭제</a></td> ";
									
							}
								html += " </tr> "; 
							
					i = i+1;
				});//
				
				
				
				$("#displayResult").append(html);
				
				$("#btnMoreMemo").val(parseInt(start)+lenMemo);
				/*
					문서가 로딩 되어지면 parseInt(start)은 1이고 lenMemo는 5이므로
					더보기.. 버튼의 value 값은 6가 되어진다.
				*/
				
				//웹브라우저상에 count 출력하기
				$("#countMemo").text(parseInt($("#countMemo").text())+ data.length);
				
				//*** 더보기 버튼을 계속해서 눌러서 countMemo와 totalMemoCount가 일치하는 경우 ***//
				//버튼의 이름을 처음으로 라고 변경하고 countMemo는 0으로 초기화 한다.
				if($("#totalMemoCount").text() == $("#countMemo").text() && start != "1"){
					$("#btnMoreMemo").text("처음으로");
					$("#countMemo").text("0");
					
				}
				
			}//end of if~else-----------------------
			
			
		},
		error: function (request, status, error) {
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
		
	});
	
}//end  of displayNEWAppend(start)--------------------------------------
function goCart(pcode) { // pnum 은 제품번호 이다.
	   
	  // *** 주문량에 대한 유효성 검사하기 ***//
	  var frm = document.pcodeFrm;
	  var regExp = /^[0-9]+$/; // 숫자만 체크하는 정규식
	  var bool = regExp.test(frm.oqty.value);
	 
	  if(!bool) {
		  // 숫자 이외의 값이 들어온 경우
		  alert("주문갯수는 1개 이상이어야 합니다.");
		  frm.oqty.value = "0";
		  frm.oqty.focus();
		  return;
	  }
	  else {
		  // 숫자가 들어온 경우
		  var oqty = parseInt(frm.oqty.value);
		  
		  if(oqty < 1) {
			  alert("주문갯수는 1개 이상이어야 합니다.");
			  frm.oqty.value = "0";
			  frm.oqty.focus();
			  return; 
		  }
		  else { // 올바르게 1개 이상 주문한 경우
			  frm.fk_pcode.value = pcode;
		  
			  frm.method = "post";
		      frm.action = "cartAdd.do";
			  frm.submit();
		  }
	  }
	   
	   
 }// end of function goCart(pnum)------------------
 
 
 function goOrder(pcode) {// pcode 은 제품번호 이다.
	   
	   // *** 주문량에 대한 유효성 검사하기 ***//
		  var frm = document.pcodeFrm;
		  var regExp = /^[0-9]+$/; // 숫자만 체크하는 정규식
		  var bool = regExp.test(frm.oqty.value);
		 
		  if(!bool) {
			  // 숫자 이외의 값이 들어온 경우
			  alert("주문갯수는 1개 이상이어야 합니다.");
			  frm.oqty.value = "0";
			  frm.oqty.focus();
			  return;
		  }
		  else {
			  // 숫자가 들어온 경우
			  var oqty = parseInt(frm.oqty.value);
			  
			  if(oqty < 1) {
				  alert("주문갯수는 1개 이상이어야 합니다.");
				  frm.oqty.value = "0";
				  frm.oqty.focus();
				  return; 
			  }
			  else { // 올바르게 1개 이상 주문한 경우
				 
				 var sumtotalprice = parseInt("${pvo.saleprice}")*oqty;
				 
				 var sumtotalpoint = parseInt("${pvo.point}")*oqty;
				 
				  frm.fk_pcode.value = pcode;
			  	  frm.sumtotalprice.value = sumtotalprice;
			  	  frm.sumtotalpoint.value = sumtotalpoint;
			  	  
			  	  frm.method ="post";
				  frm.action ="productsession.do";		// 주문하는거지 주문내역을 보는게 아니다.
				  frm.submit(); 
			  }
		  }
	  
	   
 	}// end of function goOrder(pnum)---------------
 
 
	 

function goLikeDisLikeCountShow() {
		
		var form_data = {"pcode" : "${pvo.pcode}"}
		
		$.ajax({
			url: "likeDislikeCountShow.do",
			type: "GET",
			data: form_data,
			dataType: "JSON",
			success: function(json){
				$("#likecnt").html(json.likecnt);
				$("#dislikecnt").html(json.dislikecnt);
				
			},
			error: function(request, status, error){
		    	alert("code: " + request.status+"\n"+" message: " + request.responseText+"\n"+"error: "+error);
		    			    	
		    }
			
		});
	}
	
	
	
	
	function goDisLikeAdd(pcode) {
		
		var form_data = {"userid" : "${sessionScope.loginuser.userid}",
				 "pcode" : pcode,
				 "comments": $("#comments").val()};

		$.ajax({
			url: "dislikeAdd.do",
			type: "POST",
			data: form_data,
			dataType: "JSON",
			success: function(json){
				swal(json.msg);
				goLikeDisLikeCountShow();
				
			},
			error: function(request, status, error){
		   	alert("code: " + request.status+"\n"+" message: " + request.responseText+"\n"+"error: "+error);
		   			    	
		   }
		});
		
	}// end of goDisLikeAdd(pnum) ------------------------------------------


function goLikeAdd(pcode) {
	
	var form_data = {"userid" : "${sessionScope.loginuser.userid}",
					 "pcode" : pcode,
					 "comments": $("#comments").val()};
	
	$.ajax({
		url: "likeAdd.do",
		type: "POST",
		data: form_data,
		dataType: "JSON",
		success: function(json){
			swal(json.msg);
			goLikeDisLikeCountShow();
			
		},
		error: function(request, status, error){
	    	alert("code: " + request.status+"\n"+" message: " + request.responseText+"\n"+"error: "+error);
	    			    	
	    }
	});
	
}// end of function goLikeAdd(pnum) --------------------------

function showWriteMemo(n) {
	
	$("#btnupdate").hide();
	$("#btnadd").hide();
	$(".form-group").show();
	$("#comments").val("");
	if(n==0){
		$("#btnadd").show();
	}else if(n==1){
		$("#btnupdate").show();
		
		function showcomment(){

			var form_data = {"userid" : "${sessionScope.loginuser.userid}",
					 			"pcode" : pcode };
			
			$.ajax({
				url: "showcomment.do",
				type: "POST",
				data: form_data,
				dataType: "JSON",
				success: function(json){
					$("#comments").val(json.scomment);
					
					
				},
				error: function(request, status, error){
			    	alert("code: " + request.status+"\n"+" message: " + request.responseText+"\n"+"error: "+error);
			    			    	
			    }
			});
			
		}
	
	}//end of else if~~~
	

	
	
}//end of showWriteMemo()----------------------------------------------------
function memoaddupdate(n){
	var frm = document.memoaddupdatefrm;
	
	$("#num").val(n);
	
	var num = $("#num").val();

	
	frm.userid.value="${sessionScope.loginuser.userid}";
	$("#fk_pcode").val(${pvo.pcode});

	
	frm.method = "POST";
    frm.action = "memoaddupdate.do";
	frm.submit(); 
	
}//end of memoaddupdate()-------------------------------------------------------

function commentadd() {
	var n = 0;
	showWriteMemo(n); 
	
}//end of commentdelete()---------------------------------------------------------


function commentupdate() {
	
	$("#btnupdate").hide();
	$("#btnadd").hide();
	$(".form-group").show();
	$("#btnupdate").show();
	
	
	var form_data = {"userid" : "${sessionScope.loginuser.userid}",
			 			"pcode" : ${pvo.pcode}
					};
	
	$.ajax({
		url: "showcomment.do",
		type: "POST",
		data: form_data,
		dataType: "JSON",
		success: function(json){
			$("#comments").val(json.scomment);
			
			
		},
		error: function(request, status, error){
	    	alert("code: " + request.status+"\n"+" message: " + request.responseText+"\n"+"error: "+error);
	    			    	
	    }
	});
		
	
	
}//end of commentdelete()---------------------------------------------------------


function orderCheck(){
	
	var oqty = $("#oqty").val();
	alert("oqty : " + oqty);
	$("#poqty").val(oqty);
	
	var frm = document.pcodeFrm;	// 폼에 담아서 보내줘야한다.
	frm.method ="post";
	frm.action ="orderadd.do";		// 주문하는거지 주문내역을 보는게 아니다.
	alert("보낸다");
	frm.submit();  

}

</script>
<style type="text/css">

ul{
   list-style:none;
   padding-left:0px;
   padding-top:3%;
   }


</style>

<div style="width: 98%; margin-left: 1%; margin-top: 2%; margin-bottom: 5%; border: 0px solid black"> 
	<div style="text-align: center; margin-bottom: 15%;">
		<span style="font-size: 19pt; font-weight:400;">${pvo.pname}</span>
	</div>

		<div align="center" style="float: none; width: 80%; margin-left: 10%; margin-bottom: 5%; border: 0px solid green;">
			<img src="img/${pvo.pimage}" style="width: 100%; height: 100%; "  />            
		</div>
		<div align="left" style="float: none; width: 95%; margin-left: 3%; margin-bottom: 0%; border: 0px solid green;">
			<ul>
	        	<li><span style="font-size: 11pt; color:gray;">정상가</span>  <span style="font-size:11pt; font-weight: 100; padding-left: 10%;"><fmt:formatNumber value="${pvo.price}" pattern="###,###" />원</span></li>
	        	<li><span style="font-size: 11pt; color:gray;">판매가</span> <span style="color: black; font-size:11pt; font-weight: 600; padding-left: 10%;"><fmt:formatNumber value="${pvo.saleprice}" pattern="###,###" />원</span></li>
	        	<li><span style="font-size: 11pt; color:gray;">포인트</span> <span style="font-size:11pt; font-weight: 100; padding-left: 10%;">${pvo.point} P</span></li>
			</ul>
		
		 <%-- *** 장바구니 담기 및 바로주문하기 폼 *** --%>

		<div id="abc">
	        <form name="pcodeFrm" style="margin-top: 4%;">
	          <div class="color" style="border-top:2px solid gray; ">
				<ul>
	        		<li><span style="font-size: 11pt; color:gray;">색상</span></li>
		       		<label><input type="radio" name="pcolor" value="0" checked="checked">검정색</label>
					<label><input type="radio" name="pcolor" value="1" >네이비색</label>
					<label><input type="radio" name="pcolor" value="2" >흰색</label>
					<label><input type="radio" name="pcolor" value="3" >그외</label>

	        		<li><span style="font-size: 11pt; color:gray;">사이즈</span></li>
	        		<label><input type="radio" name="psize" value="${pvo.psize}" checked="checked">${pvo.psize}</label>
	        		<li><span style="font-size: 11pt; color:gray;">수량</span> <input type="text" style="margin-left: 7%;" maxlength="4" id="oqty" name="oqty" placeholder="숫자만 입력하세요"></li>
				</ul>
	           </div>
	            <div style="border-top: 2px solid gray;">
		            <ul>
						<li><span style="font-size: 11pt; color:gray;">총금액  </span><input type="text" style="margin-left: 7%; border:0 solid black; " id="oqtyan" readonly ></li>
		            </ul>
	            </div>
			
	            <div align="center" style="padding-top: 2%;">
	            	<button type="button" class="btn" id="cart" onClick="goCart('${pvo.pcode}');">장바구니담기</button>
	            	<button type="button" class="btn" id="order" onClick="goOrder('${pvo.pcode}');">바로주문하기</button>
	            </div>
	            
	           
	            <input type="hidden" id= "fk_pcode" name="fk_pcode" value="${pvo.pcode}" />
	            <input type="hidden" name="pname" value="${pvo.pname}" />
	            <input type="hidden" name="currentURL" value="${currentURL}" />
	            <input type="hidden" name="saleprice" value="${pvo.saleprice}" />
	            <input type="hidden" name="sumtotalprice" > 
	            <input type="hidden" name="sumtotalpoint" > 
	            <input type="hidden" id="poqty" name="poqty">
	        </form> 
	      </div>
	
		</div>


	 <c:if test="${imgFileList != null && not empty imgFileList}">
		 <div style="width: 98%; margin-top: 3%; margin-left:1%; border: solid 0px blue; ">
			<c:forEach var="imagevo" items="${imgFileList}">
				<div align="center" style="border: solid 0px red; ">
					<img src="img/${imagevo.pimgfilename}" width="100%" height="100%"/>
				</div>
			</c:forEach>
		</div> 
	</c:if> 
	
	<div style="width: 98%; margin-left:1%; margin-top:2%; border: solid 0px green; ">
		<span style="color: white; font-weight: bold; font-size: 14pt; background-color: black;">제품설명</span>
		<p>${pvo.pcontent}</p>
	</div>

	<div style="width: 98%; margin-top: 3%; margin-bottom: 2%; border: solid 0px red;">
		<div style="display: inline-block; padding-left: 7%; border: solid 0px blue; width: 50%; ">
			<img src="<%= request.getContextPath() %>/img/like.png" width="70%" height="100%" style="cursor: pointer;" onClick="goLikeAdd('${pvo.pcode}');" />
			<div style="display: block;">
			<span style="color: blue; font-size: 10pt;" id="likecnt"></span>
		</div>
	</div>
	
	<div style="display: inline-block; margin-left: 2%; border: solid 0px green; width:45%; ">
		<img src="<%= request.getContextPath() %>/img/dislike.png" width="67%" height="100%" style="cursor: pointer;" onClick="goDisLikeAdd('${pvo.pcode}');" />
		<div style="display: block;">
			<span style="color: red; font-size: 10pt;" id="dislikecnt"></span>
		</div>
	</div>


</div>
</div>
<div class="container" style="margin-top: 1%; border: 0px solid green; width: 100%; margin-right: 2%;">
  <h2 align="center" style="font-size: 15pt;">회원 한줄평</h2>          
   <form name="memoaddupdatefrm">
   	   	 <div class="form-group">
		    <label for="comment">Comment:</label>
		    <textarea class="form-control" rows="2" id="comments" name="comments" ></textarea>
	     	<input type="hidden" name="pcode" value="${pvo.pcode}" />
	     	<input type="hidden" name="userid" value=" ${sessionScope.loginuser.userid}" /> 
	     	<input type="hidden" name="num" id="num" value="" />         	  
	   	 </div>
	   	 <button type="button" id="btnadd" onclick="memoaddupdate('1')">저장</button>
	   	 <button type="button" id="btnupdate" onclick="memoaddupdate('2')">수정저장</button>
	   	 <input type="hidden" name="num" value="" />
	   </form>
<div class="table-responsive">
  <table class="table">
    <thead>
		  <tr>
	        <th style="font-size: 10pt;">아이디</th>
	        <th style="font-size: 10pt;">한줄평</th>
	        <th style="font-size: 9pt;">수정/삭제</th>
	      </tr>
   	    <thead/>
    <tbody id="displayResult">
     
    </tbody>
  </table>
</div>
</div>
	

<div style="margin-top: 1%;  padding-left: 15%; border: 0px solid green; margin-bottom: 2%;">
	<button type="button" id="btnMoreMemo" value="">더보기...</button>
	<span id="totalMemoCount">${totalMemoCount}</span>
	<span id="countMemo">0</span>
	<c:if test="${sessionScope.loginuser != null}" > 
		<button type="button" id="btnwriteMemo" onclick="commentadd()">한줄평 작성하기</button>
	</c:if>
</div>


<jsp:include page="../../footer.jsp" />
    