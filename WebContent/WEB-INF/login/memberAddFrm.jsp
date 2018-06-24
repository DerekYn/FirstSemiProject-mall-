<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../../header.jsp" />

<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<style type="text/css">

	div .card-header{ font-size: 12pt; font-family: 나눔고딕; font-weight: bold;}
	
	div .card-body { font-size: 12pt; font-family: 나눔고딕; }

	.form-control{ font-size: 10pt; font-family: 나눔고딕;  }
	
	
</style>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		$("#idcheck").click(function(){
			var userid = $("#userid").val().trim();
			
			
			if(userid == "" || userid.length < 6 || userid.length > 12) {
			//	alert("아이디는 6글자 이상 12글자 이하이여야 합니다.");
			
				$("#checkResult").html("");	
				$("#idErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>아이디는 6글자 이상 12글자 이하이여야 합니다.</span>");
				$("#idErr").show();
				$("#userid").focus();
				return;
				
			}
			
			var regexp_id= new RegExp(/^[a-zA-Z0-9]{4,12}$/); 

			var bool = regexp_id.test(userid);
			
			if(!bool){
			//	alert("아이디는 영문자로만 입력가능합니다.");
				$("#checkResult").html("");	
				$("#idErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>아이디는 영문자와 숫자로만 입력 가능합니다.</span>");
				$("#idErr").show();
				$("#userid").focus();
				return;
			}
			
			
			var form_data = {"userid" : $("#userid").val()};
			
			$.ajax({
				
				url: "idCheckJSON.do",
				type: "GET",
				data: form_data,
				dataType: "JSON",
				success: function(json){
					
					var html = "";
					var result = json.result;
					
					if(result == 1){
						html = "<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>이미 사용중인 아이디 입니다 !!</span>";
					}else {
						html = "<span style='color: blue; font-size: 10pt; font-family: 나눔고딕;'>사용 가능한 아이디입니다</span>";
					}
					$("#idErr").html("");
					$("#chkResult").val(result);
					$("#checkResult").html(html);	
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
				
			});// end of $.ajax()-------------------------------
			
		});// end of $("#idcheck").click()--------------------
		
		
	});// end of $(document).ready()---------------------------------
		
	var element_layer = document.getElementById('layer');

    function DaumPostcode() {
    	new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 도로명 조합형 주소 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
                // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
                if(fullRoadAddr !== ''){
                    fullRoadAddr += extraRoadAddr;
                }
                var postcode = data.postcode;
                var postcode1 = data.postcode1;
                var postcode2 = data.postcode2;
                
                if(postcode == ""){
					// 예전  postcode가 없는 경우
					postcode1 = data.zonecode.substr(0,3)
					postcode2 = data.zonecode.substr(3);
					
                }
                
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('post1').value = postcode1+postcode2;
                document.getElementById('post2').value = postcode1+"-"+postcode2; 
                document.getElementById('addr1').value = fullRoadAddr;
                $("#postChk").val("1");
				$("#addr2").focus();

                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    //예상되는 도로명 주소에 조합형 주소를 추가한다.
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    document.getElementById('guide').innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    document.getElementById('guide').innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';

                } else {
                    document.getElementById('guide').innerHTML = '';
                }
            }
        }).open();

    }

	// 값보내기전 확인
	function goSubmit() {
		
		var chkResult = $("#chkResult").val().trim();
		
		if(chkResult == "") {
		//	alert("아이디 중복검사를 눌러주세요");
			$("#idErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>아이디 중복검사를 눌러주세요</span>");
			$("#idErr").show();
			$("#userid").focus();
			return;
		}
		else if($("#chkResult").val() == 1){
		//	alert("이미 사용중인 아이디입니다");
			$("#idErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>이미 사용중인 아이디입니다</span>");
			$("#idErr").show();
			$("#idErr").focus();
			return;
		}else {
			$("#idErr").hide();
		}
			
		
		var password = $("#password1").val().trim();
		
		var regexp_passwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
		// 암호는 영문자,숫자,특수문자가 포함된 형태의 8~15글자 이하만 허락해주는 정규표현식 객체 생성.
		
		var bool = regexp_passwd.test(password);
		// 암호 정규표현식 검사를 하는 것. 리턴값은 boolean타입이며 정규표현식에 만족하면 true, 아니면 false
		
		if(!bool) {
			// 정규식에 만족하지 못한경우
		//	alert("패스워드 형식에 맞게 입력하세요");
			$("#passErr1").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>암호는 영문자,숫자,특수문자가 포함된 형태의 8~15글자 이하만 가능합니다</span>");
			$("#passErr1").show();
			$("#password1").focus();
			return;
		}else {
			$("#passErr1").hide();
		}
		
		var passwdChk = $("#password2").val().trim();
		
		if(password != passwdChk){
		//	alert("비밀번호가 일치하지 않습니다.");
			$("#passErr2").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>비밀번호가 일치하지 않습니다.</span>");
			$("#passErr2").show();
			$("#password2").focus();
			return;
		}else {
			$("#passErr2").hide();
		}
		
		var name= $("#name").val().trim();
		
		if(name == ""){
		//	alert("이름을 입력해주세요");
			$("#nameErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>이름을 입력해주세요</span>");
			$("#nameErr").show();
			$("#name").focus();
			return;
		}else{
			$("#nameErr").hide();
		}
		
		
		var email = $("#email").val();
		var regexp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		bool = regexp_email.test(email);
		
		if(!bool){
		//	alert("이메일 형식이 아닙니다.");
			$("#emailErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>이메일 형식이 아닙니다.</span>");
			$("#emailErr").show();
			$("#email").focus();
			return;
		}else{
			$("#emailErr").hide();
		}
		
		var postChk = $("#postChk").val();
		if(postChk != 1){
			$("#addrErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>주소찾기로 검색해주세요.</span>");
			$("#addrErr").show();
			$("#addr2").focus();
			return;
		} else {
			$("#addrErr").hide();
		}
		
		var hp2 = $("#hp2").val();
		var regexp_hp2 = /\d{3,4}/g;
		bool = regexp_hp2.test(hp2);
		
		if(!bool){
		//	alert("숫자로만 입력해주세요1.");
			$("#phoneErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>숫자로만 입력해주세요.</span>");
			$("#phoneErr").show();
			$("#hp2").focus();
			return;
		}
		
		var hp3 = $("#hp3").val();
		var regexp_hp3 = /\d{3,4}/g;
		bool = regexp_hp3.test(hp3);
		
		if(!bool){
		//	alert("숫자로만 입력해주세요2.");
			$("#phoneErr").html("<span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>숫자로만 입력해주세요.</span>");
			$("#phoneErr").show();
			$("#hp3").focus();
			return;
		}
		
		
		var frm = document.frm;
		frm.method = "post";
		frm.action = "memberAdd.do";
		frm.submit();
	}
	
</script>

    <div class="card card-register mx-auto mt-5">
      <div class="card-header"><h4>회원가입 <small>(<span style='color: red;'>*</span>는 필수입력)</small></h4></div>
      <div class="card-body">
        <form name="frm">
          <div class="form-group">
            <div class="form-row">
              <div class="col-md-12">
                <label for="userid"><span style='color: red; '>*</span>아이디: </label>
                <div class="input-group">
	              <input type="text" class="form-control" id="userid" name="userid" placeholder="6 ~ 12 자 이내의 영문자"/>
	              <span class="input-group-append">
	                 <button type="button" class="btn btn-primary btn-dark" id="idcheck">
	                   <i class="fa fa-search"></i><span style="font-size: 10pt; font-family: 나눔고딕;">중복검사</span>
	                 </button>
                  </span>
                </div>
               	
                <input type="hidden" class="form-control" id="chkResult"/>
                <div id="idErr"></div>
          	    <div id="checkResult"></div>
           	   
              </div>
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword1"><span style='color: red;'>*</span>비밀번호: </label>
              <input type="password" class="form-control" id="password1" name="password" placeholder="영문자, 숫자, 특수문자를 혼합한 8글자 이상 15글자 이하의 조합"/>
              <div id="passErr1"></div>
          </div>
          <div class="form-group">
            <label for="password2"><span style='color: red;'>*</span>비밀번호 확인:</label>
            <input type="password" class="form-control" id="password2"/>
            <div id="passErr2"></div>
          </div>
          <div class="form-group">
            <label for="name"><span style='color: red;'>*</span>이름:</label>
            <input type="text" class="form-control" id="name" name="name"/>
            <div id="nameErr"></div>
          </div>
          <div class="form-group">
            <label for="email"><span style='color: red;'>*</span>이메일 주소:</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="abc@def.com"/>
            <div id="emailErr"></div>
          </div>
          <div class="form-group">
            <label for="useraddr"><span style='color: red;'>*</span>우편번호:</label>
            <input type="text" class="form-control" id="post1" placeholder="우편번호"/>
           	<input type="hidden" class="form-control" id="post2" name="post"/>
           	<input type="text" class="form-control" id="addr1" name="addr1" placeholder="도로명 또는 지번 주소"/>
           	<input type="text" class="form-control" id="addr2" name="addr2" placeholder="상세주소" />
           	<span id="guide" style="color:#999"></span>
           	
      		<button type="button" style="margin-top: 10px;" class="btn btn-primary btn-dark" id="addrSearch" onclick="DaumPostcode();">
      			<span style="font-size: 10pt; font-family: 나눔고딕;">주소찾기</span>
      		</button>
           	
           	<input type="hidden" class="form-control" id="postChk"/>              
            <div id="addrErr"></div>
          </div>
          <div class="form-group">
          	<label for="phone"><span style='color: red; font-size: 10pt; font-family: 나눔고딕;'>*</span>핸드폰: </label>
          	<div class="form-row">
          	  <div class="col-md-4">
          	    <input type="text" class="form-control" id="hp1" name="hp1"  value="010" readonly  style="text-align: center;" />
          	  </div>
          	  <div class="col-md-4">
          	    <input type="text" class="form-control" id="hp2" name="hp2" maxlength="4" style="text-align: center;"/>
          	  </div>
          	  <div class="col-md-4">
          	    <input type="text" class="form-control" id="hp3" name="hp3" maxlength="4" style="text-align: center;"/>
          	  </div>
              <div id="phoneErr"></div>
          	</div>
           
          </div>
          <div class="form-group" align="center">
     	     <button type="button" class="btn btn-primary btn-dark" onclick="goSubmit();">
     	     	<span style="font-size: 10pt; font-family: 나눔고딕;">회원가입</span></button>
     	     <button type="button" class="btn btn-primary btn-dark" onclick="javascript:history.back();" >
     	   		<span style="font-size: 10pt; font-family: 나눔고딕;">가입취소</span>
     	   	</button>
          </div>
          
        </form>
      </div>
    </div>
  


<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>


<jsp:include page="../../footer.jsp" />
