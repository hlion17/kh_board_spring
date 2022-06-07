## 개인과제 개요

### 개인 과제 목적

- 스프링 프레임 워크를 사용하여 간단한 회원제 게시판 CRUD 구현
<br>

### 개발환경

- FrontEnd: HTML, CSS, Javascript, jQuery, Bootstrap
- BackEnd: Tomcat Server, Java, Spring Framework, Mybatis
- Database: Oracle
- Test: Junit5, Mockito  
<br>

### 사용 라이브러리

- 파일업로드
    - common-fileupload 1.4
    - common-io 2.11.0
- JSON 데이터 파싱
    - jackson 2.13.2.2  
<br>

### 요구사항

- 회원 로그인, 로그아웃, 가입, 정보 확인
- 기본적인 게시판 CRUD 구현
    - 글 작성, 수정, 삭제는 회원전용 기능
    - 게시글 페이지네이션 처리
- 게시글 제목, 내용 검색
- 게시글 추천
- 파일 업로드, 다운로드  
<br>

### 추가사항

- 인터셉터를 사용하여 회원제 기능 인증
- 캐시를 사용하여 무분별한 조회수 증가 방지  
<br>

## 개발 결과
- 회원 가입, 로그인, 로그아웃  
<br>

![member](https://user-images.githubusercontent.com/89788111/172350781-16a5dc05-ec9b-429f-bba6-0e13aaef1daa.gif)
