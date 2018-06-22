<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../../header.jsp" />

<div class="content-wrapper">
    <div class="container-fluid">


		<script type="text/javascript">		
			function addboard(){
					boardform.submit();
				}
			
			
		</script>

		<!-- 게시판 등록 -->
		<form action="boardWriteEnd.do" method="post" enctype="multipart/form-data" name="boardform">
		
		<h3 align="center"> ABOUT > Q&A </h3>
        <hr style="border: solid 1px black; margin-bottom: 0px; padding: 0px;" ><br/>
			
			
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>글쓴이</label>
           	<input class="form-control" style="border: 0;" name="board_qna_userid" type="text" size="10" maxlength="10" value="${sessionScope.loginuser.userid}" readonly />
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>비밀번호</label>
			<input class="form-control" name="board_qna_pw" type="password" size="10" maxlength="10" value=""/>
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>제 목</label>
			<input class="form-control" name="board_qna_subject" type="text" maxlength="30" value=""/>
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>내 용</label>
			<textarea class="form-control" name="board_qna_content" cols="41" rows="15"></textarea>
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>파일 첨부</label>
			<input class="form-control" name="board_qna_file" type="file"/>
		</div>
		
		<div align="center">
			<a style="color: white" class="btn btn-primary" href="javascript:addboard()">저장</a>&nbsp;&nbsp;
			<a style="color: white" class="btn btn-primary" href="javascript:history.go(-1)">뒤로</a>&nbsp;&nbsp;
		</div>
		
		</form>

	</div>
</div>

<jsp:include page="../../footer.jsp" />
