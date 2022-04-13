<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/component/header.jsp" %>

<script>
$(document).ready(function() {
	var id
	var pw
	var pwch
	
	$("#joinSubmit").click(function() {
		id = $('input[name=id]').val()
		pw = $('input[name=pw]').val()
		pwch = $('#pwch').val()
		
		if (idChk(id) && pwChk(pw, pwch)) {
			var queryString = $("form[name=joinForm]").serialize()
			
			$.ajax({
				type: "POST"
				, url: "/member/join"
				, dataType: "json"
				, data: queryString
				, success: function(res) {
					if (res.msg == 'success') {
						alert('회원가입에 성공하셨습니다.')
						//location.href = '/member/login'
						$("a[data-bs-target='#loginModal']").get(0).click()
					} else {
						alert('회원가입에 실패했습니다.')
						location.href = '/member/join'
					}
				}
				, error: function() {
					console.log("ajax 실패")	
				}
			})
		}
	})
	
	
	// 입력란 빈칸 확인
	function emptyChk(val, msg) {
		if (val === "") {
			alert(msg + '입력해주세요')
			return false
		}
		return true
	}
	// 아이디 검증
	function idChk(id) {
		if (!emptyChk(id, "아이디를 ")) return false
		if (!/^[a-zA-Z0-9]{4,12}$/.test(id)) {
			alert("아이디는 4~12 영어 대소문자와 숫자로만 입력해주세요")
			return false
		}
		return true
	}
	// 비밀번호 검증
	function pwChk(pw, pwch) {
		if (!emptyChk(pw, "비밀번호를 ")) return false
		if (!/^[a-zA-Z0-9]{4,8}$/.test(pw)) {
			alert("비밀번호는 4~8 영어 대소문자와 숫자로만 입력해주세요")
			return false
		}
		if (pw != pwch) {
			alert("비밀번호가 일치하지 않습니다.")
			return false
		}
		return true
	}
	// 비밀번호 일치 확인
	$("#pwch").keyup(function() {
		pw = $("#pw").val()
		pwch = $("#pwch").val()
		
		if (pw != pwch) {
			$("#pwValidate").text("비밀번호가 일치하지 않습니다.") 
			$("#pwValidate").css("color", "red")
		} else {
			$("#pwValidate").text("비밀번호가 일치합니다.")
			$("#pwValidate").css("color", "green")
		}
	})
})
</script>


<div class="container-sm" style="max-width: 600px">
<form name="joinForm">
	
	<div class="mb-3">
	  <label for="id" class="form-label">아이디</label>
	  <input type="text" class="form-control" id="id" aria-describedby="emailHelp" name="id">
	  <div id="idValidate" class="form-text">아이디 검증 문구</div>
	</div>
	<div class="mb-3">
	  <label for="nick" class="form-label">닉네임</label>
	  <input type="text" class="form-control" id="nick" name="nick">
	  <div id="nickValidate" class="form-text">닉네임 검증 문구</div>
	</div>
	<div class="mb-3">
	  <label for="pw" class="form-label">비밀번호</label>
	  <input type="text" class="form-control" id="pw" name="pw">
	</div>
	<div class="mb-3">
	  <label for="pwch" class="form-label">비밀번호 확인</label>
	  <input type="text" class="form-control" id="pwch">
	  <div id="pwValidate" class="form-text"></div>
	</div>
	
	<button id="joinSubmit" type="button" class="btn btn-primary col col-lg-2">회원가입</button>
	<button type="button" class="btn btn-danger col col-lg-2" onclick="history.back(-1)">취소</button>
  
</form>
</div>


<%@include file="/WEB-INF/views/component/footer.jsp" %>