<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/views/component/header.jsp" %>

<script>
$(document).ready(function (){
	var msg = '${msg}'
	
	if (msg != '') {
		alert(msg)
	}
	
	const select = document.querySelector("select[name='category']")
	//const select = $("select[name='category']")

    for (i = 0; i < select.length; i++) {
        if (select.options[i].value == '${category}') {
            select.options[i].selected = true
            break;
        }
    }
	
	
	/*$("#btnSearch").click(function() {
		var keyword = $("input[name=keyword]").val()
		var category = $("select[name=category]").val()
		var curPage = '${pn.curPage}'
		
		console.log(keyword)
		console.log(category)
		console.log(curPage)
		
		if (keyword == '') {
			alert("검색어를 입력해주세요")
			return false
		}
		location.href="/board/list?curPage=" + curPage + "&category=" + category + "&keyword=" + keyword;
	})*/
	
		
})

// 페이지 이동 함수 정의 
function getPageList(curPage) {
	var keyword = $("input[name=keyword]").val()
	var category = $("select[name=category]").val()
	if (curPage == null) curPage = '${pn.curPage}'
	
	console.log(keyword)
	console.log(category)
	console.log(curPage)
	
	/* if (keyword == '') {
		alert("검색어를 입력해주세요")
		return false
	} */
	location.href="/board/list?curPage=" + curPage + "&category=" + category + "&keyword=" + keyword;
}
</script>

<style>
a {
	text-decoration: none;
	color: black;
}
a:hover {
	text-decoration: underline;
}
</style>

<div class="container">
    <div class="row">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
            <th scope="col">조회수</th>
            <th scope="col">추천수</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${list}">
        <tr>
            <td>${b.boardNo}</td>
            <td><a href="/board/view?boardNo=${b.boardNo}">${b.title}</a></td>
            <td>${b.writerId}</td>
            <td>
            <fmt:formatDate value="${b.writeDate}" pattern="yyyy/MM/dd"/>
			</td>
            <td>${b.hit}</td>
            <td>${b.recommend}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
    <div class="row justify-content-start">
        <div class="col-2">
        <button type="button" class="btn btn-primary" onclick="location.href='/board/write'">글작성</button>
        </div>
    </div>
    <!-- 페이지네이션 -->
    <div class="row justify-content-center" style="margin-top: 20px;">
        <div class="col-auto">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
            	<!-- 이전 -->
            	<c:if test="${pn.curPage gt 1}">
                <li class="page-item">
                    <a class="page-link" href="javascript:void(0);" onclick="getPageList(${pn.curPage - 10})">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-item"><a class="page-link" href="javascript:void(0);" onclick="getPageList(${pn.prevPage})">Prev</a></li>
                </c:if>
                
                <!-- 페이지들 -->
                <c:forEach begin="${pn.startPage}" end="${pn.endPage}" var="i">
                <c:choose>
                <c:when test="${pn.curPage eq i }">
     	            <li class="page-item active">
     	            	<a class="page-link" href="javascript:void(0);" onclick="getPageList(${i})">${i}</a>
   	            	</li>
                </c:when>
                <c:otherwise>
	                <li class="page-item">
	                	<%-- <a class="page-link" href="/board/list?curPage=${i}">${i}</a> --%>
	                	<a class="page-link" href="javascript:void(0);" onclick="getPageList(${i})">${i}</a>
                	</li>
                </c:otherwise>
                </c:choose>
                </c:forEach>
                
                <!-- 다음 -->
                <c:if test="${pn.curPage ne pn.pageCnt && pn.curPage lt pn.pageCnt}">
                <li class="page-item"><a class="page-link" href="javascript:void(0);" onclick="getPageList(${pn.nextPage})">Next</a></li>
                <li class="page-item">
                    <a class="page-link" href="javascript:void(0);" onclick="getPageList(${pn.curPage + 10})">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                </c:if>
                
            </ul>
        </nav>
        </div>
    </div>
    <!-- 검색바 -->
    <div class="row justify-content-center">
    	<div class="col-auto">
		    <select class="form-select" name="category">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
		</div>
		<div class="col-auto">
			<div class="d-flex">
				<input class="form-control me-2" type="search" placeholder="Search" name="keyword" value="${keyword}">
				<button class="btn btn-outline-success" type="button" id="btnSearch" onclick="getPageList()")>Search</button>
			</div>
		</div>
	</div>
</div>


<%@include file="/WEB-INF/views/component/footer.jsp" %>