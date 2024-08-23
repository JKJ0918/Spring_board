package org.zerock.mapper; //db와 영속성을 가진 패키지

import java.util.List;

// import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	// interface로 선언하는 이유는 추상메서드와 xml을 결합하여 구현 클래스를 사용하는 마이바티스
	// xml을 생성할 때는 resources 안에 폴더를 계층별로 만들고 파일명을 인터페이스와 같이 xml을 생성
	
	// 인터페이스에 자체적인 추상메서드를 활용
	//@Select("select * from tbl_board where bno > 0") // where bno > 0 -> bno가 pk라 인덱싱이 되어 있어 빠름
	public List<BoardVO> getList(); // 인터페이스안에는 추상메서드임. 이걸 호출하면 select문이 돈다
	// 리턴은 List<BoardVO> 임으로 배열 안쪽에 객체가 BoardVO로 완성 됨
	
	//CREATE
	// board 삽입용 코드
	public void insert(BoardVO board);
	
	
	// 삽입할 번호를 먼저 파악후 게시물 등록
	public void insertSelectKey(BoardVO board);
	
	//READ
	// 게시물의 번호를 받아 객체를 출력한다.
	public BoardVO read(Long bno);
	
	//UPDATE
	// 게시물의 번호를 받아 객체를 수정한다.
	public int update(BoardVO boardVO);
	
	//DELETE
	// 게시물의 번호를 받아 객체를 삭제한다.
	public int delete(Long bno);
	
	//페이징 처리 관련
	public List<BoardVO> getListWithPaging(Criteria cri);

	//페이징 처리 관련 2
	public int getTotalCount(Criteria cri);
	
}
