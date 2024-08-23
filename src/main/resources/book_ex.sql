create sequence seq_board; -- 자동번호 객체 생성

create table tbl_board (
	bno number(10,0),
	title varchar2(200) not null,
	content varchar2(2000) not null,
	writer varchar2(50) not null,
	regdate date default sysdate,
	updatedate date default sysdate
); -- tbl_board 테이블 생성(번호, 제목, 내용, 작성자, 작성일, 수정일)

alter table tbl_board add constraint pk_board primary key (bno);

select*from tbl_board;

-- tbl_board 더미 데이터 입력
insert into tbl_board (bno, title, content, writer)
				values(seq_board.nextval, '테스트 제목1', '테스트 내용1', 'user01');
insert into tbl_board (bno, title, content, writer)
				values(seq_board.nextval, '테스트 제목2', '테스트 내용2', 'user02');
insert into tbl_board (bno, title, content, writer)
				values(seq_board.nextval, '테스트 제목3', '테스트 내용3', 'user03');
insert into tbl_board (bno, title, content, writer)
				values(seq_board.nextval, '테스트 제목4', '테스트 내용4', 'user04');
insert into tbl_board (bno, title, content, writer)
				values(seq_board.nextval, '테스트 제목5', '테스트 내용5', 'user05');

--재귀 복사를 통해서 데이터의 개수를 늘림. 반복해서 여러 번 실행
insert into tbl_board (bno, title, content, writer)
(select seq_board.nextval, title, content, writer from tbl_board);

-- select * from 비교
select * from tbl_board order by bno + 1 desc; -- 63ms
select * from tbl_board order by bno desc; -- 47ms
select /*+ INDEX_DESC(tbl_board pk_board) */ * from tbl_board where bno > 0; -- 32ms 힌트 방식 사용
select /*+ FULL(tbl_board)*/ * from tbl_board order by bno desc; -- 62ms FULL힌트 
select rownum rn, bno, title from tbl_board;
select /*+ FULL(tbl_board)*/ rownum rn, bno, title from tbl_board where bno > 0 order by bno;

select /*+INDEX_DESC(tbl_board pk_board) */ rownum rn, bno, title, content from tbl_board where rownum <= 10; -- 한 페이지당 10개의 데이터를 출력 하는 명령어

select bno, title, content from (
	select /*+INDEX_DESC(tbl_board pk_board) */ rownum rn, bno, title, content from tbl_board where rownum <=20
)
where rn > 10; -- 1페이지 10개, 2페이지 10개 총 20개 일때, 2페이지의 10개를 출력하는 명령어


