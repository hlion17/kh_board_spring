<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<script>
$(document).ready(function() {
	
	var msg = '${msg}'
	
	if (msg != '') {
		alert(msg)
	}

	$("#loginForm").submit(function() {
		var id = $('input[name=id]').val()
		var pw = $('input[name=pw]').val()
		
		if (emptyChk(id, '아이디') && emptyChk(pw, '비밀번호')) {
			$(this).submit()
		}
		return false
	})
	
	
	function emptyChk(val, msg) {
		if (val == '') {
			alert(msg + '를 입력해주세요')
			return false
		}
		return true
	}
	
})
</script>

<div class="container" style="max-width: 300px;">
<main class="form-signin">
  <form id="loginForm" action="/member/login" method="post">
    
    <h1 class="h3 mb-3 fw-normal">로그인</h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="id" placeholder="아이디" name="id">
      <label for="id">아이디</label>
    </div>
    <br>
    <div class="form-floating">
      <input type="password" class="form-control" id="pw" placeholder="비밀번호" name="pw">
      <label for="pw">비밀번호</label>
    </div>
    <br>
    <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2021</p>
  </form>
</main>
</div>

<%@include file="/WEB-INF/views/component/footer.jsp" %>