<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@include file="/WEB-INF/views/component/header.jsp" %>

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

<div class="row justify-content-start">
    <div class="col-2">
    <button type="button" class="btn btn-primary" onclick="location.href='/board/list'">목록으로</button>
    </div>
</div>

</div>




<%@include file="/WEB-INF/views/component/footer.jsp" %>