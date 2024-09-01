package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j2
public class UploadController {

	// <form> 방식의 파일 업로드 
	@GetMapping("/uploadForm") // 파일을 올림
	public void uploadForm() {
		log.info("upload form");
	}
	
	@PostMapping("/uploadFormAction") // 실질적인 파일 업로드 처리
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		//MultipartFile : File을 핸들러에서 손쉽게 다룰 수 있도록 도와주는 Handlar 매개변수
		String uploadFolder = "D:\\upload"; // 파일 저장 경로
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("------------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename()); // 파일 원본이름 확인
			log.info("Upload File Size : " + multipartFile.getSize()); // 업로드 파일 사이즈 확인

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			// 위 파일 저장 경로에, 멀티파트파일 형식으로 업로드된 파일의 원래 이름으로 저장하겠다.
			
			try {
				multipartFile.transferTo(saveFile); // transferTo 메소드에 의해 저장 결로에 실질적으로 File이 생성됨
			} catch (Exception e) {
				log.error(e.getMessage());
			}// end catch
			
			
		}// end for
		
	} //end uploadFormPost method
	
	//ajax를 이용하는 파일 업로드
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
/*	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		
		log.info("update ajax post............");
		
		String uploadFolder = "D:\\upload"; // 파일 저장 경로
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("---------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename()); // 업로드파일 원래이름
			log.info("Upload File Size : " + multipartFile.getSize()); // 업로드 파일 사이즈 확인

			
			String uploadFileName = multipartFile.getOriginalFilename(); // 업로드 파일 이름 String 처리
			
			// IE에 파일 경로가 있을 경우
			// string 객체의 시작 인덱스로 부터 종료 인덱스 전 까지 문자열의 부분 문자열을 반환
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // 부분은 파일 이름에 경로가 포함되어 있을 경우, 파일 이름만 추출
			log.info("only file name : " + uploadFileName);
			
			File saveFile = new File(uploadFolder, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile); // transferTo 메소드에 의해 저장 결로에 실질적으로 File이 생성됨
			} catch (Exception e) {
				log.error(e.getMessage());
			}// end catch
			
			
			
		}// end for
	}// end uploadAjaxPost */
	
	// 이미지 타입인지 검사 하는 메서드
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
		
	}// end checkImageType
	
	//년/월/일 폴더의 생성 (한 폴더 내에 너무 많은 파일의 생성문제 해결) - mkdirs() 메서드 사용
	private String getFolder() {  //오늘 날짜의 경로를 문자열로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
		
	} // end getFolder
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {  //해당 경로가 있는지 검사 후 폴더 생성
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "D:\\upload"; // 파일 저장 경로
		
		String uploadFolderPath = getFolder();
		//폴더 생성 --------
		File uploadPath = new File(uploadFolder, uploadFolderPath); 
		log.info("upload path:" + uploadPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		//폴더 생성 (yyyy/MM/dd)
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("---------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename()); // 업로드파일 원래이름
			log.info("Upload File Size : " + multipartFile.getSize()); // 업로드 파일 사이즈 확인
			
			AttachFileDTO attachDTO = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename(); // 업로드 파일 이름 String 처리
			
			// IE에 파일 경로가 있을 경우
			// string 객체의 시작 인덱스로 부터 종료 인덱스 전 까지 문자열의 부분 문자열을 반환
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // 부분은 파일 이름에 경로가 포함되어 있을 경우, 파일 이름만 추출
			log.info("only file name : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			//파일 이름 중복 방지 UUID
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
			File saveFile = new File(uploadPath, uploadFileName);
			multipartFile.transferTo(saveFile); // transferTo 메소드에 의해 저장 결로에 실질적으로 File이 생성됨
			
			attachDTO.setUuid(uuid.toString());
			attachDTO.setUploadPath(uploadFolderPath);
			
			if(checkImageType(saveFile)) {//이미지 타입 파일 체크
				
				attachDTO.setImage(true);
				
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
				Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
				thumbnail.close(); //섬네일용 이미지에 결론적으로 크기가 작아지고, 파일 이름에"_s"가 붙게 됨
			}// end if
			
			//리스트에 추가
			list.add(attachDTO);
			
			} catch (Exception e) {
				log.error(e.getMessage());
			}// end catch

			
		}// end for
		return new ResponseEntity<>(list, HttpStatus.OK); //ResponseBody의 리턴처리
		
	}// end uploadAjaxPost
	
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) { // 특정한 파일 이름을 받아서 이미지 데이터를 전송하는 코드
		
		log.info("fileName : " + fileName);
		
		File file = new File("D:\\upload\\" + fileName); // 파일의 경로가 포함된 fileName을 파라미터로 받음
		
		log.info("file: " + file);
		
		ResponseEntity<byte[]> result = null;
		
		HttpHeaders header = new HttpHeaders(); //응답 헤더를 설정하기 위한 객체 생성
		
		try {
			header.add("Content-Type", Files.probeContentType(file.toPath())); //MINE 타입 추론(probeContentType) 하여 'Content-Type'에 헤더 추가 (image/jpeg와 같은 타입이 설정됨)
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK); // 파일의 내용을 바이트 배열로 복사하여 ResponseEntity 에 담아 HTTP 상태 코드 200(OK)와 함께 반환
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}// end ResponseEntity
	
	//                                                     MINE 타입(binatry data) 응답으로 제공
	@GetMapping(value ="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody // 메서드의 반환값이 HTTP 응답의 본문으로 직접 전송되도록 설정
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){ // 첨부파일 다운로드 메서드
		//IE 서비스 : User-Agent 정보를 파라미터 값으로 수집하고, IE에 대한 처리 추가함.
		log.info("download file : " + fileName);
		
		Resource resource = new FileSystemResource("D:\\upload\\" + fileName);
		
		log.info("resource : " + resource);
		
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		// UUID 삭제
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1); // UUID 부분을 잘라낸 상태의 파일 이름이 저장
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				
				log.info("인터넷 익스플로러 브라우저");
				
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			}else if(userAgent.contains("Edge")) {
				
				log.info("엣지 브라우저");
				
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
				
				log.info("Edge name :" + downloadName);
				
			}else{
				
				log.info("크롬 브라우저");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				// 파일이름은  UTF-8로 인코딩되어 ISO-8859-1로 변환, 한글 파일 이름이나 특수 문자가 있는 경우에도 제대로 처리
			}
			
			// Content-Disposition 헤더를 추가하여,  브라우저가 파일을 다운로드할 때 파일 이름 지정,
			headers.add("Content-Disposition", "attachment; filename=" + downloadName );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		
	}// end downloadFile
	
	
}
