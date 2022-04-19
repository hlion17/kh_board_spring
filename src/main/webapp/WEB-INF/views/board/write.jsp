<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/component/header.jsp" %>

<!-- 텍스트 에디터 로드 -->
<script src="/resources/ckeditor/ckeditor.js"></script>

<script>
$(document).ready(function() {
	
 	$('#writeForm').submit(function() {
		var title = $('input[name=title]').val()
		var content = $('textarea[name=content]').val()

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

<form id="writeForm" action="/board/write" method="post" enctype="multipart/form-data">

<div class="mb-3 row">
	<label for="title" class="form-label">글제목</label>
	<div class="col-sm-10">
  		<input type="text" class="form-control" id="title" value="" name="title">
	</div>
</div>
<div class="mb-3">
	<label for="content" class="form-label">글내용</label>
	<textarea class="form-control" id="content" rows="3" name="content"></textarea>
</div>



<div class="row justify-content-start">
    </div>
		<label for="file" class="form-label">파일</label>
		<input class="form-control" type="file" id="file" name="file">
	<div>
    
    <div class="col-2 d-flex">
    	<button type="submit" class="btn btn-primary" id="write">글작성</button>
    	<button type="button" class="btn btn-danger" id="calcel" onclick="history.back();">취소</button>
    </div>
	
</div>

<input type="hidden" name="writerId" value="${loginId}">
<input type="hidden" name="writerNick" value="${loginNick}">

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