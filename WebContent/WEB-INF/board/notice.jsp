<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<jsp:include page="../../header.jsp" />
    
<div class="content-wrapper">
    <div class="container-fluid">
    
    <h3 align="center"> ABOUT > NOTICE </h3>
          <hr style="border: solid 1px black; margin-bottom: 30px; padding: 0px;" >
    	<form name="form_notice">
    	<div class="notice_list inner">
             <ul id="ulList">
             <div id="noticeList" role="tablist"></div>
              </ul>
              <input type="hidden" name="noticeno" id="noticeno" />
              <input type="hidden" name="noticename" id="noticename" />
              <input type="hidden" name="noticeContent" id="noticeContent" />
              <div>
              	<button type="button" id="btnMore" value="" style="margin-bottom: 20px;" class="btn btn-primary btn-block btn-dark">MORE</button>
              	<a style="color: white" id="writeNotice" class="btn btn-primary" href="noticeWrite.do">새 공지 쓰기</a>&nbsp;&nbsp;
				<span id="totalNoticeCount">${totalNoticeCount}</span>
				<span id="countNotice">0</span>
			  </div>
         </div>
		</form>
	</div>
</div>	


<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">

            $(document).ready(function(){

            	$("#totalNoticeCount").hide();
        		$("#countNotice").hide(); 
        		$("#writeNotice").hide();


        		displayNoticeAppend("1");


        		$("#btnMore").bind("click", function() {
        			
        			if( $(this).text() == "BACK" ) {
        				$("#noticeList").empty();
        				displayNoticeAppend("1");
        				$(this).text("MORE");
        			}
        			else {
        				displayNoticeAppend($(this).val());
        			}
        		});
        		
        		if('admin' == "${sessionScope.loginuser.userid}"){
        			$("#writeNotice").show();
        		}
        		
				
        		
        		
             
         });// end of ready()-------------------------------------

            
            var lenNOTICE = 5; // 더보기 클릭시 더 보여줄 매장 수
        	
        	function displayNoticeAppend(start) {
        	
        	    var form_data = {"start" : start,
        		                 "len" : lenNOTICE};
        	
        		$.ajax({
        			url: "noticeListEndJSON.do",
        			type: "GET",
        			data: form_data,
        			dataType: "JSON",
        			success: function(json){
        				 var html = "";
        				 
        				 
        				 if(json.length == 0) {
        				   // 데이터가 아예 없는 경우이라면 
        				   	html += "공지사항 준비 중 입니다...";
        				    	
        				    $("#noticeList").html(html);
        				    	
        				   // 더보기 버튼의 비활성화 처리
        				   	$("#btnMore").attr("disabled", true);
        				    $("#btnMore").css("cursor","not-allowed");
        				    	      
        				 }
        				 else {
        				    	// 데이터가 존재하는 경우이라면
        				    	$.each(json, function(entryIndex, entry){

        				    		html += "<li style='margin=10px'>"
        				    		     +  "<div role='tab' name='"+entry.noticeno+"' id='"+entry.noticeno+"'>"
        				    		     +  "<h5 class='notice-title'>"
       								 	 +  "<a data-toggle='collapse' href='#collapse"+entry.noticeno+"' aria-expanded='false' aria-controls='"+entry.noticeno+"'>"
       								 	 +  "<span>"+entry.noticeName+"</span><br/>"
       								 	 +  "<h6 style='text-align: right; font-size:9pt;' >"+entry.noticeDate+"</h6>"
       								     +  "</a>"
       								     +  "</h5>"
       								     +  "</div><hr>"
       								     +  "<div id='collapse"+entry.noticeno+"' class='collapse' role='tabpanel' aria-labelledby='"+entry.noticeno+"' data-parent='#noticeList'>"
       								     +  "<div class='card-body'>"
       								     +  entry.noticeContent+"<br/>"
       								     +  "<c:if test='${sessionScope.loginuser.userid == \"admin\" }'>"
       								     +  "<a style='color: white' id='modifyNotice' class='btn btn-primary' onClick='modifynotice("+entry.noticeno+",\""+entry.noticeName+"\",\""+ entry.noticeContent+" \")'>수정</a>&nbsp;&nbsp;"
       								     +  "<a style='color: white' id='deleteNotice' class='btn btn-primary' onClick='deletenotice("+entry.noticeno+")'>삭제</a>&nbsp;&nbsp;"
       								     +  "</c:if>"
	       								 +  "</div><hr>"
	       								 +  "</div>"
	       								 +  "</div>";
       				        					             
        				    	});// end of $.each()------------------------------
        				    	
        				    	
        				    	$("#noticeList").append(html);
        				    	
        				    	// >>>>> !!!! 중요 !!!! 더보기 버튼의 value 속성에 값을 지정해주기 <<<<<<
        				    	$("#btnMore").val(parseInt(start) + lenNOTICE);
        				    	/*
        				    	    문서가 로딩되어지면 parseInt(start) 은 1이고
        				    	  lenSTORE는 5이므로 더보기.. 버튼의 value값은 6가 되어진다. 
        				    	*/
        				    	
        				    	// *** 웹브라우저상에 count 출력하기
        				    	$("#countNotice").text(parseInt($("#countNotice").text()) + json.length);
        				    	
        				    	// *** 더보기... 버튼을 계속해서 눌러서 countStore 와  totalHITCount 가 일치하는 경우 
        				    	//     버튼의 이름을 "처음으로" 라고 변경하고 countStore 는 0 으로 초기화한다. 
        				    	if( $("#totalNoticeCount").text() == $("#countNotice").text() ) {
        				    		$("#btnMore").text("BACK");
        				    		$("#countNotice").text("0");
        				    	}
        				    	
        				    }// end of if~else----------------------
        					
        			},// end of success: function(json)----------------
        			error: function(request, status, error){
        			   alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        			}
        		});
        		
        	}// end of function displayNewAppend(start)------	
        	
        	function deletenotice(noticeno){
        		
        		var frm = document.form_notice;
        		
        		frm.noticeno.value = noticeno;
        		frm.method="post";
        		frm.action="noticeDelete.do";
        		frm.submit();
        		
        	};
        	
			function modifynotice(noticeno, noticename, noticeContent){
        		
        		var frm = document.form_notice;
        		
        		
        		frm.noticeno.value = noticeno;
        		frm.noticename.value = noticename;
        		frm.noticeContent.value = noticeContent;
        		
        		frm.method="post";
        		frm.action="noticeModify.do";
        		frm.submit();
        		
        	};
        	
        	
            
        </script>


<jsp:include page="../../footer.jsp" />
