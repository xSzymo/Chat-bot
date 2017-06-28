package com.xszymo.hibernate.controllers.chat.ChatForAll;

import java.util.Collections;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import com.xszymo.hibernate.interfaces.AnswersMessageService;
import com.xszymo.hibernate.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.interfaces.UserService;

@RestController
@RequestMapping("/chat")
public class ChatController {
	public LinkedList<MyChat> myChat = new LinkedList<MyChat>();

	@Resource(name = "answerMessageService")
	AnswersMessageService answer;

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;

	@Autowired
	UserService userService;

	public Chat chatRepository = new Chat();

	public Map<DeferredResult<List<String>>, Integer> chatRequests = new ConcurrentHashMap<DeferredResult<List<String>>, Integer>();

	@GetMapping("createChatId")
	public String createChatId() {
		MyChat a = new MyChat();
		a.id = createId();
		myChat.add(a);
		System.out.println("id : " + a.id);
		return a.id;
	}

	@GetMapping
	public LinkedList<String> getMessages(@RequestParam String chatId, @RequestParam LinkedList<String> messages, @RequestParam String user) {
		MyChat chat = findOne(chatId);
		if(chat == null) {
			System.out.println("error");
			return messages;
		}

		if(chat.messages.size() != messages.size()) {
			//System.out.println("more");
		} else {
			//System.out.println("less");
			return messages;
		}
		return chat.messages;
	}


	@PostMapping
	public void postMessage(@RequestParam String chatId, @RequestParam String message, @RequestParam String user) {
		MyChat chat = findOne(chatId);
		if(chat == null) {
			System.out.println("error");
			return;
		}

		chat.messages.add(user + " : " + message);
	}

//	@GetMapping
//	public DeferredResult<List<String>> getMessages(@RequestParam int messageIndex, @RequestParam String user) {
//		final DeferredResult<List<String>> deferredResult = new DeferredResult<List<String>>(null,
//				Collections.emptyList());
//		this.chatRequests.put(deferredResult, messageIndex);
//
//		deferredResult.onCompletion(new Runnable() {
//			@Override
//			public void run() {
//				chatRequests.remove(deferredResult);
//			}
//		});
//
//		List<String> messages = this.chatRepository.getMessages(messageIndex);
//		if (!messages.isEmpty())
//			deferredResult.setResult(messages);
//
//		return deferredResult;
//	}



	public String createId() {
		String code;

		boolean cannotLeave;
		outerLoop:
		do {
			cannotLeave = false;
			code = Coder.coder();
			for (MyChat x : myChat) {
				if (x.id.equals(code))
					cannotLeave = true;
			}
		}while(cannotLeave);

		return code;
	}

	public MyChat findOne(String chatId) {
		for(MyChat x : myChat) {
			if(x.id.equals(chatId))
				return x;
		}
		return null;
	}
}
