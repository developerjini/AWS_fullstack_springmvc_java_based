package com.developerjini.service;

import java.util.List;

import com.developerjini.domain.AttachFileDTO;
import com.developerjini.domain.BoardVO;
import com.developerjini.domain.Criteria;

public interface BoardService {
	void register(BoardVO vo);
	
	BoardVO get(Long bno);
	
	boolean modify(BoardVO vo);
	
	boolean remove(Long bno);
	
//	List<BoardVO> getList();
	
	List<BoardVO> getList(Criteria cri);
	
	int getTotalCnt(Criteria cri); 
	
	String deleteFile(AttachFileDTO dto);
}
