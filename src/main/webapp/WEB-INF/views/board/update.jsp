<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/component/header.jsp" %>

<!-- 텍스트 에디터 로드 -->
<script src="/resources/ckeditor/ckeditor.js"></script>

<script>
$(document).ready(function() {
	
 	$('#updateForm').submit(function() {
		var title = $('input[name=title]').val()

		if (emptyChk(title, '제목')) {
			$(this).submit()	
		}
		return false;
	})
	
	function emptyChk(val, msg) {
		if (val == '') {
			alert(msg + '이(가) 비어있습니다.')
			return false
		}
		return true
	}
})
</script>

<div class="container">

<form id="updateForm" action="/board/update" method="post">

<div class="mb-3 row">
	<label for="title" class="form-label">글제목</label>
	<div class="col-sm-10">
  		<input type="text" class="form-control" id="title" value="${board.title}" name="title">
	</div>
</div>
<div class="mb-3">
	<label for="content" class="form-label">글내용</label>
	<textarea class="form-control" id="content" rows="3" name="content">${board.content}</textarea>
</div>

<div class="row justify-content-start">
    <div class="col-auto">
    <button type="button" class="btn btn-primary" onclick="location.href='/board/list'">목록으로</button>
    </div>
    <div class="col-auto">
    <button type="submit" class="btn btn-info">수정완료</button>
    </div>
</div>

<input type="hidden" name="boardNo" value="${board.boardNo}">

</form>

</div>

<!-- 텍스트 에디터 -->
<script>
    $(function () {
        CKEDITOR.replace('content', {
            filebrowserUploadUrl: '/ck/upload'
        })
    });
</script>

<%@include file="/WEB-INF/views/component/footer.jsp" %>