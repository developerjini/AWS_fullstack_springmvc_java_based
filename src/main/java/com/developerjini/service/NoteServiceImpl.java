package com.developerjini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.developerjini.domain.noteVO;
import com.developerjini.mapper.NoteMapper;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService{
	private NoteMapper mapper;
	
	@Override
	public int send(noteVO vo) {
		// TODO Auto-generated method stub
		return mapper.insert(vo);
	}

	@Override
	public noteVO get(Long noteno) {
		// TODO Auto-generated method stub
		return mapper.selectOne(noteno);
	}

	@Override
	public int receive(Long noteno) {
		// TODO Auto-generated method stub
		return mapper.update(noteno);
	}

	@Override
	public int remove(Long noteno) {
		// TODO Auto-generated method stub
		return mapper.delete(noteno);
	}

	@Override
	public List<noteVO> getSendList(String id) {
		// TODO Auto-generated method stub
		return mapper.sendList(id);
	}

	@Override
	public List<noteVO> getReceiveList(String id) {
		// TODO Auto-generated method stub
		return mapper.receiveList(id);
	}

	@Override
	public List<noteVO> getReceiveUncheckedList(String id) {
		// TODO Auto-generated method stub
		return mapper.receiveUncheckedList(id);
	}
	
}
