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

<div class='bigPictureWrapper'> <!-- 실제 원본 이미지를 보여주는 영역 -->
	<div class='bigPicture'>
	</div>
</div>

<style> /* 폴더 아이콘 */
.uploadResult {
	width: 100%;
	background-color: gray;
}

.uploadResult ul {
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
	align-content : center;
	text-align : center;
}

.uploadResult ul li img {
	width: 100px;
}
.uploadResult ul li span {
	color:white;
}
.bigPictureWrapper{
	position : absolute;
	display : none;
	justify-content : center;
	align-items : center;
	top : 0%;
	width : 100%;
	height : 100%;
	background-color : gray;
	z-index : 100;
	background:rgba(255, 255, 255, 0.5);
}
.bigPicture{
	position : relative;
	display : flex;
	justify-content : center;
	align-items : center;
}
.bigPicture img{
	width : 600px;
}

</style>


<div class='uploadDiv'>
	<input type='file' name='uploadFile' multiple>
</div>
		<div class='uploadResult'>
			<ul>
	
			</ul>
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

function showImage(fileCallPath){	// 이미지 클릭시 원본 이미지 관련
	
	//alert(fileCallPath);
	// jQuery를 사용하여 HTML에서 class가 bigPictureWrapper인 요소를 선택
	// 택된 요소의 CSS display 속성을 "flex"로 설정
	$(".bigPictureWrapper").css("display", "flex").show();
	
	$(".bigPicture") //class가 bigPicture인 요소를 선택
	.html("<img src='/display?fileName= " + encodeURI(fileCallPath) + " '>") // 선택한 요소의 HTML 내용을 설정, 
	// <img> 태그를 생성하고 그 src 속성에 원본 이미지의 경로를 설정합니다. fileCallPath는 인코딩된 파일 경로로 변환되어 URL에 사용
	.animate({width:'100%', height:'100%'}, 1000);
}

$(".bigPictureWrapper").on("click", function(e){ // 원본 이미지 다시한번 클릭하면 사라지는 메서드
	  $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
 	 /* setTimeout(() => {
	    $(this).hide();
	  }, 1000); */
	  
	  setTimeout(function(){ // setTimeot() => 가 IE 11에서 제대로 작동안할 떄 쓰는 코드
		$('.bigPictureWrapper').hide();  
	  }, 1000);
});

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

var uploadResult = $(".uploadResult ul");

function showUploadedFile(uploadResultArr){ // 업로된 이미지 처리
	
	var str = "";

	$(uploadResultArr).each(function(i, obj){
		
		if(!obj.image){ // 파일이 이미지 가 아닐 경우 해당 아이콘을 출력하게 됨
			
			var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName); //URI 호출에 적합한 문자열로 인코딩
			
			str += "<li><a href='/download?fileName=" + fileCallPath + "'><img src='/resources/img/attach.png'>" + obj.fileName + "</a></li>"			
					
		}else{
			
			var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
			
			var originPath = obj.uploadPath+ "\\"+obj.uuid + "_" + obj.fileName;
			
			originPath = originPath.replace(new RegExp(/\\/g),"/"); // \표시를 /로 대체 
			
			// str += "<li><img src='/display?fileName="+fileCallPath+"'><li>"; //uploadController 의 getFile 메서드를 통해 파일의 이미지 데이터 전송	
			
			str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a></li>";
		}
		
	});
	
	uploadResult.append(str); //apend -> 컨텐츠를 선택된 요소 내부의 끝 부분에서 삽입
}

var cloneObj = $(".uploadDiv").clone(); //<input type='file'> 의 초기화 

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
	 console.log(result); // <input type='file'> 의 초기화 
	 
	 showUploadedFile(result); // 업로드 된 이미지 처리
	 
	 $(".uploadDiv").html(cloneObj.html());
	 
	 
	 }
	}); //$.ajax */
});



</script> 
	

</body>
</html>