<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html>

<style type="text/css">
	
	.navbar-brand {font-size: 12pt;  font-family: 나눔고딕;}
	
	.navbar {font-size: 10pt; font-family: 나눔고딕;}


</style>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>ZIOZIA</title>
  
  
  <!-- Bootstrap core CSS-->
  <link href="<%= request.getContextPath() %>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
  <!-- Custom fonts for this template-->
  <link href="<%= request.getContextPath() %>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Page level plugin CSS-->
  <link href="<%= request.getContextPath() %>/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <!-- Custom styles for this template-->
  <link href="<%= request.getContextPath() %>/css/sb-admin.css" rel="stylesheet">
  
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/jquery-ui-1.11.4.custom/jquery-ui.min.css">
  
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/main.do">SUIT
    	<c:if test="${not empty sessionScope.loginuser}">
    		<span style="color: white; font-size: 10pt; padding-left: 20pt;" >[${sessionScope.loginuser.name}님 환영합니다.]</span>
    	</c:if>    
    </a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
          <a class="nav-link" href="<%= request.getContextPath() %>/main.do">
            <span class="nav-link-text" style="color: white; font-size: 15pt;">NEW</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Components">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseComponents" data-parent="#exampleAccordion">
            <span class="nav-link-text" style="color: white; font-size: 15pt;">SHOP</span>
          </a>
          <ul class="sidenav-second-level collapse" id="collapseComponents">
            <li>
              <a href="<%= request.getContextPath() %>/producttoplist.do?category=outer">OUTER</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/producttoplist.do?category=top">TOP</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/producttoplist.do?category=bottom">BOTTOM</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/producttoplist.do?category=suit">SUIT</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/producttoplist.do?category=acc">ACC</a>
            </li>
          </ul>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Example Pages">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseExamplePages" data-parent="#exampleAccordion">
            <span class="nav-link-text" style="color: white; font-size: 15pt;">ABOUT</span>
          </a>
          <ul class="sidenav-second-level collapse" id="collapseExamplePages">
            <li>
              <a href="<%= request.getContextPath() %>/noticeList.do">공지사항</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/boardList.do">Q&A</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/searchStoreList.do">STORE</a>
            </li>
          </ul>
        </li>
        
      <c:if test="${sessionScope.loginuser != null && sessionScope.loginuser.userid == 'admin' }">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Example Pages">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseAdminPages" data-parent="#exampleAccordion">
            <span class="nav-link-text" style="color: white; font-size: 15pt;">관리자용 페이지</span>
          </a>
          <ul class="sidenav-second-level collapse" id="collapseAdminPages">
            <li>
              <a href="<%= request.getContextPath() %>/admin/memberList.do">회원정보 목록</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/productRegister.do">제품등록</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/productlists.do">제품목록</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/productaddpage.do">제품입고</a>
            </li>
            <li>
              <a href="<%= request.getContextPath() %>/admin/chartList.do">차트</a>
            </li>
          </ul>
        </li>
      </c:if>
  
      </ul>
      <ul class="navbar-nav sidenav-toggler">
        <li class="nav-item">
          <a class="nav-link text-center" id="sidenavToggler">
            <i class="fa fa-fw fa-angle-left"></i>
          </a>
        </li>
      </ul>
      
      <ul class="navbar-nav ml-auto">
        <c:if test="${empty sessionScope.loginuser}">
	        <li class="nav-item">        
	          <a class="nav-link" href="<%= request.getContextPath() %>/loginOk.do"> 
	            <i class="fa fa-fw fa-sign-in"></i>로그인</a>
	        </li>
        </c:if>
        
        
        <c:if test="${not empty sessionScope.loginuser}">
	        <li class="nav-item">        
	          <a class="nav-link" href="<%= request.getContextPath() %>/logout.do"> 
	            <i class="fa fa-fw fa-sign-out"></i>로그아웃</a>
	        </li>
        </c:if> 
        
         <c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.userid != 'admin'}">
	        <li class="nav-item">        
	          <a class="nav-link" href="<%= request.getContextPath() %>/personalEdit.do"> 
	            <i class="fa fa-fw fa-sign-out"></i>정보 수정하기</a>
	        </li>
        </c:if> 
        <c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.userid != 'admin'}">
	        <li class="nav-item">        
	          <a class="nav-link" href="<%= request.getContextPath() %>/cartList.do"> 
	            <i class="fa fa-fw fa-sign-out"></i>장바구니 목록</a>
	        </li>
        </c:if> 
        <c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.userid != 'admin'}">
	        <li class="nav-item">        
	          <a class="nav-link" href="<%= request.getContextPath() %>/orderList.do"> 
	            <i class="fa fa-fw fa-sign-out"></i>주문 내역 목록</a>
	        </li>
        </c:if> 
        
        
        
      </ul>
    </div>
  </nav>
  
  <div class="content-wrapper">
    <div class="container-fluid">
