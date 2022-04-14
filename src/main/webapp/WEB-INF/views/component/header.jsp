<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script>
$(document).ready(function() {
	
	
	/* $("#login").click(function() {
		var id = $('input[name=id]').val()
		var pw = $('input[name=pw]').val()
		var queryString = $('#loginForm').serialize()
		
		if (emptyChk(id, '아이디') && emptyChk(pw, '비밀번호')) {
			$.ajax({
				type: "POST",
				url: "/member/login", 
				dataType: "json", 
				data: queryString, 
				success: function(res) {
					if (res.result == 'true') {
						alert(res.msg)
						//location.href='/'
					} else {
						alert(res.msg)
					}
				}
			})
		}
	}) */
	
	/* $("#loginForm").submit(function() {
		var id = $('input[name=id]').val()
		var pw = $('input[name=pw]').val()
		var queryString = $('#loginForm').serialize()
		
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
	} */
})
</script>
</head>
<body>

<!-- 헤더 -->
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"/></svg>
            <span class="fs-4">Simple header</span>
        </a>

        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/board/list" class="nav-link" aria-current="page">게시판</a></li>
            <c:choose>
            <c:when test="${empty isLogin}">
<!--             <li class="nav-item"><a href="" class="nav-link" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</a></li> -->
            <li class="nav-item"><a href="/member/login" class="nav-link">로그인</a></li>
            <li class="nav-item"><a href="/member/join" class="nav-link">회원가입</a></li>
            </c:when>
            <c:otherwise>
            <li class="nav-item"><a href="/member/logout" class="nav-link">로그아웃</a></li>
            <li class="nav-item"><a href="/member/detail?id=${loginId}" class="nav-link">회원정보</a></li>
            </c:otherwise>
            </c:choose>
        </ul>
    </header>
</div>

<!-- 로그인 모달 -->
<!-- 사용 보류 -->
<!-- <div class="modal fade" tabindex="-1" role="dialog" id="loginModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content rounded-5 shadow">
      <div class="modal-header p-5 pb-4 border-bottom-0">
        <h5 class="modal-title">Modal title</h5>
        <h2 class="fw-bold mb-0">로그인</h2>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>

      <div class="modal-body p-5 pt-0">
        <form class="" id="loginForm" action="/member/login" method="post">
          <div class="form-floating mb-3">
            <input type="text" class="form-control rounded-4" id="floatingInput" placeholder="아이디를 입력하세요" name="id">
            <label for="floatingInput">아이디</label>
          </div>
          <div class="form-floating mb-3">
            <input type="password" class="form-control rounded-4" id="floatingPassword" placeholder="비밀번호를 입력하세요" name="pw">
            <label for="floatingPassword">비밀번호</label>
          </div>
          <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit" id="login">로그인</button>
          <small class="text-muted">By clicking Sign up, you agree to the terms of use.</small>
          <hr class="my-4">
          <h2 class="fs-5 fw-bold mb-3">Or use a third-party</h2>
          <button class="w-100 py-2 mb-2 btn btn-outline-dark rounded-4" type="submit">
            <svg class="bi me-1" width="16" height="16"><use xlink:href="#twitter"/></svg>
            Sign up with Twitter
          </button>
          <button class="w-100 py-2 mb-2 btn btn-outline-primary rounded-4" type="submit">
            <svg class="bi me-1" width="16" height="16"><use xlink:href="#facebook"/></svg>
            Sign up with Facebook
          </button>
          <button class="w-100 py-2 mb-2 btn btn-outline-secondary rounded-4" type="submit">
            <svg class="bi me-1" width="16" height="16"><use xlink:href="#github"/></svg>
            Sign up with GitHub
          </button>
        </form>
      </div>
    </div>
  </div>
</div> -->