<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../header.jsp" />
	
	<script src="<%= request.getContextPath() %>/js/jquery.min.js"></script>
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			if(${result == "success"}) {
				alert("업데이트에 성공했습니다!");
				location.assign("productaddpage.do");
			}
			
			else if(${result == "fail"}) {
				alert("업데이트에 실패했습니다!");
				location.assign("productaddpage.do");
			}
			
			$("#spinner").spinner({
				spin: function( event, ui ) {
					if( ui.value > 100 ) {
						$( this ).spinner( "value", 0 ); 
						return false;
					} 
					else if ( ui.value < 0 ) {
						$( this ).spinner( "value", 100 );
						return false;
					}
				}
			});
			
		});
	
		function goProductName(pnamelbl, pcode) {
			$("#pnamelbl").val(chk_byte(pnamelbl));
			$("#pcode").val(pcode);
			$("#close").click();
		}
		
		function chk_byte(in_texts)
		
		{
		  var ls_str = in_texts; 
		  var li_str_len = ls_str.length; //전체길이
		
		  //변수초기화
		  var li_max = 22; //제한할 글자수 크기
		  var i = 0;
		  var li_byte = 0;   //한글일경우 2, 그외글자는 1을 더함
		  var li_len = 0;    // substring하기 위해 사용
		  var ls_one_char = "";  //한글자씩 검사
		  var ls_str2 = "";      //글자수를 초과하면 제한한 글자전까지만 보여줌.
		  
		  for(i=0; i< li_str_len; i++)
		  {
		    ls_one_char = ls_str.charAt(i);   //한글자 추출
		    if(escape(ls_one_char).length > 4){ 
		      li_byte +=2;   //한글이면 2를 더한다
		    }else{
		      li_byte++;     //한글아니면 1을 다한다
		    }
		      
		    if(li_byte <= li_max){
		      li_len = i + 1;
		    }
		  }
		
		  if(li_byte > li_max)
		  {
		    ls_str2 = ls_str.substr(0, li_len) + "...";
		    return ls_str2;
		  }
		
		  return ls_str;
		}
		
		function goProductAdd() {
			
			if($("#pnamelbl").val() == "") {
				alert("상품을 선택 해 주십시오.");
				return;
			}
			
			var frm = document.productaddfrm;
			
			frm.method = "post";
			frm.action = "productadd.do";
			frm.submit();
			
		}
		function goProductDelete(){
			
			if($("#pnamelbl").val() == "") {
				alert("상품을 선택 해 주십시오.");
				return;
			}
			
			var frm = document.productaddfrm;
			
			frm.method = "post";
			frm.action = "productDelte.do";
			frm.submit();
			
		}
	
	</script>

	<form name="productaddfrm">
		
		<div class="form-group" align="center" style="margin-top: 30%; margin-bottom: 10%">
			<div align="left">
				<label>상품선택 :</label>
			</div>
			<div style="width: 70%; float: left;" >
				<input type="text" id="pnamelbl" class="form-control" style="margin-right: 7%; font-weight: bold;" size="18" readonly="readonly"/>
			</div>
			<!-- Button to Open the Modal -->
			
			<div style="width: 28%; float: right;" >
				<button type="button" id="pnameselect" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
					상품 선택
				</button>
			</div>
		</div>
		
		<br>
		
		<div class="form-group" align="center" style="margin-top: 10%; margin-bottom: 10%;">
			<label style="font-weight: bold; margin-right: 7%">입고 갯수</label>
			<input type="number" name="pqty" id="spinner" value = "1" size="10" />
		</div>
		
		<hr>
		
		<div class="form-group" align="center" style="margin-top: 30%;">
			<button type="button" class="btn btn-primary" style="font-size: 14pt; font-weight: bold; width: 30%; padding: 3%;" onClick="goProductAdd();">
				입고하기
			</button>
			
		</div>
		
		<input type="hidden" id="pcode" name="pcode" />
		
	</form>
		
	<!-- The Modal -->
	<div class="modal" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">제품명 선택</h4>
	        <button type="button" id="close" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	        <div class="content-wrapper">
			    <div class="container-fluid">
			      <!-- Example DataTables Card-->
			      <div class="card mb-3">
			        <div class="card-body">
			          <div class="table-responsive">
			            <table class="table table-bordered" id="dataTable">
			              <thead>
			                <tr>
			                  <th>제품코드</th>
			                  <th>제품명</th>
			                  <th>수량</th>
			                </tr>
			              </thead>
			              <tbody>
			                <c:forEach var="pmap" items="${plist}">
			                	<tr>
			                		<td>
			                			${pmap.pcode}
			                		</td>
			                		<td>
			                			<a onCLick="goProductName('${pmap.pname}', '${pmap.pcode}');">${pmap.pname}</a>
			                		</td>
			                		<td>
			                			<span>${pmap.pqty}</span>
			                		</td>
			                	</tr>
			                </c:forEach>
			              </tbody>
			            </table>
			          </div>
			        </div>
			        <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
			      </div>
			    </div>
	
	        <!-- Modal footer -->
	        <div class="modal-footer">
	          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	        </div>
	
	      </div>
	    </div>
	  </div>
	</div>
    
<jsp:include page="../../footer.jsp" />