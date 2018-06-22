<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="board.model.*" %>
<%
	QnaVO board = (QnaVO)request.getAttribute("boarddata");
%>

<jsp:include page="../../header.jsp" />

<div class="content-wrapper">
    <div class="container-fluid">

		<script type="text/javascript">
			function modifyboard(){
				modifyform.submit();
			}
		</script>
	
		<!-- 게시판 수정 -->
		<form action="boardModify.do" method="post" name="modifyform">
		<input type="hidden" name="board_qna_num" value=<%=board.getBoard_qna_num() %>>
				
		<h3 align="center"> ABOUT > Q&A </h3>
        <hr style="border: solid 1px black; margin-bottom: 0px; padding: 0px;" ><br/>
			
			
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>제 목</label>
           	 <input class="form-control" name="board_qna_subject" maxlength="100" value="<%=board.getBoard_qna_subject()%>">
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>내 용</label>
           	<textarea class="form-control" name="board_qna_content" cols="41" rows="15">
				<%=board.getBoard_qna_content() %>
			</textarea>
		</div>
		
		<%if(!(board.getBoard_qna_file() == null)){ %>
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>파일 첨부</label>
           	&nbsp;&nbsp;<%=board.getBoard_qna_file() %>
		</div>
		<%} %>	
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>비밀번호</label>
           	<input class="form-control" name="board_qna_pw" type="password">
		</div>		
			
		<div align="center">
			<a style="color: white" class="btn btn-primary" href="javascript:modifyboard()">수정</a>&nbsp;&nbsp;
			<a style="color: white" class="btn btn-primary" href="javascript:history.go(-1)">뒤로</a>&nbsp;&nbsp;
		</div>
		
		
		</form>

	</div>
</div>

<jsp:include page="../../footer.jsp" />
		