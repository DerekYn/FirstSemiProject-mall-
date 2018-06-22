<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<jsp:include page="../../header.jsp"/>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<table id="datatable" class="table table-bordered">
    <thead>
        <tr>
            <th></th>
            <th>좋아요</th>
            <th>싫어요</th>
        </tr>
    </thead>
    <tbody>
    	<c:if test="${maplist == null || empty maplist}">
    		<td colspan="3" align="center">
    			제품에 대한 평가가 존재하지 않습니다.
    		</td>
    	</c:if>
    	<c:if test="${maplist != null && not empty maplist}">
    		<c:forEach items="${maplist}" var="map">
    			<tr>
	    			<th>${map.pname}</th>
	    			<td>${map.likecnt}</td>
	    			<td>${map.dislikecnt}</td>
    			</tr>
    		</c:forEach>
    	</c:if>
       <!--  
        <tr>
            <th>Apples</th>
            <td>3</td>
            <td>4</td>
        </tr>
        <tr>
            <th>Pears</th>
            <td>2</td>
            <td>0</td>
        </tr>
        <tr>
            <th>Plums</th>
            <td>5</td>
            <td>11</td>
        </tr>
        <tr>
            <th>Bananas</th>
            <td>1</td>
            <td>1</td>
        </tr>
        <tr>
            <th>Oranges</th>
            <td>2</td>
            <td>4</td>
        </tr>
         -->
    </tbody>
</table>

<script type="text/javascript">

Highcharts.chart('container', {
    data: {
        table: 'datatable'
    },
    chart: {
        type: 'column'
    },
    title: {
        text: '제품 선호도 차트(가장 많이 투표한 제품 3개)'
    },
    yAxis: {
        allowDecimals: false,
        title: {
            text: 'Units'
        }
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                this.point.y + ' ' + this.point.name.toLowerCase();
        }
    }
});

</script>



<jsp:include page="../../footer.jsp"/>