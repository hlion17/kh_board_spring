<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="/WEB-INF/views/component/header.jsp" %>

<div class="container">
로그인 세션: ${isLogin}<br>
로그인 세션 아이디: ${loginId}<br>
</div>

<%@include file="/WEB-INF/views/component/footer.jsp" %>
