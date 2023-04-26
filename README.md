# 🌸 스프링 주특기 LV.4 과제
> Spring Security를 사용하여 인증/인가 구현하기

## 🙋‍♀️ 회원 관리 관련 기능
1. 회원 가입 API
    - username, password를 Client에서 전달받기
    - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문`로 구성되어야 한다.
    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글 수정 / 삭제 가능
     <br> 
            
2. 로그인 API
    - username, password를 Client에서 전달받기
    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
  <br> 


## 📖 게시물 관련 기능
1. 게시글 작성 API
    - Spring Security 를 사용하여 토큰 검사 및 인증하기
    - 제목, 작성자명(username), 작성 내용을 저장하고 저장된 게시글을 Client 로 반환하기
    <br> 
    
2. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 
    - 선택한 게시글에 등록된 모든 댓글을 선택한 게시글과 같이 Client에 반환하기
    <br> 
    
3. 선택한 게시글 수정 API
    - Spring Security 를 사용하여 토큰 검사 및 인증하기
    - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    <br> 
    
4. 선택한 게시글 삭제 API
    - Spring Security 를 사용하여 토큰 검사 및 인증하기
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    <br> 
    
    
## ✏️ 댓글 관련 기능
1. 댓글 작성 API
    - Spring Security 를 사용하여 토큰 검사 및 인증하기
    - 선택한 게시글의 DB 저장 유무를 확인하기
    - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
    <br> 
    
2. 댓글 수정 API
    - Spring Security 를 사용하여 토큰 검사 및 인증하기
    - 선택한 댓글의 DB 저장 유무를 확인하기
    - 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기
    <br> 
    
5. 댓글 삭제 API
    - Spring Security 를 사용하여 토큰 검사 및 인증하기
    - 선택한 댓글의 DB 저장 유무를 확인하기
    - 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    <br> 
    


## 🫡 API 명세서 보러가기
https://docs.google.com/spreadsheets/d/19PoED25SQOPyf3w7zTtsQ4wcw2k5Sjgqt4Cpyr7nq_U/edit?usp=sharing



## ⭐ ERD
<div align="center">

![image](https://user-images.githubusercontent.com/102853354/234566923-046cb1f7-0d6b-4fc2-93dd-47eb791da92c.png)

</div>
