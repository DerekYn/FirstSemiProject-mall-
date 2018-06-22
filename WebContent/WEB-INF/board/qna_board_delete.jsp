<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int num=Integer.parseInt(request.getParameter("num"));
%>
<jsp:include page="../../header.jsp" />

		<form name="deleteForm" action="boardDelete.do?num=<%=num %>" method="post">
		  <div style="margin-left: 8%; margin-top: 80%" align="left">
			<table border=1>
				<tr>
					<td>
						<font size=2>글 비밀번호 : </font>
					</td>
					<td>
						<input class="form-control" name="board_qna_pw" type="password" />
					</td>
				</tr>
				<tr>
					<td colspan=2 align="center">
						<a href="javascript:deleteForm.submit()">삭제</a>
							&nbsp;&nbsp;
						<a href="javascript:history.go(-1)">돌아가기</a>
					</td>
				</tr>
			</table>
	      </div>
		</form>

<jsp:include page="../../header.jsp" />