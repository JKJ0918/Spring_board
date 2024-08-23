package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

// 비즈니스 로직을 처리하는 용도, 여러객체를 믹스하여 구현


public interface BoardService { // 각 계층 간의 연결은 인터페이스를 이용해서 느슨한 연결(결합)을 한다.
	// 인터페이스는 조장이 메서드 명을 정하는 느낌으로 작성
	// 인터페이스를 구현하는 구현 클래스는 조장이 만든 이름을 메서드화 하여 실제 코드를 작성한다.
	
	// 게시판의 C, R(ALL, ONE), U, D를 기본으로 설정
	// mapper는 db용어를 많이 사용하고, service는 실제용어를 많이 사용함
	
	public void register(BoardVO board);	// 게시글 등록
	
	public BoardVO get(Long bno);			// 1개의 게시글을 가져옴
	
	public boolean modify(BoardVO board);	// 게시글 수정
	
	public boolean remove(Long bno);		// 1개의 게시글 삭제
	
	// public List<BoardVO> getlist();			// 게시글의 전체 내용을 리스트로 출력
	
	public List<BoardVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
	
}
