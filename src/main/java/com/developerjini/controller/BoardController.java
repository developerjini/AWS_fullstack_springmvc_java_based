package com.developerjini.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developerjini.domain.AttachVO;
import com.developerjini.domain.BoardVO;
import com.developerjini.domain.Criteria;
import com.developerjini.domain.PageDto;
import com.developerjini.service.BoardService;

import lombok.Data;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("board/*")
@Data
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("list")
	public void list(Model model, Criteria cri) {
		log.info("list()");
		log.info(cri);
		model.addAttribute("list", boardService.getList(cri));
		model.addAttribute("page", new PageDto(boardService.getTotalCnt(cri), cri));
	}
	
	@GetMapping({"get", "modify"})
	public void get(Model model, Long bno, @ModelAttribute("cri") Criteria cri) {
		log.info("get() or modify()");
		log.info(bno);
		log.info(cri);		
		model.addAttribute("cri", cri);
		model.addAttribute("board", boardService.get(bno));
	}
	
	@GetMapping("{bno}")
	public String getByPath(Model model, @PathVariable Long bno) {
		log.info("get() or modify()");
		log.info(bno);
		model.addAttribute("board", boardService.get(bno));
		return "board/get";
	}
	
	@GetMapping("register")
	@PreAuthorize("isAuthenticated()")
	public void register() {}
	
	@GetMapping("register2")
	public void register2() {}
	
	@PostMapping("register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		log.info("register");
		log.info(vo);
		boardService.register(vo);
//		rttr.addFlashAttribute("bno",vo.getBno());
		rttr.addFlashAttribute("result",vo.getBno());
		rttr.addAttribute("pageNum", 1);
		rttr.addAttribute("amount",cri.getAmount());
		return "redirect:/board/list";
	}
	
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.writer")
	@PostMapping("modify")
	public String modify(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		// 원래는 컨트롤러에서 하는 것이 좋음(트랜잭션이 끝난상태)
		// 원본 리스트
		List<AttachVO> originList = boardService.get(vo.getBno()).getAttachs(); // 사용되지 않는 코드, 서비스에서 다 처리됨
				// 수정될 리스트
		List<AttachVO> list = vo.getAttachs(); // 사용되지 않는 코드, 서비스에서 다 처리됨
		
		
		log.info("modify");
		log.info(vo);
		log.info(cri);
		if(boardService.modify(vo)) {
			rttr.addFlashAttribute("result", "success");
			// 수정시에 파일이 삭제됨
//			originList.stream().filter(v -> {
//				boolean flag = false;
//				for(AttachVO vo2 : list) {
//					vo2.getUuid().equals(v.getUuid());
//				}
//				return !flag;
//				}).forEach(boardService::deleteFile);;
			
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@PreAuthorize("isAuthenticated() and principal.username eq #writer")
	@PostMapping("remove")
	public String remove(String writer, Long bno, RedirectAttributes rttr, Criteria cri) {
		log.info("modify");
		log.info(bno);
		log.info(cri);
		List<AttachVO> list = boardService.get(bno).getAttachs();
		if(boardService.remove(bno)) {
//			list.forEach(boardService::deleteFile);
			for(AttachVO vo:list) {
				boardService.deleteFile(vo);
			}
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
}
