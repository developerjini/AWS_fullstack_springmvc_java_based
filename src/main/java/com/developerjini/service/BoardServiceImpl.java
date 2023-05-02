package com.developerjini.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developerjini.controller.UploadController;
import com.developerjini.domain.AttachFileDTO;
import com.developerjini.domain.AttachVO;
import com.developerjini.domain.BoardVO;
import com.developerjini.domain.Criteria;
import com.developerjini.mapper.AttachMapper;
import com.developerjini.mapper.BoardMapper;
import com.developerjini.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@ToString
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	private ReplyMapper replyMapper;
	private AttachMapper attachMapper;
	private BoardMapper boardMapper;
	
	
	@Override
	@Transactional
	public void register(BoardVO vo) {
		// TODO Auto-generated method stub
		boardMapper.insertSelectKey(vo);
		Long bno = vo.getBno();
		List<AttachVO> list = vo.getAttachs();
		int idx = 0;
		for(AttachVO attach : list) {
			attach.setBno(bno);
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
	}

	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		return boardMapper.read(bno);
	}

	@Override
	@Transactional
	public boolean modify(BoardVO vo) {
		
/*		// 원래는 컨트롤러에서 하는 것이 좋음(트랜잭션이 끝난상태)
		// 원본 리스트
		List<AttachVO> originList = boardService.get(vo.getBno()).getAttachs();
		
		// 수정될 리스트
		List<AttachVO> list = vo.getAttachs();
		*/
		attachMapper.deleteAll(vo.getBno());		
		List<AttachVO> list = vo.getAttachs();
		int idx = 0;
		for(AttachVO attach : list) {
			attach.setBno(vo.getBno());
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
		return boardMapper.update(vo) > 0;
	}

	@Override
	@Transactional
	public boolean remove(Long bno) {
		replyMapper.deleteByBno(bno);
		attachMapper.deleteAll(bno);
		// TODO Auto-generated method stub
		return boardMapper.delete(bno) > 0;
	}
	@Override
	public List<BoardVO> getList(Criteria cri) {
		// TODO Auto-generated method stub
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotalCnt(Criteria cri) {
		return boardMapper.getTotalCnt(cri);
	}

	@Override
	public String deleteFile(AttachFileDTO dto) {
		log.info(dto);

		String s = UploadController.getPATH() + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();
		
		File file = new File(s);
		file.delete();
		if(dto.isImage()) {
			s =  UploadController.getPATH() + "/" + dto.getPath() + "/s_" + dto.getUuid() + "_" + dto.getName();
			file = new File(s);
			file.delete();
		}
		log.info(file);
		return dto.getUuid();
	}
	
	
	
	
}
