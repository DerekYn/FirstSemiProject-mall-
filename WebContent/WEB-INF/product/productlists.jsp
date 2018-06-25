<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<jsp:include page="../../header.jsp"/>

<script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
	
<script type="text/javascript">

function goProductDelete(pcode){
	
	$("#pcode").val(pcode);
	var frm = document.prod;
	
	frm.method = "post";
	frm.action = "productDelte.do";
	frm.submit();
	
}
</script>


<div class="card mb-3">
   <div class="card-header">
     <h4>상품 정보 리스트</h4>
   </div>
   <div class="card-body"> 
	 <div class="table-responsive">           
		  <table class="table table-bordered" id="dataTable">
		    <thead>
		      <tr>
		        <th>상품코드</th>
		        <th>상품명</th>
		        <th>색상</th>
		        <th>사이즈</th>
		        <th>잔고수</th>
		        <th>삭제</th>
		      </tr>
		    </thead>
		    <tbody>
			  <c:if test="${produtlists == null || empty produtlists}">
			  	<tr>
			  	  <td colspan="4"> 저장된 상품이 없습니다. </td>
			  	<tr>
			  </c:if>
			  <c:if test="${produtlists != null && not empty produtlists}">
			  	<c:forEach items="${produtlists}" var="vo">
			  		<tr>
			  		  <td>${vo.pcode}</td>
			  		  <td>${vo.pname}</td>
			  		  <td>${vo.pcolor}</td>
			  		  <td>${vo.psize}</td>
			  		  <td>${vo.pqty}</td>
			  		  <td>
			  		  	<button type="button" class="btn btn-primary" style="font-size: 14pt; font-weight: bold; padding: 3%;" onClick="goProductDelete(${vo.pcode});">
							삭제하기
						</button>
					</td>
			  		</tr>
			  	</c:forEach>
			  </c:if>    
		    </tbody>
		  </table>
	 </div>  
   </div>
 </div>         
  

<form name="prod">
<input type="hidden" name="pcode" id="pcode">
</form>



<jsp:include page="../../footer.jsp"/>