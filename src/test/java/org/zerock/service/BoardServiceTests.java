package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)  // 메서드별 테스트용 JUnit4
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") // 참고할 파일
@Log4j2
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	private BoardService service; // setService(BoardService)
	
	@Test
	public void testExist() {
		//객체 생성 유무 판단용 테스트
		log.info(service); // 인터페이스를 필드로 생성 하였는데 서비스 객체를 실행하면 Impl이 붙은 클래스가 실행됨
		assertNotNull(service);
	}
	
	@Test
	public void testRegister() {
		
		BoardVO board = new BoardVO(); // 빈 객체 생성
		board.setTitle("서비스로 만든 제목");
		board.setContent("서비스로 만든 내용");
		board.setWriter("서비스 사용자");
		
		service.register(board);
		
		log.info("등록된 게시물의 번호 : " + board.getBno());
	}
	
	@Test
	public void testGet() {
		log.info(service.get(1L));
	}
	
	@Test
	public void testGetList() {
		service.getlist().forEach(board -> log.info(board));
	}
	
	@Test
	public void testUpdate() {
		
		BoardVO board = service.get(1L);
		
		if(board == null) {
			log.info("찾는 객체가 없습니다.");
			return;
		}
		board.setTitle("서비스에서 수정한 제목");
		log.info("서비스에서 수정메서드 결과 : " + service.modify(board)); // boolean
		
	}
	
	@Test
	public void testDelete() {
		log.info("삭제된 결과 :" + service.remove(2L));
	}
	
	
}
