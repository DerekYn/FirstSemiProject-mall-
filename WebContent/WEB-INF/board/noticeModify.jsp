<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../../header.jsp" />

<div class="content-wrapper">
    <div class="container-fluid">


		<script type="text/javascript">		
		
				
			function addnotice(){
				var frm = document.noticeform;
				var noticeno = '${noticeno}';
				
				frm.noticeno.value = noticeno;
				frm.method = "post";
				frm.action = "noticeModifyEnd.do";
				frm.submit();
			}			
		</script>

		<!-- 공지사항 등록 -->
		<form name="noticeform">
		
		<h3 align="center"> ABOUT > Notice </h3>
        <hr style="border: solid 1px black; margin-bottom: 0px; padding: 0px;" ><br/>
        
        <input type="hidden" name="noticeno" id="noticeno" />
        
    		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>제 목</label>
			<input class="form-control" name="noticename" type="text" maxlength="30" value="${noticename}"/>
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>내 용</label>
			<textarea class="form-control" name="noticeContent" cols="41" rows="15" >${noticeContent}</textarea>
		</div>

		<div align="center">
			<a style="color: white" class="btn btn-primary" href="javascript:addnotice()">저장</a>&nbsp;&nbsp;
			<a style="color: white" class="btn btn-primary" href="javascript:history.go(-1)">뒤로</a>&nbsp;&nbsp;
		</div>
		
		</form>

	</div>
</div>

<jsp:include page="../../footer.jsp" />
