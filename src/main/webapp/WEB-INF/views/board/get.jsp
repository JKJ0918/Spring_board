<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- jstl 코어 태그용 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- jstl 포메팅 태그용 -->

<%@ include file="../includes/header.jsp" %>

<!-- list.jsp에서 /board/register 경로를 호출하면 get메서드가 실행 폼 박스가 나옴 -->
<!-- 입력완료를 누르면 vo객체가 만들어 져서 /board/register에 post 메서드가 실행 -->

<div class="row">
	<div class="cal-lg-12">
		<h1 class="page-header"> 게시판 글 상세 보기 페이지 </h1>
	</div> <!-- .col-lg-12 end -->
</div> <!-- .row end -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-warning">
			<div class="panel-heading">Board Get</div>
			<div class="panel-body">
				<!-- form박스 만들고 submit 처리 -->
				<!-- <form role="form" action="/board/register" method="post"> -->
					
					<div class="form-group">
						<label>번호</label>
						<input class="form-control" name="bno" value='<c:out value="${ board.bno }"/>' readonly="readonly">
						
					</div>
					
					<div class="form-group">
						<label>제목</label>
						<input class="form-control" name="title" value='<c:out value="${ board.title }"/>' readonly="readonly">
					</div> <!-- title.form-group end -->
					
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="3" name="content" readonly="readonly">
						<c:out value="${ board.content }" /></textarea>
					</div> <!-- content.form-group end -->
					
					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name="writer"  value='<c:out value="${ board.writer }"/>' readonly="readonly">
					</div><!-- writer.form-group end -->
					
					<button data-oper='modify' class="btn btn-primary">수정</button>
					<%-- onclick="location.href='/board/modify?bno=<c:out value="${ board.bno }"/>'" --%>
					<!-- 링크 /board/modify?bno=게시물번호 -->
					<!-- <button data-oper='delete' class="btn btn-danger">삭제</button> -->
					<button data-oper='list' class="btn btn-info">리스트</button>
					<!-- onclick="location.href='/board/list'" -->
					
					<!-- 수정 버튼을 클릭시 bno를 가지고 가도록 설정 -->
					<form id='operForm' action="/board/modify" method="get">
						<input type='hidden' id='bno' name='bno' value='<c:out value="${ board.bno }"/>'>
					</form>
						
					<!-- </from> -->
			
			</div> <!-- .panel-body end -->
		</div> <!-- .panel panel-default end -->
	</div> <!-- .col-lg-12 end -->
</div> <!-- .row end -->


<script type="text/javascript">
$(document).ready(function() {
  
  var operForm = $("#operForm"); /* <form id='operForm' action="/board/modify" method="get"> */
  
  $("button[data-oper='modify']").on("click", function(e){
    
    operForm.attr("action","/board/modify").submit();
    
  });
  
    
  $("button[data-oper='list']").on("click", function(e){
    
    operForm.find("#bno").remove(); /* input에 있는 bno를 삭제 */
    operForm.attr("action","/board/list")
    operForm.submit();
    
  });  
});
</script>
