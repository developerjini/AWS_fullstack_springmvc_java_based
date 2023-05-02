package com.developerjini.service;

import java.util.List;

import com.developerjini.domain.noteVO;

public interface NoteService {
	int send(noteVO vo);
	
	noteVO get(Long noteno);
	
	int receive(Long noteno);
	
	int remove(Long noteno);
	
	List<noteVO> getSendList(String id);
	
	List<noteVO> getReceiveList(String id);
	
	List<noteVO> getReceiveUncheckedList(String id);
}
