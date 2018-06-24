<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../header.jsp" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    
    

    
	<style type ="text/css">
	
	@import url("http://fonts.googleapis.com/earlyaccess/nanumgothic.css");
	
	html {
		height: 100%;
	}
	
	body {
	    width:100%;
	    height:100%;
	    margin: 0;
  		padding-top: 80px;
  		padding-bottom: 40px;
  		font-family: "Nanum Gothic", arial, helvetica, sans-serif;
  		/* background-repeat: no-repeat;
  		background:linear-gradient(to bottom right, #0098FF, #6BA8D1);  */
	}
	
    .card {
        margin: 0 auto; /* Added */
        float: none; /* Added */
        margin-bottom: 10px; /* Added */
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
	}
	
	.form-signin .form-control {
  		position: relative;
  		height: auto;
  		-webkit-box-sizing: border-box;
     	-moz-box-sizing: border-box;
        	 box-sizing: border-box;
  		padding: 10px;
  		font-size: 16px;
	}
	
	</style>
	
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>	
<script type="text/javascript">
	
	$(document).ready(function(){
		
		
		$("#upw").keydown(function(event){
			if(event.keyCode == 13) { // 엔터를 했을 경우
			   goLogin();
			}// end of if------------------------------
		});// end of $("#loginPwd").keydown()----------
		
		
		$(".myclose").click(function(){
			// alert("닫는다.");
			
			javascript:history.go(0);
			
		});
		
});// end of $(document).ready()---------------


function goLogin() { // 로그인을 사용한 함수
	
	//	alert("goLogin");
	
	var frm = document.loginFrm;
	frm.method = "post";
	frm.action = "loginEnd.do";
	frm.submit();
	
	
}// end of goLogin()---------------


/* 
function goEditPersonal(idx) {
	
	// 팝업창 띄우기
	var url="personalEdit.do?idx="+idx;
	
	window.open(url, "personalEdit",
			    "left=350px, top=100px, width=650px, height=570px"); } */
			    




	</script>
	
	<%-- 
<%
   String goBackURL = util.my.MyUtil.getCurrentURL(request);
   
   // WEB-INF/memo/memoListJSTLVO.jsp?currentShowPageNo=15&sizePerPage=3

   // http://localhost:9090/MyMVC/memoVOList.do?currentShowPageNo=15&sizePerPage=3
	
   int index = goBackURL.indexOf("?");
   
   String addURL = "";
   
   if(index > 0) { // GET 방식이라면
	    addURL = goBackURL.substring(index);
     // ?currentShowPageNo=15&sizePerPage=3
    		 
	    goBackURL = goBackURL.substring(0, index);
     // WEB-INF/memo/memoListJSTLVO.jsp
   }
  
   // 아래는 GET 방식 , POST 방식 모두 적용되는 것임.
   int lastindex = goBackURL.lastIndexOf("/");
   goBackURL = goBackURL.substring(lastindex+1);
// memoListJSTLVO.jsp 
%>        --%>
  	<script type="text/javascript">
        <%-- alert("<%=goBackURL%>");  --%>
    </script>

	
<%-- <%
	UserinfoVO userinfovo = (UserinfoVO)session.getAttribute("loginuser");

    if(userinfovo == null) {
    	
    /*
            로그인 하려고 할때 WAS(톰캣서버)는 
            사용자 컴퓨터 웹브라우저로 부터 전송받은 쿠키를 검사해서
            그 쿠키의 사용유효시간이 0초 짜리가 아니라면
            그 쿠키를 가져와서 웹브라우저에 적용시키도록 해준다.
            우리는 쿠키의 키 값이 "saveid" 가 있으면
            로그인 ID 텍스트박스에 아이디 값을 자동적으로 올려주면 된다.
    */
    
    Cookie[] cookieArr = request.getCookies();
    /*
            쿠키는 쿠키의 이름별로 여러개 저장되어 있으므로
            쿠키를 가져올때는 배열타입으로 가져와서
            가져온 쿠키배열에서 개발자가 원하는 쿠키의 이름과 일치하는것을
            뽑기 위해서는 쿠키 이름을 하나하나씩 비교하는 것 밖에 없다.
    */
    
    String cookie_key = "";
    String cookie_value = "";
    boolean flag = false;
    
    if(cookieArr != null) {
    	for(Cookie c : cookieArr) {
    		cookie_key = c.getName(); // 쿠키의 이름(키값)을 꺼내오는 메소드.
    		
    		if(cookie_key.equals("saveid")) {
    			cookie_value = c.getValue(); // 쿠키의 value값 을 꺼내오는 메소드.
    			flag = true;
    			break; // for 탈출
    		}
    	}// end of for-----------------
    }
%>
 --%>	

	<div class="card card-register mx-auto mt-5" style="width:20rem; border-radius:20px;">
		<div class="card-title" style="margin-top:30px;">
			<h2 class="card-title text-center" style="color:#113366;">Login</h2>
		</div>
		<div class="card-body">
	      <form name="loginFrm" class="form-signin" method="POST" onSubmit="logincall();return false">
	        <h5 class="form-signin-heading">로그인 정보를 입력하세요</h5>
	        
	        
	        <label for="inputEmail" class="sr-only">Your ID</label>
	        <input type="text" id="uid" name="userid" class="form-control" placeholder="Your ID" required autofocus><BR>
	        <label for="inputPassword" class="sr-only">Password</label>
	        <input type="password" id="upw" name="password" class="form-control" placeholder="Password" required><br>
	        <div>
			  <tr>
			     <td colspan="3" align="center"> 
			  	   <a style="cursor: pointer;" data-toggle="modal" data-target="#userIdfind" data-dismiss="modal">아이디찾기</a> /
			 	   <a style="cursor: pointer;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal">비밀번호찾기</a>
			 	   <br/><br/>
				   <a href="memberFrm.do"><h5>회원가입</h5></button></a><br/>
		         </td>
			  </tr>
	        </div>
	        <br/>
		   <button id="btn-Yes" class="btn btn-lg btn-primary btn-block btn-dark" onclick="goLogin();">로 그 인</button>
	      </form>
		</div>
	</div>


	<!-- <div class="modal">
	 -->
	<%-- ****** 아이디 찾기 Modal ****** --%>
  <div class="modal fade" id="userIdfind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">아이디 찾기</h4>
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body" style="height: 300px; width: 100%;">
          <div id="idFind">
          	<iframe style="border: none; width: 100%; height: 280px;" src="<%= request.getContextPath() %>/idFind.do">
          	</iframe>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>   


  <%-- ****** 비밀번호 찾기 Modal ****** --%>
  <div class="modal fade" id="passwdFind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">비밀번호 찾기</h4>
           <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <div id="pwFind">
          	<iframe style="border: none; width: 100%; height: 350px;" src="<%= request.getContextPath() %>/pwdFind.do">  
          	</iframe> 
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
	</div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script> 
  
  
  
  
<jsp:include page="../../footer.jsp" />


  
        
        
           
           
            
            
           
       
  