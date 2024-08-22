<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- jstl 코어 태그용 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- jstl 포메팅 태그용 -->

<%@ include file="../includes/header.jsp" %>

<!-- list.jsp에서 /board/register 경로를 호출하면 get메서드가 실행 폼 박스가 나옴 -->
<!-- 입력완료를 누르면 vo객체가 만들어 져서 /board/register에 post 메서드가 실행 -->

<div class="row">
	<div class="cal-lg-12">
		<h1 class="page-header"> 게시판 글 등록 페이지 </h1>
	</div> <!-- .col-lg-12 end -->
</div> <!-- .row end -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Register</div>
			<div class="panel-body">
				<!-- form박스 만들고 submit 처리 -->
				<form role="form" action="/board/register" method="post">
				
					<div class="form-group">
						<label>제목</label>
						<input class="form-control" name="title" />
					</div> <!-- title.form-group end -->
					
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="3" name="content">
						</textarea>
					</div> <!-- content.form-group end -->
					
					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name="writer"/>
					</div><!-- writer.form-group end -->
					
					<button type="submit" class="btn btn-default">저장</button>
					<button type="reset" class="btn btn-default">초기화</button>
				</form> <!-- form end -->
					
			
			</div> <!-- .panel-body end -->
		</div> <!-- .panel panel-default end -->
	</div> <!-- .col-lg-12 end -->
</div> <!-- .row end -->