<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- jstl 코어 태그용 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- jstl 포메팅 태그용 -->

<%@ include file="../includes/header.jsp" %>

<!-- list.jsp에서 /board/register 경로를 호출하면 get메서드가 실행 폼 박스가 나옴 -->
<!-- 입력완료를 누르면 vo객체가 만들어 져서 /board/register에 post 메서드가 실행 -->

<div class="row">
	<div class="cal-lg-12">
		<h1 class="page-header"> 게시판 글 수정 페이지 </h1>
	</div> <!-- .col-lg-12 end -->
</div> <!-- .row end -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-warning">
			<div class="panel-heading">Board Modify Page</div>
			<!-- <div class="panel-body"> -->
				<!-- form박스 만들고 submit 처리 -->
				<form role="form" action="/board/modify" method="post">
					<!-- 페이징처리 관련 추가 -->
					<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
					<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
					<input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>
					<input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
					
					<div class="form-group">
						<label>번호</label>
						<input class="form-control" name="bno" value='<c:out value="${ board.bno }"/>' readonly="readonly"> <!-- pk이기 때문에 놔둔다. -->
						
					</div>
					
					<div class="form-group">
						<label>제목</label>
						<input class="form-control" name="title" value='<c:out value="${ board.title }"/>' >
					</div> <!-- title.form-group end -->
					
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="3" name="content" >
						<c:out value="${ board.content }" /></textarea>
					</div> <!-- content.form-group end -->
					
					<div class="form-group">
						<label>작성자</label>
						<input class="form-control" name="writer"  value='<c:out value="${ board.writer }"/>' readonly="readonly">
					</div><!-- writer.form-group end -->
					
 					<div class="form-group">
						<label>등록일</label>
						<input class="form-control" name="regdate"  value='<c:out value="${ board.regdate }"/>' readonly="readonly">
					</div><!-- regdate.form-group end -->
					
					<div class="form-group">
						<label>수정일</label>
						<input class="form-control" name="updateDate"  value='<c:out value="${ board.updateDate }"/>' readonly="readonly">
					</div><!-- updateDate.form-group end -->  <!-- 등록일, 작성일 데이터가 안넘어가서 생기는 문제로 파악됨 -->
					
					<button type="submit" data-oper='modify' class="btn btn-primary">수정</button>
					<button type="submit" data-oper='remove' class="btn btn-danger">삭제</button>
					<button type="submit" data-oper='list' class="btn btn-info">리스트</button>
					<!-- submit이 많은 경우에는 java script(js)를 이용하여 분기처리 -->
					
					
				</form> <!-- form end -->
					
			
			</div> <!-- .panel-body end -->
		</div> <!-- .panel panel-default end -->
	</div> <!-- .col-lg-12 end -->
</div> <!-- .row end -->



<script type="text/javascript">
$(document).ready(function() {


	  var formObj = $("form"); /* 상단 코드중에 form태그를 formObj 관여 하겠다. */

	  $('button').on("click", function(e){
	    
	    e.preventDefault(); // button 기본 사용을 안함. submit 안됨
	    
	    var operation = $(this).data("oper"); /* data-oper='modify' , data-oper='remove', data-oper='list' */
	    
	    console.log(operation); /* 개발자 도구에 콘솔에 찍힘 */
	    
	    if(operation === 'remove'){ /* data-oper='remove' */
	      formObj.attr("action", "/board/remove");  /* 삭제 컨트롤러를 요청 */
	      
	    }else if(operation === 'list'){
	      //move to list
	      formObj.attr("action", "/board/list").attr("method","get");
	      
	      var pageNumTag = $("input[name='pageNum']").clone();
	      var amountTag = $("input[name='amount']").clone();
	      var keywordTag = $("input[name='keyword']").clone();
	      var typeTag = $("input[name='type']").clone();      
	      
	      formObj.empty();
	      
	      formObj.append(pageNumTag);
	      formObj.append(amountTag);
	      formObj.append(keywordTag);
	      formObj.append(typeTag);	       
	    }
	    
	    formObj.submit(); /* data-oper='modify' 23행 실행 */
	  });

});
</script>