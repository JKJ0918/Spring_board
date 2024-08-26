package org.zerock.domain;

import lombok.Data;

@Data
public class Ticket {
	//url을 통해서 json 타입으로 받아 객체처리 테스트
	
	private int tno; // 티켓 번호
	private String owner; // 티켓 주인
	private String grade; // 티켓 랭크
}
