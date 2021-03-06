<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<jsp:include page="../../header.jsp" />

	 <style type="text/css">
	
		a:hover {
			text-decoration: none;
		}
	
	</style>
	
	<script src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>

	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			$("#totalCount").hide();
			$("#count").hide();
			
			// 상품 게시물을 더보기 위하여 더보기.. 버튼 클릭액션에 대한 초기값 호출
			displayAppend("1");
			
			// 상품 더보기.. 버튼 클릭 이벤트 등록
			$("#btnMore").bind("click", function() {
				
				if( $(this).text() == "처음으로" ) {
					$("#displayResult").empty();
					displayAppend("1", $(':radio[name="sort"]:checked').val());
					$(this).text("더보기...");
				}
				else {
					displayAppend($(this).val(), $(':radio[name="sort"]:checked').val(), $("#category").val());
				}
				
			});
			
			$("#new").bind("click", function() {
				$("#displayResult").empty();
				displayAppend("1", "new");
				$("#btnMore").show();
			});
			
			$("#high").bind("click", function() {
				$("#displayResult").empty();
				displayAppend("1", "high");
				$("#btnMore").show();
			});
			
			$("#low").bind("click", function() {
				$("#displayResult").empty();
				displayAppend("1", "low");
				$("#btnMore").show();
			});
			
		});
		
		function goCategory(category) {
			
			$("#category").val(category);
			$("#displayResult").empty();
			displayAppend("1", $(':radio[name="sort"]:checked').val(), category);
			$("#btnMore").show();
			
		}
		
		function displayAppend(start, sort, category) {
			
			var len = 3;
			var category = $("#category").val();
		    var form_data = {"sort" : sort,
		    				 "category" : category,
							 "start" : start,
			                 "len" : len};
		
			$.ajax({
				url: "hitlistJSON.do",
				type: "GET",
				data: form_data,
				dataType: "JSON",
				success: function(json) {
					
					var html = "";
					    
					if(json.length == 0) {
					    // 데이터가 아예 없는 경우 
						html += "상품준비중 입니다...";
						$("#btnMore").hide();
					}
					
					else {
						// 데이터가 존재하는 경우
						$.each(json, function(entryIndex, entry) {
							html += " <div align=\"center\"  style=\"display: inline-block; width:100%; \"> " 
							     +  "	<br/> "
							     +  "	  <a href='/semiProject/prodView.do?pcode="+entry.pcode+"'> "
							     +  "		<img width=\"50%;\" src=\"<%= request.getContextPath() %>/img/"+entry.pimage+"\"> " 
						         +  "	</a> "
						         +  "	<br/><span style=\"font-size:10pt; font-family:나눔고딕; font-weight: bold;\">"+entry.pname+"</span>"
						         +  "   <br/><img width='35%' src='img/star/star" + entry.starpoint + ".png'> " 
						         +  "	<br/><span style=\"font-size:10pt; font-family:나눔고딕;\"><del>"+entry.price+"원</del></span>  "
						         +  " 	<span style=\"font-size:10pt; font-family:나눔고딕; font-weight: bold;\">"+entry.saleprice+"원</span> "   
						         +  "	<br/><span style=\"font-size:10pt; font-family:나눔고딕; orange;\">"+entry.point+" point</span> "
					             +  " </div> "
					             +  "<hr/>";
					             
					             
				    	});// end of $.each()------------------------------
				    	
				    	html += "<div style=\"clear: both;\">&nbsp;</div>";
				    	
				    	// 상품 결과 출력
				    	$("#displayResult").append(html);
				    	
				    	// >>>>> !!!! 중요 !!!! 더보기 버튼의 value 속성에 값을 지정해주기 <<<<<<
				    	$("#btnMore").val(parseInt(start) + len);
				    	/*
				    	    문서가 로딩되어지면 parseInt(start) 은 1이고
				    	  lenHIT는 3이므로 더보기.. 버튼의 value값은 4가 되어진다. 
				    	*/
				    	
				    	// *** 웹브라우저상에 count 출력하기
				    	$("#count").text(parseInt($("#count").text()) + json.length);
				    	
				    	// *** 더보기... 버튼을 계속해서 눌러서 countHIT 와  totalHITCount 가 일치하는 경우 
				    	//     버튼의 이름을 "처음으로" 라고 변경하고 countHIT 는 0 으로 초기화한다. 
				    	if( $("#totalCount").text() == $("#count").text() ) {
				    		$("#btnMore").text("처음으로");
				    		$("#count").text("0");
				    	}
				    	
				    }// end of if~else----------------------
						
				},
				error: function(request, status, error){
				   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			
		}// end of function displayAppend(start)------
	
	</script>

	<div class="container">
		
		<div align="center">
			<div class="btn-group btn-group-justified" id="sortbutton" data-toggle="buttons">
				<label class="btn btn-primary active" id="new" style="background-color: #404040; border-color: #404040;">
					<input type="radio" style="visibility: hidden;" autocomplete="off" name="sort" id="new" value="new" checked />
					<span style="font-size: 10pt; font-family: 나눔고딕;">최신순</span>
				</label>
				<label class="btn btn-primary" id="high" style="background-color: #404040; border-color: #404040;">
					<input type="radio" style="visibility: hidden;"  autocomplete="off" name="sort" id="high" value="high" />
					<span style="font-size: 10pt; font-family: 나눔고딕;">높은가격순</span> 
				</label>
				<label class="btn btn-primary" id="low" style="background-color: #404040; border-color: #404040;">
					<input type="radio" style="visibility: hidden;" autocomplete="off" name="sort" id="low" value="low" />
					<span style="font-size: 10pt; font-family: 나눔고딕;">낮은가격순&nbsp;&nbsp;</span> 
				</label>
			</div>
		</div>
		
			
		<form name="mainlistfrm">
			<input type="hidden" id="sort" />
			<input type="hidden" id="category" name="category" value="${category}" />
		</form>
		
		<!-- 상품 디스플레이 -->
		<div style="width: 100%; margin-top: 50px; margin-bottom: 30px;">
		
			
				
			<div id="displayResult" style="margin: auto; border: solid 0px red;" align="center"></div>
				
			<div style="margin-top: 3%; margin-bottom: 3%;">
				<button type="button" id="btnMore" class="btn btn-primary btn-block btn btn-dark" value="" >MORE</button>
				<span id="totalCount">${totalCount}</span>
				<span id="count">0</span>
			</div>
		
		</div>
	
	</div>

<jsp:include page="../../footer.jsp" />