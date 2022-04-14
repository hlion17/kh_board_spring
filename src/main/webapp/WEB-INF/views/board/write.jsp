<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/component/header.jsp" %>

<script>
$(document).ready(function() {
	
/* 	$('#write').click(function() {
		var title = $('input[name=title]').val()
		var content = $('textarea[name=content]').val()
		var writerId = '${loginId}'
		
		if (emptyChk(title, '제목') && emptyChk(content, '내용')) {
		
			$.ajax({
				type: "POST"
				, url: "/board/write"
				, dataType: "html"
				, data: {title: title, content: content, writerId: writerId}
				, success: function(res) {
					alert('확인')
				}
				, error: function() {
					console.log("ajax 실패")
				}
			})
		}
		return false
		
	}) */
	
 	$('#writeForm').submit(function() {
		var title = $('input[name=title]').val()
		var content = $('textarea[name=content]').val()

		if (emptyChk(title, '제목') && emptyChk(content, '내용')) {
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

<form id="writeForm" action="/board/write" method="post">

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
    <div class="col-2">
    <button type="submit" class="btn btn-primary" id="write">글작성</button>
    </div>
</div>

<input type="hidden" name="writerId" value="${loginId}">
<input type="hidden" name="writerNick" value="${loginNick}">

</form>

</div>

<%@include file="/WEB-INF/views/component/footer.jsp" %>