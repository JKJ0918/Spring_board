<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajax를 이용한 파일업로드</title>
</head>
<body>
<h1>Upload with Ajax</h1>

<div class='uploadDiv'>
	<input type='file' name='uploadFile' multiple>
</div>


<button id='uploadBtn'>Upload</button>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous">
</script> <!-- 외부 js 삽입 -->

<script>
/* $(document).ready(function(){ // HTML 문서가 로드 되고 DOM이 준비되었을 때 실행할 코드 지정
	
	$("#uploadBtn").on("click", function(e){ // #uploadBtn ID를 가진 버튼이 클릭되었을 때 실행되는 함수
		
		var formData = new FormData();// FormData 객체는 폼에 포함된 데이터를 쉽게 보내기 위해 사용하는 객체
		var inputFile = $("input[name='uploadFile']"); // name속성이 uploadFile인 input요소 선택
		var files = inputFile[0].files; // 사용자가 선택한 Filelist 객체
		
		console.log(files); // F12 개발자 도구 콘솔에 파일 목록이 찍힘
		
		//add filedata to formdata
		for (var i = 0; i < files.length; i++) {
			
			formData.append("uploadFile", files[i]); 
			// append 메서드 - 키-값 쌍으로 데이터 추가
			//"iploadFile" - 서버에 전송될 데이터 키(name)
			//files[i] - 실제로 전송될 파일 데이터
		}//filedata to formdata end
		
		
		$.ajax({ // ajax를 통한 formData 자체전송
			url:'uploadAjaxAction',
			processData : false,
			contentType : false,
			data : formData,
			type : 'POST',
			success : function(result){
				alert("uploaded");
			}
		})// $.end ajax
		
	});
}); */

var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
var maxSize = 5242880; //5MB

function checkExtension(fileName, fileSize) { // 파일 확장자나 크기의 사전 처리

	if (fileSize >= maxSize) {
		alert("파일 사이즈 초과");
		return false;
	}

	if (regex.test(fileName)) {
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return false;
	}
	return true;
}

$("#uploadBtn").on("click", function(e) {

	var formData = new FormData();

	var formData = new FormData();

	var inputFile = $("input[name='uploadFile']");

	var files = inputFile[0].files;

	//console.log(files);

	for (var i = 0; i < files.length; i++) {

		if (!checkExtension(files[i].name, files[i].size)) {
			return false;
		}

		formData.append("uploadFile", files[i]);

	}

	 $.ajax({
	 url: '/uploadAjaxAction',
	 processData: false, 
	 contentType: false,
	 data: formData,
	 type: 'POST',
	 dataType : 'json',
	 success: function(result){
		 
	 // alert("Uploaded");
	 console.log(result)

	 }
	 }); //$.ajax */
});

</script> 
	

</body>
</html>