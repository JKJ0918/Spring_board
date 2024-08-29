package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
// Java Config
// @ContextConfiguration(classes = {org.zerock.config.RootConfig.class} )
@Log4j2
public class ReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Test
	public void testMapper() {
		
		log.info(mapper);
		// INFO  org.zerock.mapper.ReplyMapperTests(testMapper30) - org.apache.ibatis.binding.MapperProxy@148c7c4b
	}
	
	@Test
	public void testCreate() {
		ReplyVO vo = new ReplyVO();
		vo.setBno(11L);
		vo.setReply("매퍼댓글테스트");
		vo.setReplyer("매퍼kkw");
		
		mapper.insert(vo);
	}
	
	@Test
	public void testRead() {
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info("select" + vo);
		//selectReplyVO(rno=10, bno=8, reply=댓글8, replyer=kkw, replyDate=Tue Aug 27 10:52:28 KST 2024, updateDate=Tue Aug 27 10:52:28 KST 2024)
		//     ^여기 까지 가 "select" 부분 
	}
	
	@Test
	public void testUpdate() {
	
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno); // 10번 객체를 가져와
		
		vo.setReply("메퍼로 수정 테스트");
		
		int count = mapper.update(vo); // 수정된 객체를 넣고 결과를 받음
		
		log.info("수정된 카운트 : " + count);
		log.info("수정된 객체 :" + vo);
		
		//INFO  org.zerock.mapper.ReplyMapperTests(testUpdate68) - 수정된 카운트 : 1
		//INFO  org.zerock.mapper.ReplyMapperTests(testUpdate69) - 수정된 객체 :ReplyVO(rno=10, bno=8, reply=메퍼로 수정 테스트, replyer=kkw, replyDate=Tue Aug 27 10:52:28 KST 2024, updateDate=Tue Aug 27 10:52:28 KST 2024)

	}
	
	@Test
	public void testDelete() {
		
		Long targetRno = 1L;
		
		log.info("삭제후 결과 : " + mapper.delete(targetRno));
	}
	
	// 번호를 이용한 객체가 리스트로 나옴
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		log.info("Criteria : " + cri);
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 10L);
		
		replies.forEach(reply -> log.info(reply));
		// INFO  org.zerock.mapper.ReplyMapperTests(lambda$092) - ReplyVO(rno=2, bno=10, reply=댓글10, replyer=kkw, replyDate=Tue Aug 27 10:52:06 KST 2024, updateDate=Tue Aug 27 10:52:06 KST 2024)
		// INFO  org.zerock.mapper.ReplyMapperTests(lambda$092) - ReplyVO(rno=8, bno=10, reply=댓글10, replyer=kkw, replyDate=Tue Aug 27 10:52:26 KST 2024, updateDate=Tue Aug 27 10:52:26 KST 2024)
		
	}
	
	// 댓글 페이징 테스트 1
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1, 10);
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 11L);
		
		replies.forEach(reply -> log.info(reply));
	}
	

}
