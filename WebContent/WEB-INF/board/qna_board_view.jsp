<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="board.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	QnaVO board = (QnaVO)request.getAttribute("boarddata");
%>

<jsp:include page="../../header.jsp" />

<div class="content-wrapper">
    <div class="container-fluid">
    
    
    	<h3 align="center"> ABOUT > Q&A </h3>
      	<hr style="border: solid 1px black; margin-bottom: 0px; padding: 0px;" ><br/>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>제 목</label>
           	<input class="form-control" style="border: 0;" name="board_qna_subject" type="text" value="<%=board.getBoard_qna_subject()%>" readonly />
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>내 용</label>
           	<textarea class="form-control" name="board_qna_content" cols="41" rows="15" readonly><%=board.getBoard_qna_content() %></textarea>
		</div>
		
		<div class="form-group">
			<label for="name"><span style='color: red;'>*</span>첨부 파일</label>
           	<%if(!(board.getBoard_qna_file()==null)){ %>
				<a href="../boardupload/<%=board.getBoard_qna_file()%>">
					<%=board.getBoard_qna_file() %>
				</a>
			<%} %>
			<input class="form-control" style="border: 0;" name="board_qna_file" type="text" value="첨부파일 없음" readonly />
		</div>
					
		<c:if test="${sessionScope.loginuser.userid == 'admin'}">
		
			<a style="color: white" class="btn btn-primary" href="boardReply.do?num=<%=board.getBoard_qna_num() %>">답변</a>&nbsp;&nbsp;
		
		</c:if>
		
		<c:if test="${sessionScope.loginuser.userid == boarddata.board_qna_userid}">
		
			<a style="color: white" class="btn btn-primary" href="boardModifyView.do?num=<%=board.getBoard_qna_num() %>&userid=<%=board.getBoard_qna_userid() %>">수정</a>&nbsp;&nbsp;
			
		</c:if>	
		
		<c:if test="${sessionScope.loginuser.userid == 'admin' || sessionScope.loginuser.userid == '<%=board.getBoard_qna_userid() %>'}">
		
			<a style="color: white" class="btn btn-primary" href="boardDeleteView.do?num=<%=board.getBoard_qna_num() %>">삭제</a>&nbsp;&nbsp;
			
		</c:if>
		
		<a style="color: white" class="btn btn-primary" href="boardList.do">목록</a>&nbsp;&nbsp;
		
	</div>
</div>

<jsp:include page="../../footer.jsp" />
	
