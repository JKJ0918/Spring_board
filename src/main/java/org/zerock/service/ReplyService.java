package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyService { // 구현 클래스 필수 ReplyServiceImpl.java
	
	public int register(ReplyVO vo); // 댓글 등록용 int 리턴
	
	public ReplyVO get(Long rno); // 댓글 1개 가져옴
	
	public int modify(ReplyVO vo); // 객체 수정 후 int 리턴
	
	public int remove(Long rno); // 댓글 1개 삭제 후 int 리턴
	
	public List<ReplyVO> getList(Criteria cti, Long bno); // 게시글을 이용해 모든 댓글을 리스트로 출력
	
}
