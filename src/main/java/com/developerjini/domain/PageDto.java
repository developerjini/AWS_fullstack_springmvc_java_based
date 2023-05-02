package com.developerjini.domain;

import lombok.Data;

@Data
public class PageDto {
	// 하단에 출력 될 페이지 사이즈
	private int pageCount = 10;
	// 시작 페이지 숫자 
	private int startPage;
	// 종료 페이지 숫자
	private int endPage;
	// 게시글 총 갯수
	private int total;
	// next, previous
	private boolean prev;
	private boolean next;
	
	private boolean doublePrev;
	private boolean doubleNext;
	// Criteria
	private Criteria cri;
	
	public PageDto(int total, Criteria cri) {
		this(10, total, cri);
	}

	public PageDto(int pageCount, int total, Criteria cri) {
		this.pageCount = pageCount;
		this.total = total;
		this.cri = cri;
//		cri.getAmount()
//		cri.getPageNum()
//		total
		// 3 > 10
		// 1 > 10
		endPage = (cri.getPageNum() + (pageCount-1)) / pageCount * pageCount;
		startPage = endPage - (pageCount-1);
		int realEnd = (total+(cri.getAmount()-1)) / cri.getAmount();
//		System.out.println(realEnd);
		if(endPage > realEnd) {
			endPage = realEnd;
		}
		prev = cri.getPageNum() > 1;
		next = cri.getPageNum() < realEnd;
		
		doublePrev = startPage > 1;
		doubleNext = endPage < realEnd;
	}
	public static void main(String[] args) {
//		Criteria cri = new Criteria(11, 10, 1);
//		PageDto page = new PageDto(223, cri);
//		System.out.println(page);
//		System.out.println(new PageDto(121, new Criteria(1, 25)).endPage);
//		System.out.println(new PageDto(121, new Criteria(1, 50)).endPage);
	}
	// 예정, <<, >>
	
}
