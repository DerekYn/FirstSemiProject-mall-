<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../../header.jsp" />



<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.3.1.min.js"></script>
<style>
	table#tblProdInput {border: solid gray 0px; 
	                    border-collapse: collapse; }
	                    
    table#tblProdInput td {border: solid gray 0px; 
	                       padding-left: 6%;
	                       padding-top: 15%;
	                       height: 10%;
	                       padding-top:2%;
	                       padding-bottom:2% }
	                       
    .prodInputName {
                   font-weight: 500; 
                   color:black;}	                       	                    
	
	.error {color: red; font-weight: bold; font-size: 9pt;}
	
	.button {
			    background-color: #87CEFA;
			    border: none;
			    color: white;
			    text-align: center;
			    text-decoration: none;
			    display: inline-block;
			    font-size: 16px;
			}
	
</style>


<script type="text/javascript">
	$(document).ready(function(){
		
		$(".error").hide();
		$("#psizeErr").hide();
		$("#btnRegister").bind("click", function(event){
			$(".error").hide();
			var bool = false;
			var booll = false;
			$(".infoData").each(function(){
				var val = $(this).val();
				if(val==""){
					$(this).next().next().show();
					bool = true;
				}
			}); // end of each()----------------
			

			$(".infonumData").each(function(){
				var val = $(this).val();
				var regNumber = /^[0-9]*$/;
				var price = $("#price").val();
				var saleprice = $("#saleprice").val();
				var priceb = parseInt(price);
				var salepriceb = parseInt(saleprice);

				if(val==""){
					$(this).next().next().show();
					booll = true;
				}
				else if(!regNumber.test(val)){
						$(this).next().next().show();
						booll = true;

				}else if(priceb < salepriceb){
						$("#saleprice").next().next().show();
						booll = true;
				}

				
			}); // end of each()----------------
		

			var psize = $("#psize").val().trim();
			if(psize == ""){
				$("#psizeErr").show();
				event.preventDefault();
			}
			
			if(bool || booll) {
				event.preventDefault();
			}
			 else {
				var prodInputFrm = document.prodInputFrm;
				prodInputFrm.submit();
			} 
			
		}); // end of $("#btnRegister").bind();------------------------------
			
		
			
			$("#addbtn").bind("click",function(event){
				var html = "";
				
				var ImgQtyVal = $("#ImgQty").val();
				

				if(ImgQtyVal == "0") {
					$("#divfileattach").empty();
					$("#attachCount").val("");
					return;
				}
				else
				{
					for(var i=0; i<parseInt(ImgQtyVal); i++) {
						html += "<br/>";
						html += "<input type='file' name='attach"+i+"' class='btn btn-default' />";
					}
					
					$("#divfileattach").empty();
					$("#divfileattach").append(html);
					$("#attachCount").val(ImgQtyVal);
				}
			});
			
		
	});
</script>

<div align="center" style="margin-bottom: 10%; border: 0px solid black; padding:1%; ">

<div style=" width: 100%; margin-top: 2%; padding-top: 2%; padding-bottom: 1%; border-left: hidden; border-right: hidden; border: 0px solid green">       
	<span style="font-size: 15pt; font-weight: bold;">제품등록</span>	
</div>
 <hr>
<br/>
<form name="prodInputFrm" 
    	action="<%= request.getContextPath() %>/productRegisterEnd.do"
     	method="post"
     	enctype="multipart/form-data">
  <div class="form-group" align="left">
    <label class="prodInputName">카테고리:</label>
    <br/><select name="fk_pcategory" class="infoData" style=" width:40%; " >
				<option value="">:::선택하세요:::</option>
				
				<!-- <option value="1000">outer</option>
				<option value="2000">top</option>
				<option value="3000">bottom</option> 
				<option value="4000">ACC</option>
				<option value="5000">suit</option>  -->
				
				<c:forEach var="categoryvo" items="${categoryList}"> 
					<option value="${categoryvo.code}">${categoryvo.cname }</option>
				</c:forEach>
				
			</select>
			<br/><span class="error">필수입력</span>
  </div>
   <div class="form-group" align="left">
    <label class="prodInputName">제품명:</label>
    <br/><input type="text" style="height: 2%;" name="pname" class="box infoData form-control" />
	<br/><span class="error">필수입력</span>
  </div>
   <div class="form-group" align="left">
    <label class="prodInputName">색상:</label>
    <br/><label><input type="radio" name="pcolor" value="0" checked="checked">검정색</label>
	<label><input type="radio" name="pcolor" value="1" >네이비색</label>
	<label><input type="radio" name="pcolor" value="2" >흰색</label>
	<label><input type="radio" name="pcolor" value="3" >그외</label>
	<br/><span class="error">필수입력</span>
  </div>
   <div class="form-group" align="left">
    <label class="prodInputName">사이즈:</label>
    <br/><label><input type="Number" id="psize" name="psize" class="box infonumData form-control"/></label>
	<br/><span id="psizeErr" class="error">필수입력사항입니다</span>
  </div>
   <div class="form-group" align="left">
    <label class="prodInputName">제품이미지:</label>
    <input type="file" name="pimage1" class="infoData" />
	<br/><span class="error">필수입력(숫자만 입력)</span>
  </div>
  <div class="form-group" align="left">
    <label class="prodInputName">제품수량(개):</label>
    <br/><input type="Number" name="pqty" class="box infonumData form-control" />
	<br/><span class="error">필수입력(숫자만 입력)</span>
  </div>
  <div class="form-group" align="left">
    <label class="prodInputName">정상가(원):</label>
    <br/><input type="text" name="price" id="price" class="box infonumData form-control" /> 
	<br/><span class="error">필수입력 (숫자만 입력)</span>
  </div>
  <div class="form-group" align="left">
    <label class="prodInputName" >판매가(원):</label>
    <br/><input type="text" name="saleprice" id="saleprice" class="box infonumData form-control" /> 
    <br/><span class="error">필수입력 (숫자만 입력, 제품정가보다 낮아야 합니다.)</span>
  </div>
  <div class="form-group" align="left">
    <label class="prodInputName">신상품:</label>
    <br/><label><input type="radio" name="pstatus" value="1" checked="checked" >new</label>
	<br/><span class="error">필수입력</span>
  </div>
  <div class="form-group" align="left" >
    <label class="prodInputName">상세설명:</label>
	<br/><textarea name="pcontent" rows="4" cols="42" class="form-control"></textarea>
  </div>
   <div class="form-group" align="left">
    <label class="prodInputName">추가이미지파일(선택):</label>
    <br/><input type="text" id="ImgQty" value="0" style="width:25%; text-align:right;" />
	<button type="button" id="addbtn" class="button" >추가</button>
    <div id="divfileattach"></div>
    <input type="hidden" name="attachCount" id="attachCount" />
  </div>
  <hr>
  <div style="margin-top:2%;">
	  <input type="button" value="제품등록" id="btnRegister" class="button" /> 
	  &nbsp;
	  <input type="reset" value="등록취소" class="button" />
	  <input type="button" value="뒤로가기" class="button" onclick="javascript:history.back();" />	
  </div>
</form>
</div>

<jsp:include page="../../footer.jsp" />    