<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<div class="container">

<form>
  <fieldset disabled>
    <legend>회원정보</legend>
    <div class="mb-3">
      <label for="id" class="form-label">아이디</label>
      <input type="text" id="id" class="form-control" placeholder="${member.id}">
    </div>
    <div class="mb-3">
      <label for="nick" class="form-label">닉네임</label>
      <input type="text" id="nick" class="form-control" placeholder="${member.nick}">
    </div>
    <div class="mb-3">
      <label for="pw" class="form-label">비밀번호</label>
      <input type="text" id="pw" class="form-control" placeholder="${member.pw}">
    </div>
  </fieldset>
</form>

</div>

<%@include file="/WEB-INF/views/component/footer.jsp" %>