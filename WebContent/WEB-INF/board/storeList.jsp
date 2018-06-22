<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../header.jsp" />
 
<div class="content-wrapper">
    <div class="container-fluid">
<form id="form_id_search" action="searchStoreList.do" method="post">
	<input id="page" name="page" type="hidden" value="1"/>
	<input id="shopTypeCd" name="shopTypeCd" type="hidden" value=""/>
	<input id="storeAreaCd" name="storeAreaCd" type="hidden" value=""/>
</form>
<form name = "frm1">
<!-- container// -->
        <section id="container">
          <h3 align="center"> ABOUT > STORE </h3>
          <hr style="border: solid 1px black; margin-bottom: 0px; padding: 0px;" >
            <div id="about">
                <div class="select_wrap">
                    <div>
                        <select style="width: 90%; font-size: 10pt;" id="filterShopTypeCd" data-filter-type="shop">
                            <option value="매장전체" selected="selected">매장전체</option>
                            <option value="직영점" >직영점</option>
                            <option value="백화점" >백화점</option>
                            <option value="아울렛" >아울렛</option>
                        </select>
                    </div>
                    <div>
                        <select style="width: 90%; font-size: 10pt;" id="filterStoreAreaCd" data-filter-type="area">
                            <option value="지역전체" selected="selected">지역전체</option>
                            <option value="서울" >서울</option>
                            <option value="부산" >부산</option>
                            <option value="대구" >대구</option>
                            <option value="대전" >대전</option>
                            <option value="인천" >인천</option>
                            <option value="경기" >경기</option>
                            <option value="광주" >광주</option>
                            <option value="울산" >울산</option>
                            <option value="강원" >강원</option>
                            <option value="충청" >충청</option>
                            <option value="전라" >전라</option>
                            <option value="경상" >경상</option>
                            <option value="제주" >제주</option>
                            </select>
                    </div>
                </div>

                <div class="store_list inner">
                    <ul id="ulList">
                    	<div id="selectedStoreList"></div>
                    </ul>
                     <input type="hidden" name="storeno" id="storeno" />
                    <div>
                    	<button type="button" id="btnMore" value="" class="btn btn-primary btn-dark" style="width: 100%; margin-bottom: 20px;">MORE</button>
						<span id="totalStoreCount">${totalStoreCount}</span>
						<span id="countStore">0</span>
					</div>
                </div>
            </div>
        </section>
        <!-- //container -->
 
</form>
</div>
</div>  

<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

            $(document).ready(function(){

            	$("#totalStoreCount").hide();
        		$("#countStore").hide(); 
        		
        		// NEW상품 게시물을 더보기 위하여 더보기.. 버튼 클릭액션에 대한 초기값 호출
        		displayStoreAppend("1");
        		
        		// NEW상품 더보기.. 버튼 클릭 이벤트 등록
        		$("#btnMore").bind("click", function() {
        			
        			if( $(this).text() == "BACK" ) {
        				$("#selectedStoreList").empty();
        				displayStoreAppend("1");
        				$(this).text("MORE");
        			}
        			else {
        				displayStoreAppend($(this).val());
        			}
        		});


             // 매장구분, 지역구분 필터 클릭
             $('#filterShopTypeCd, #filterStoreAreaCd').on('change', function() {
                 
             	var form_data = {shopTypeCd : $("#filterShopTypeCd").val(),
             			         storeAreaCd : $("#filterStoreAreaCd").val()};
             	
             	$.ajax({
             		url: "searchStoreListJSON.do",
             		type: "GET",
             		data: form_data,
             		dataType: "JSON",
             		
             		success: function(json){
 					    // json 는 ajax 요청에 의해 URL 페이지로 부터 리턴받은 데이터(JSONObject 형태)이다.
 					    
             			$("#selectedStoreList").empty();
 						
             			var html = "";
             			
             			if(json.length < 5){
             				 $('#btnMore').hide();                				
             			}
             			else {
             				 $('#btnMore').show();
             			}
             			
             			if(json != null && json.length > 0) {
     						
     						$.each(json, function(entryIndex, entry){
	
     							html += "<li>"
     								 +  "<a onclick='showMap("+entry.storeno+");'>"
     								 +  "<span>"+entry.storeName+" </span>"
     								 +  "<span>"+entry.addr+"</span>"
     								 +  "<span>"+entry.tel+" / "+entry.sopen+" ~ "+entry.sclose+"</span>"
     								 +  "<div>"
     								 +  "<span>"+entry.storectg+"</span>"
     								 +  "</div>"
     								 +  "</a>"
     								 +  "</li>";
     							
     						});
     						
     						$("#selectedStoreList").append(html);
    				    	
    				    	// >>>>> !!!! 중요 !!!! 더보기 버튼의 value 속성에 값을 지정해주기 <<<<<<
    				    	$("#btnMore").val(parseInt(start) + lenSTORE);
    				    	/*
    				    	    문서가 로딩되어지면 parseInt(start) 은 1이고
    				    	  lenSTORE는 5이므로 더보기.. 버튼의 value값은 6가 되어진다. 
    				    	*/
    				    	
    				    	// *** 웹브라우저상에 count 출력하기
    				    	$("#countStore").text(parseInt($("#countStore").text()) + json.length);
    				    
    				    	// *** 더보기... 버튼을 계속해서 눌러서 countStore 와  totalHITCount 가 일치하는 경우 
    				    	//     버튼의 이름을 "처음으로" 라고 변경하고 countStore 는 0 으로 초기화한다. 
    				    	if( $("#totalStoreCount").text() == $("#countStore").text() ) {
    				    		$("#btnMore").text("BACK");
    				    		$("#countStore").text("0");
    				    	}
     						
     					}// end of if()------------------------------------------------------------------
     					else {
     						
     						html += "<li>"
						         +  "<span>해당 지역에는 매장이 없습니다.</span>"								 
						         +  "</li>";
     						
     					}// end of else()----------------------------------------------------------------------
     					
     					$("#selectedStoreList").html(html);
     					
 					}, // end of success: function(data){}------------------------
 					error: function(request, status, error){
 						alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
 					}
             	})
             	
             });

             
         });// end of ready()-------------------------------------
            
            function showMap(storeno) {
            	
            	$("#storeno").val(storeno);
    
            	var frm = document.frm1;
            	frm.method = "get";
        	    frm.action = "searchStoreMap.do";
        	    frm.submit(); 
            	
            };// end of showMap(storeno)--------------------------------
            
            
        	
        	function displayStoreAppend(start) {
            	
        		var lenSTORE = 5; // 더보기 클릭시 더 보여줄 매장 수
        	
        	    var form_data = {"start" : start,
        		                 "len" : lenSTORE};
        	
        		$.ajax({
        			url: "searchStoreListEndJSON.do",
        			type: "GET",
        			data: form_data,
        			dataType: "JSON",
        			success: function(json){
        				 var html = "";
        				 
        				 if(json.length == 0) {
        				   // 데이터가 아예 없는 경우이라면 
        				   	html += "매장 오픈 준비 중 입니다...";
        				    	
        				    $("#selectedStoreList").html(html);
        				    	
        				   // 더보기 버튼의 비활성화 처리
        				   	$("#btnMore").attr("disabled", true);
        				    $("#btnMore").css("cursor","not-allowed");
        				    	      
        				 }
        				 else {
        				    	// 데이터가 존재하는 경우이라면
        				    	$.each(json, function(entryIndex, entry){

        				    		html += "<li>"
       								 +  "<a onclick='showMap("+entry.storeno+");'>"
       								 +  "<span>"+entry.storeName+" </span>"
       								 +  "<span>"+entry.addr+"</span>"
       								 +  "<span>"+entry.tel+" / "+entry.sopen+" ~ "+entry.sclose+"</span>"
       								 +  "<div>"
       								 +  "<span>"+entry.storectg+"</span>"
       								 +  "</div>"
       								 +  "</a>"
       								 +  "</li>";	
        					             
        				    	});// end of $.each()------------------------------
        				    	
        				    	
        				    	$("#selectedStoreList").append(html);
        				    	
        				    	// >>>>> !!!! 중요 !!!! 더보기 버튼의 value 속성에 값을 지정해주기 <<<<<<
        				    	$("#btnMore").val(parseInt(start) + lenSTORE);
        				    	/*
        				    	    문서가 로딩되어지면 parseInt(start) 은 1이고
        				    	  lenSTORE는 5이므로 더보기.. 버튼의 value값은 6가 되어진다. 
        				    	*/
        				    	
        				    	// *** 웹브라우저상에 count 출력하기
        				    	$("#countStore").text(parseInt($("#countStore").text()) + json.length);
        				    
        				    	// *** 더보기... 버튼을 계속해서 눌러서 countStore 와  totalHITCount 가 일치하는 경우 
        				    	//     버튼의 이름을 "처음으로" 라고 변경하고 countStore 는 0 으로 초기화한다. 
        				    	if( $("#totalStoreCount").text() == $("#countStore").text() ) {
        				    		$("#btnMore").text("BACK");
        				    		$("#countStore").text("0");
        				    	}
        				    	
        				    }// end of if~else----------------------
        					
        			},// end of success: function(json)----------------
        			error: function(request, status, error){
        			   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        			}
        		});
        		
        	}// end of function displayNewAppend(start)------	
            
        </script>

<jsp:include page="../../footer.jsp" />
