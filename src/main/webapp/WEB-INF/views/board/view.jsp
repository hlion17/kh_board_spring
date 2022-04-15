<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/views/component/header.jsp" %>

<!-- 메세지 스크립트 -->
<script>
$(document).ready(function (){
	// view 페이지 로드시 추천 버튼 변경
	chk('${isRecommend}')
	// View 페이지 메시지 출력
	var msg = '${msg}'
	if (msg != '') {
		alert(msg)
	}
	// 글 삭제 POST 요청	
	$("#boardDel").click(function() {
		var boardNo = '${board.boardNo}'
		var writerId = '${board.writerId}'
		
		sendData('/board/delete', {boardNo: boardNo, writerId: writerId})	
	})

	// 추천하기
/* 	$(".rec").click(function() {
		var boardNo = '${board.boardNo}'
		var loginId = '${loginId}'
		
		sendData('/board/recommned', {boardNo: boardNo, loginId: loginId})
	}) */
	
	// 추천하기
	$("#rec").click(function() {
		var boardNo = '${board.boardNo}'
		var loginId = '${loginId}'
			
		$.ajax({
			type: "POST"
			, url: "/board/recommned"
			, dataType: "json"
			, data: {boardNo: boardNo, loginId: loginId}
			, success: function(res) {
				chk(res.isRecommend)
				alert(res.msg)
				$("#recommend").val(res.recommendCnt)
			}
			, error: function() {
				
			}
		})
	})
	// 추천 여부에 따라 추천 버튼 변경
	function chk(isRecommend) {
		if (isRecommend == 'true') {
			$("#rec").removeClass("btn-info")
			$("#rec").addClass("btn-danger")
			$("#rec").text("추천 취소")
		} else {
			$("#rec").removeClass("btn-danger")
			$("#rec").addClass("btn-info")
			$("#rec").text("추천")
		}
	}
	
	
	
})
</script>


<div class="container">

<div class="mb-3 row">
  <label for="title" class="col-sm-2 col-form-label">글제목</label>
  <div class="col-sm-10">
    <input type="text" readonly class="form-control-plaintext" id="title" value="${board.title}" disabled readonly>
  </div>
</div>
<div class="mb-3 row">
  <label for="title" class="col-sm-2 col-form-label">닉네임</label>
  <div class="col-sm-10">
    <input type="text" readonly class="form-control-plaintext" id="title" value="${board.writerNick}" disabled readonly>
  </div>
</div>
<div class="mb-3 row">
  <label for="recommend" class="col-sm-2 col-form-label">추천수</label>
  <div class="col-sm-10">
    <input type="text" readonly class="form-control-plaintext" id="recommend" value="${board.recommend}" disabled readonly>
  </div>
</div>
<div class="mb-3 row">
  <label for="title" class="col-sm-2 col-form-label">작성일</label>
  <div class="col-sm-10">
    <input type="text" readonly class="form-control-plaintext" id="title" disabled readonly value="<fmt:formatDate value="${board.writeDate}" pattern="yyyy/MM/dd"/>">
  </div>
</div>

<%-- <div class="mb-3">
  <label for="content" class="form-label">글내용</label>
  <textarea class="form-control" id="content" rows="3" disabled readonly>${board.content}</textarea>
</div> --%>
<div>
	<span class="form-label">글내용</span>
</div>
<hr>
<div>
${board.content}
</div>
<hr>

<div class="row justify-content-start">
    <div class="col-auto">
    <button type="button" class="btn btn-primary" onclick="location.href='/board/list'">목록으로</button>
    </div>
    <div class="col-auto">
    <button type="button" class="btn btn-warning" onclick="location.href='/board/update?boardNo=${board.boardNo}&writerId=${board.writerId}'">수정</button>
    </div>
    <div class="col-auto">
    <button type="button" id="boardDel" class="btn btn-danger">삭제</button>
    </div>
    <div class="col-auto">
    
    <%-- <c:if test="${empty isRecommend}">
    <button type="button" class="btn btn-info rec">추천</button>
    </c:if>
    <c:if test="${not empty isRecommend}">
    <button type="button" class="btn btn-danger rec">추천 취소</button>
    </c:if> --%>
    
    <button type="button" id="rec" class="btn"></button>
    
    </div>
</div>

</div>




<%@include file="/WEB-INF/views/component/footer.jsp" %>