<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="board.model.*" %>

<%
	List boardList=(List)request.getAttribute("boardlist");
	int listcount=((Integer)request.getAttribute("listcount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();
%>

<jsp:include page="../../header.jsp" />

<div class="content-wrapper">
    <div class="container-fluid">
    

		<!-- 게시판 리스트 -->
		<table width="95%" border="0" cellpadding="0" cellspacing="0">
			<tr align="center" valign="middle">
				<td colspan="5" style="border-bottom: solid 2px black; margin-bottom: 0px; padding: 0px;">
					<h3> ABOUT > Q&A </h3>
				</td>
			</tr>
			
						
			<tr align="center" valign="middle" bordercolor="#333333">
				<td style="font-family:Tahoma;font-size:8pt;" width="8%" height="26">
					<div align="center">번호</div>
				</td>
				<td style="font-family:Tahoma;font-size:8pt;" width="50%">
					<div align="center">제목</div>
				</td>
				<td style="font-family:Tahoma;font-size:8pt;" width="14%">
					<div align="center">작성자</div>
				</td>
				<td style="font-family:Tahoma;font-size:8pt;" width="17%">
					<div align="center">날짜</div>
				</td>
				<td style="font-family:Tahoma;font-size:8pt;" width="11%">
					<div align="center">조회수</div>
				</td>
			</tr>
			
			<%
				for(int i = 0; i < boardList.size(); i++){
					QnaVO bl = (QnaVO)boardList.get(i);
			%>
			
			<tr align="center" valign="middle" bordercolor="#333333"
				onmouseover="this.style.backgroundColor='F8F8F8'"
				onmouseout="this.style.backgroundColor=''">
				<td height="23" style="font-family:Tahoma;font-size:10pt;">
					<%=bl.getBoard_qna_num()%>
				</td>
				
				<td style="font-family:Tahoma;font-size:10pt;">
					<div align="left">
					<%if(bl.getBoard_qna_re_lev() != 0){ %>
						<%for(int a=0; a<=bl.getBoard_qna_re_lev() * 2; a++){ %>
						&nbsp;
						<%} %>
						▶
					<%}else{ %>
						▶
					<%} %>
					<a href="<%= request.getContextPath() %>/boardDetail.do?num=<%=bl.getBoard_qna_num()%>">
						<%=bl.getBoard_qna_subject()%></a>
					</div>
				</td>
				
				<td style="font-family:Tahoma;font-size:10pt;">
					<div align="center"><%=bl.getBoard_qna_userid() %></div>
				</td>
				<td style="font-family:Tahoma;font-size:10pt;">
					<div align="center"><%=bl.getBoard_qna_date() %></div>
				</td>	
				<td style="font-family:Tahoma;font-size:10pt;">
					<div align="center"><%=bl.getBoard_qna_readcount() %></div>
				</td>
			</tr>
			<%} %>
			<tr align=center height=20>
				<td colspan=7 style=font-family:Tahoma;font-size:10pt;>
					<%if(nowpage <= 1){ %>
					[이전]&nbsp;
					<%}else{ %>
					<a href="<%= request.getContextPath() %>/boardList.do?page=<%=nowpage-1 %>">[이전]</a>&nbsp;
					<%} %>
					
					<%for(int a=startpage;a<=endpage;a++){
						if(a==nowpage){%>
						[<%=a %>]
						<%}else{ %>
						<a href="<%= request.getContextPath() %>/boardList.do?page=<%=a %>">[<%=a %>]</a>&nbsp;
						<%} %>
					<%} %>
					<%if(nowpage>=maxpage){ %>
					[다음]
					<%}else{ %>
					<a href="<%= request.getContextPath() %>/boardList.do?page=<%=nowpage+1 %>">[다음]</a>
					<%} %>
				</td>
			</tr>
			<tr align="right">
				<td align="left" colspan="2">
					총 글 개수 : ${listcount}
				</td>
				<td colspan="3">
			   		<a href="<%= request.getContextPath() %>/boardWrite.do">[글쓰기]</a>
				</td>
			</tr>
		</table>
	
	</div>
</div>

<jsp:include page="../../footer.jsp" />