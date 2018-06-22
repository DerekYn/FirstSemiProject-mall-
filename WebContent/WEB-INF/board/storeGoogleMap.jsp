<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxname = request.getContextPath();
%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="<%= ctxname %>/bootstrap-3.3.7-dist/css/bootstrap.min.css">

<script type="text/javascript" src="<%= ctxname %>/js/jquery-1.12.4.min.js"></script> 
<%-- jquery 1.x 또는 jquery 2.x 를 사용해야만 한다. 구글맵은 jquery 3.x 을 사용할 수 없다.   --%>

<script type="text/javascript" src="<%= ctxname %>/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<%-- 구글맵 api 사용하기  --%>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB70lCDXMdr8ajuFT2jaKroyoFboUnbijg"></script>

<style>
	#div_name {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
	
	#div_mobile {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
	
	#div_findResult {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;		
		position: relative;
	}
	
	#div_btnFind {
		width: 70%;
		height: 15%;
		margin-bottom: 5%;
		margin-left: 10%;
		position: relative;
	}
	
</style>

<script type="text/javascript">
	
	$(document).ready(function(){
		google.maps.event.addDomListener(window, 'load', initialize);
	}); // end of $(document).ready()-------------------------
	
	
	function initialize() {
		
		// 구글 맵 옵션 설정
		var mapOptions = { 
	        zoom : 15, // 기본 확대율
	        center : new google.maps.LatLng("${LatitudeLongitude[0]}", "${LatitudeLongitude[1]}"), // 지도 중앙 위치
	        disableDefaultUI : false,  // 기본 UI 비활성화 여부
	        scrollwheel : true,        // 마우스 휠로 확대, 축소 사용 여부
	        zoomControl : true,        // 지도의 확대/축소 수준을 변경하는 데 사용되는 "+"와 "-" 버튼을 표시
	        mapTypeControl : true,     // 지도 유형 컨트롤은 드롭다운이나 가로 버튼 막대 스타일로 제공되며, 사용자가 지도 유형(ROADMAP, SATELLITE, HYBRID 또는 TERRAIN)을 선택할 수 있다. 이 컨트롤은 기본적으로 지도의 왼쪽 위 모서리에 나타난다.
	        streetViewControl : true,  // 스트리트 뷰 컨트롤에는 지도로 드래그해서 스트리트 뷰를 활성화할 수 있는 펙맨 아이콘이 있다. 기본적으로 이 컨트롤은 지도의 오른쪽 아래 근처에 나타난다.
	        scaleControl: true,        // 배율 컨트롤은 지도 배율 요소를 표시한다. 이 컨트롤은 기본적으로 비활성화되어 있다.
	    };
	    // 구글맵 옵션내역 사이트 아래 참조 
	    // https://developers.google.com/maps/documentation/javascript/reference#MapOptions
	 
	    var targetmap = new google.maps.Map(document.getElementById('googleMap'), mapOptions);  
		// 구글 맵을 사용할 타겟
		// !!! 주의 !!!  document.getElementById('googleMap') 라고 해야지
		//              $("#googleMap") 이라고 하면 지도가 나타나지 않는다.
	    
	    google.maps.event.addDomListener(window, "resize", function() {
	        var center = targetmap.getCenter();
	        google.maps.event.trigger(targetmap, "resize");
	        map.setCenter(center); 
	    });
		 
		
		var storeArr = [
				     <c:set var="cnt" value="1" />
				     <c:forEach var="storemapvo" items="${storemapList}" varStatus="status">					
				     [
				    	"${storemapvo.storeName}",
				    	"${storemapvo.latitude}",
				    	"${storemapvo.longitude}",
				    	"${storemapvo.zindex}"
					 ]
				     <c:if test="${cnt < storemapList.size()}">
				     ,
				     </c:if>
				     
				     <c:set var="cnt" value="${cnt + 1}" />   // 변수 cnt 를 1씩 증가
				     </c:forEach>
			      ];
			
		setMarkers(targetmap, storeArr);
		    
	} // end of function initialize()--------------------------------
		 
		
	var markerArr;  // 전역변수로 사용됨.
		
	function setMarkers(targetmap, storeArr){
		    
		markerArr = new Array(storeArr.length);
			
		for(var i=0; i < storeArr.length; i++){
			var store = storeArr[i];
			//  console.log(store[0]);  // 점포명  ${storevo.storeName} 출력하기
				
			var myLatLng = new google.maps.LatLng(Number(store[1]), Number(store[2]));  
			// Number() 함수를 꼭 사용해야 함을 잊지 말자.               위도, 경도 
				
			<%--	
			if(i == 0) {
				var image = "<%= request.getContextPath() %>/images/pointerBlue.png";  // 1번 마커 이미지	
			} 
			else if(i == 1) {
				var image = "<%= request.getContextPath() %>/images/pointerPink.png"; // 2번 마커 이미지
			}
			else {
				var image = "<%= request.getContextPath() %>/images/pointerGreen.png"; // 3번 마커 이미지
			}
		    --%>	
				
		    // *** 마커 설정하기 *** //
			markerArr[i] = new google.maps.Marker({  
				position: myLatLng,        // 마커 위치
				map: targetmap,
			//	icon : image,              // 마커 이미지
				title : store[0],          // 위에서 정의한 "${store.storeName}" 임
				zIndex : Number(store[3])  // 위에서 정의한 "${storevo.zindex}" 임.  Number() 함수를 꼭 사용해야 함을 잊지 말자.
			});
			
			// **** 마커를 클릭했을때 어떤 동작이 발생하도록 하는 함수 호출하기 ****//   
			markerListener(targetmap, markerArr[i]);
				
		} // end of for------------------------------	
			
	}// end of setMarkers(map, locations)--------------------------
	 
		
///////////////////////////////////////////////////////////////////////////////////////

    var infowindowArr = new Array();  // 풍선창(풍선윈도우) 여러개를 배열로 저장하기 위한 용도 
        
 // **** 마커를 클릭했을때 어떤 동작이 발생하도록 하는 함수 생성하기 ****// 
	function markerListener(targetmap, marker){      
	
		// 확인용
		// console.log(marker.zIndex);	//  1  2  3 
	
		// === 풍선창(풍선윈도우)만들기 ===
		var infowindow = new google.maps.InfoWindow(  
				{// content: '안녕하세요~', 
				 // content: marker.title,	
				    content: viewContent(marker.title, marker.zIndex), 
				    size: new google.maps.Size(100,100) 
				});
		
		infowindowArr.push(infowindow); 
		// 생성한 풍선창(풍선윈도우) infowindow를 배열 infowindowArr 속에 집어넣기		
		
		// **** === marker에 click 이벤트 처리하기 === ***// 
		/*  마커를 클릭했을때 어떤 동작이 발생하게 하려면  
            addListener() 이벤트 핸들러를 사용하여 이벤트 알림을 등록하면 된다. 
                       이 함수는 소스객체, 수신할 이벤트, 지정된 이벤트가 발생할 때 호출할 함수를 인자로 받는다. */
		google.maps.event.addListener(marker, 'click', 
		    function(){ 
			      // marker에(i번째 마커) click(클릭)했을 때 실행할 내용들...
            
	              // 확인용
	              // console.log(marker.zIndex);  // 1  2  3
		     			
				  for(var i=0; i<markerArr.length; i++) { // 생성된 마커의 갯수만큼 반복하여
					  if(i != (marker.zIndex - 1) ) {     // 마커에 클릭하여 발생된 풍선창(풍선윈도우) infowindow 를 제외한 나머지 다른 마커에 달린 풍선창(풍선윈도우) infowindow 는
						 infowindowArr[i].close();	      // 닫는다.
					  }
					  else if(i == (marker.zIndex - 1)) {           // 마커에 클릭하여 발생된 풍선창(풍선윈도우) infowindow 는
						 infowindowArr[i].open(targetmap, marker);  // targetmap 상에 표시되어 있는 marker 위에 띄운다.
					  }
				  }// end of for-----------------------	 		
			 	
		  });  // end of google.maps.event.addListener()-------------------
		
	}// end of function markerListener(map, marker)-----------
	
	function viewContent(title, zIndex) {
		var html =  "<span style='color:red; font-weight:bold;'>"+title+"</span><br/>"
			     +  "<a href='javascript:goDetail("+zIndex+");'></a>"; 
			         // 매장번호(marker.zIndex)를 넘겨받아 매장지점 상세정보 보여주기와 같은 팝업창 띄우기
		return html;	
	}
	
	function showStoreList() {
		javascript:history.back();
	}
	
</script>

<div id="googleMap"	style="width: 100%; height: 95%; margin: auto;"></div>
<button type="button"style="width: 100%; height: 40px; background-color: navy; color: white; border: none;" onClick="showStoreList();">닫기</button>

