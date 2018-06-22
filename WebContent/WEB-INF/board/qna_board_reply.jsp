<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="board.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	QnaVO board=(QnaVO)request.getAttribute("boarddata");
%>

<jsp:include page="../../header.jsp" />

<div class="content-wrapper">
    <div class="container-fluid">


		<script type="text/javascript">
			function replyboard(){
				boardform.submit();
			}
		</script>


		<!-- 게시판 답변 -->
		<form action="boardReplyView.do" method="post" name="boardform">
			<input type="hidden" name="board_qna_num" value="<%=board.getBoard_qna_num() %>">
			<input type="hidden" name="board_qna_re_ref" value="<%=board.getBoard_qna_re_ref() %>">
			<input type="hidden" name="board_qna_re_lev" value="<%=board.getBoard_qna_re_lev() %>">
			<input type="hidden" name="board_qna_re_seq" value="<%=board.getBoard_qna_re_seq() %>">
			
			<h3 align="center"> ABOUT > Q&A </h3>
       		<hr style="border: solid 1px black; margin-bottom: 0px; padding: 0px;" ><br/>
			
			<div class="form-group">
				<label for="name"><span style='color: red;'>*</span>글쓴이</label>
	           	<input class="form-control" style="border: 0;" name="board_qna_userid" type="text" value="${sessionScope.loginuser.userid}" readonly />
			</div>
							
			<div class="form-group">
				<label for="name"><span style='color: red;'>*</span>제 목</label>
	           	<input class="form-control" name="board_qna_subject" maxlength="30" value="Re: <%=board.getBoard_qna_subject()%>">
			</div>
		
			<div class="form-group">
				<label for="name"><span style='color: red;'>*</span>내 용</label>
	           	<textarea class="form-control" name="board_qna_content" cols="41" rows="15"></textarea>
			</div>
			
			
			<div align="center">
				<a style="color: white" class="btn btn-primary" href="javascript:modifyboard()">등록</a>&nbsp;&nbsp;
				<a style="color: white" class="btn btn-primary" href="javascript:history.go(-1)">뒤로</a>&nbsp;&nbsp;
			</div>
		</form>

	</div>
</div>
		
<jsp:include page="../../footer.jsp" />
		