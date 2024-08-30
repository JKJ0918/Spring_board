package org.zerock.controller;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

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
	
	//년/월/일 폴더의 생성 (한 폴더 내에 너무 많은 파일의 생성문제 해결) - mkdirs() 메서드 사용
	private String getFolder() {  //오늘 날짜의 경로를 문자열로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
		
	} // end getFolder
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {  //해당 경로가 있는지 검사 후 폴더 생성
		
		String uploadFolder = "D:\\upload"; // 파일 저장 경로
		
		//폴더 생성 --------
		File uploadPath = new File(uploadFolder, getFolder()); 
		log.info("upload path:" + uploadPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		//폴더 생성 (yyyy/MM/dd)
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("---------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename()); // 업로드파일 원래이름
			log.info("Upload File Size : " + multipartFile.getSize()); // 업로드 파일 사이즈 확인

			
			String uploadFileName = multipartFile.getOriginalFilename(); // 업로드 파일 이름 String 처리
			
			// IE에 파일 경로가 있을 경우
			// string 객체의 시작 인덱스로 부터 종료 인덱스 전 까지 문자열의 부분 문자열을 반환
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1); // 부분은 파일 이름에 경로가 포함되어 있을 경우, 파일 이름만 추출
			log.info("only file name : " + uploadFileName);
			
			//파일 이름 중복 방지 UUID
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile); // transferTo 메소드에 의해 저장 결로에 실질적으로 File이 생성됨
			} catch (Exception e) {
				log.error(e.getMessage());
			}// end catch

			
		}// end for
		
		
	}
	
	
	
}
