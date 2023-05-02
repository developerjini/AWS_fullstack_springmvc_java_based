package com.developerjini.handler;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.developerjini.domain.MemberVO;
import com.developerjini.domain.noteVO;
import com.developerjini.service.NoteService;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


// ServerSocket
@Log4j
@EnableWebSocket
public class NoteHandler extends TextWebSocketHandler{
	// 접속자 관리 객체가 필요
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// A B C가 들어왔있다면
	@Autowired
	private NoteService noteService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		log.warn("입장한 사람 : " + getIdBySession(session));
		sessions.add(session);
//		log.warn("현재 접속자 수 " + sessions.size());
//		log.warn("현재 접속자 정보 ");
		sessions.forEach(log::warn);
		
		log.warn(sessions.stream().map(s->getIdBySession(s)).collect(Collectors.toList()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		log.warn(session.getId() + " 로그아웃");
		sessions.remove(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.warn(noteService);
		
		String receiver = message.getPayload(); // js측면에서 ws.send() 수신자(정보가 필요)
		String sender = getIdBySession(session);
		List<noteVO> list0 = noteService.getSendList(getIdBySession(session));
		List<noteVO> list1 = noteService.getReceiveList(receiver);
		List<noteVO> list2 = noteService.getReceiveUncheckedList(receiver);
		
		Map<String, Object> map = new HashMap<>();
		map.put("sendList", list0);
		map.put("receiveList", list1);
		map.put("receiveUncheckedList", list2);
		map.put("sender", sender);
		
		Gson gson = new Gson();
		
		for(WebSocketSession s : sessions) {
			if(receiver.equals(getIdBySession(s)) || session == s) {
				s.sendMessage(new TextMessage(gson.toJson(map)));
			}
		}
		// A > B에게 발송, 확인은 B가
	}
	
	private String getIdBySession(WebSocketSession session) {
		Object obj = session.getAttributes().get("member");
		String id = null;
		if(obj != null && obj instanceof MemberVO) {
//			id = ((MemberVO)obj).getId();
		}
		return id;
	}
}
