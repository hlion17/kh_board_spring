<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<div class="container" style="max-width: 300px;">
<main class="form-signin">
  <form>
    
    <h1 class="h3 mb-3 fw-normal">로그인</h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="id" placeholder="아이디">
      <label for="id">아이디</label>
    </div>
    <br>
    <div class="form-floating">
      <input type="password" class="form-control" id="pw" placeholder="비밀번호">
      <label for="pw">비밀번호</label>
    </div>
    <br>
    <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2021</p>
  </form>
</main>
</div>

<%@include file="/WEB-INF/views/component/footer.jsp" %>