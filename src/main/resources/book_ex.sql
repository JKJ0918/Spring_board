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