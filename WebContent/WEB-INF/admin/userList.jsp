<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../header.jsp"/>

<script type="text/javascript">


 	function goDel(idx,goBackURL){
 		
		
    var bool = confirm(idx + "번 회원님을 삭제하시겠습니까?");

    
    if(bool) {
    	var frm = document.idxFrm;
    	frm.idx.value = idx;
    	frm.goBackURL.value = goBackURL;
   		frm.action = "<%= request.getContextPath() %>/admin/memberDelete.do";    /* 중요 (admin을 uri창에 입력하지 않아도 된다.) */
    	frm.method = "post";
    	frm.submit();
    }
}




</script>
	


<div class="card mb-3">
   <div class="card-header">
     <h4>회원 정보 리스트</h4> 
   </div>
   <div class="card-body"> 
	 <div class="table-responsive">           
		  <table class="table table-bordered table-responsive"  id="dataTable">
		    <thead>
		      <tr>
		        <th>이름</th>
		        <th>이메일</th>
		        <th>주소</th>
		        <th>핸드폰 번호</th>
		        <th>삭제</th>
		      </tr>
		    </thead>
		    <tbody>
			  <c:if test="${userList == null || empty userList}">
			  	<tr>
			  	  <td colspan="4"> 가입된 회원이 없습니다. </td>
			  	<tr>
			  </c:if>
			  <c:if test="${userList != null && not empty userList}">
			  	<c:forEach items="${userList}" var="vo">
			  		<tr>
			  		  <td>${vo.name}</td>
			  		  <td>${vo.email}</td>
			  		  <td>${vo.alladdr}</td>
			  		  <td>${vo.allphone}</td>
			  		  <td>
			  		  <button type="button" class="btn btn-primary" id="delname" name="delname" value="${vo.idx}" onclick="javascript:goDel('${vo.idx}','${currentURL}');">회원삭제</button>
			  		  </td>
			  		</tr>
			  	<%-- 	<tr>
			  		  <td><input type="text" value="${vo.idx}"/></td>
			  		  <td></td>
			  		  <td></td>
			  		  <td></td>
			  		</tr> --%>
			  	</c:forEach>
			  </c:if>    
		    </tbody>
		  </table>
	 </div>  
   </div>
 </div>     
 
  <form name="idxFrm">
  		<input type="hidden" name="idx"/>
  		<input type="hidden" name="goBackURL"/>
  </form>
 
     
 

<jsp:include page="../../footer.jsp"/>