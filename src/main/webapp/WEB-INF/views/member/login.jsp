<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<script>
$(document).ready(function() {
	// 페이지 메시지 처리
	var msg = '${msg}'
	if (msg != '') {
		alert(msg)
	}
	// 페이지 로드시 아이디 포커스
	$("input[name=id]").focus()
	// 취소버튼 - 뒤로가기
	$("#cancel").click(function() {
		history.go(-1)
	})
	// 로그인 요청
	$("#loginForm").submit(function() {
		var id = $('input[name=id]').val()
		var pw = $('input[name=pw]').val()
		// 빈칸 검증
		if (emptyChk(id, '아이디') && emptyChk(pw, '비밀번호')) {
			$(this).submit()
		}
		return false
	})
	// 빈칸 검증 함수 정의
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
      <input type="text" class="form-control" id="id" placeholder="아이디" name="id" required="required">
      <label for="id">아이디</label>
    </div>
    <br>
    <div class="form-floating">
      <input type="password" class="form-control" id="pw" placeholder="비밀번호" name="pw" required="required">
      <label for="pw">비밀번호</label>
    </div>
    <br>
    <div class="d-flex justify-content-around">
	    <button class="btn btn-lg btn-primary" type="submit">로그인</button>
	    <button id="cancel" class="btn btn-lg btn-danger" type="button">취소</button>
    </div>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2021</p>
  </form>
</main>
</div>

<%@include file="/WEB-INF/views/component/footer.jsp" %>