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
})
</script>

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
    <div class="row justify-content-center">
        <div class="col-6">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
            	<c:if test="${pn.curPage gt 1}">
                <li class="page-item">
                    <a class="page-link" href="/board/list?curPage=${pn.curPage - 10}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-item"><a class="page-link" href="/board/list?curPage=${pn.prevPage}">Prev</a></li>
                </c:if>
                <c:forEach begin="${pn.startPage}" end="${pn.endPage}" var="i">
                <c:choose>
                <c:when test="${pn.curPage eq i }">
     	            <li class="page-item active"><a class="page-link" href="/board/list?curPage=${i}">${i}</a></li>
                </c:when>
                <c:otherwise>
	                <li class="page-item"><a class="page-link" href="/board/list?curPage=${i}">${i}</a></li>
                </c:otherwise>
                </c:choose>
                
                </c:forEach>
                <c:if test="${pn.curPage ne pn.pageCnt && pn.curPage lt pn.pageCnt}">
                <li class="page-item"><a class="page-link" href="/board/list?curPage=${pn.nextPage}">Next</a></li>
                <li class="page-item">
                    <a class="page-link" href="/board/list?curPage=${pn.curPage + 10}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                </c:if>
            </ul>
        </nav>
        </div>
    </div>
</div>


<%@include file="/WEB-INF/views/component/footer.jsp" %>