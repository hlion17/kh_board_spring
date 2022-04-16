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
	// 댓글 입력(no-ajax)
	$("#submitComment").click(function() {
		var content = $("textarea[name=commentContent]").val()
		var id = '${loginId}'
		var boardNo = '${board.boardNo}'
		
		if (id == '') {
			alert("로그인이 필요한 서비스입니다.")
			return false;
		}
		if (content == '') {
			alert("댓글 내용을 입력해주세요")
			return false;
		}
		sendData("/comment/insert", {boardNo: boardNo, id: id, content: content})
	})
	// 댓글 삭제
	$(".delComment").click(function() {
		var boardNo = '${board.boardNo}'
		var commentNo = $(this).attr("data-val")

		sendData("/comment/delete", {boardNo: boardNo, commentNo: commentNo})
	})
	
	
	
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

<!-- 게시글 네비 메뉴 -->
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
    <!-- 추천/추천 취소 버튼 -->
    <button type="button" id="rec" class="btn"></button>
    
    </div>
</div>
<!-- 게시글 네비 메뉴 끝 -->

<hr>

<!--  댓글  -->
<div class="card-body">
    <!-- 댓글 입력 -->
    <form class="mb-4">
    <div class="row">
    <textarea class="form-control" rows="3" placeholder="상대에게 상처가 주는 말을 삼가주세요" name="commentContent"></textarea>
    <button type="button" id="submitComment" class="btn btn-info col-2" style="margin-top: 10px;">등록</button>
    </div>
    </form>

	<!-- 댓글 조회 -->    
    <c:forEach var="c" items="${cList}">
	<div class="container" style="margin: 20px 0; padding: 10px;">
		<div class="row">
			<div class="col-auto">
				<img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="...">
			</div>
			<div class="col-8">
				<div class="row" style="border-bottom: 1px solid #ccc; align-items: center; padding: 10px 0;">
					<div class="col-auto">${c.id}</div>
					<div class="col-4">
					<fmt:formatDate value="${c.writeDate}" pattern="yy/MM/dd HH:mm"/>
					</div>
					<c:if test="${c.id eq loginId}">
					<div class="col-auto" style="margin-left: auto;">
						<button data-val="${c.commentNo}" class="btn btn-default delComment" style="color: red; padding: 0;">삭제</button>
					</div>
					</c:if>
				</div>
				<div class="row" style="padding: 5px;">
					${c.content}
				</div>
			</div>
		</div>
	</div>
    </c:forEach>
    
</div>
<!-- 댓글 끝 -->

<hr>

</div>




<%@include file="/WEB-INF/views/component/footer.jsp" %>