package com.xszymo.hibernate.controllers.chat.ChatForAll;

import java.util.Collections;
import org.springframework.web.bind.annotation.*;
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

	@Resource(name = "answerMessageService")
	AnswersMessageService answer;

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;

	@Autowired
	UserService userService;

	public Chat chatRepository = new Chat();

	public Map<DeferredResult<List<String>>, Integer> chatRequests = new ConcurrentHashMap<DeferredResult<List<String>>, Integer>();

	@PostMapping("createChatId")
	public String createChatId() {
		return "test";
	}

	@GetMapping("checkChatId")
	public String halo(@RequestParam String id) {
		return id;
	}

	@GetMapping
	public DeferredResult<List<String>> getMessages(@RequestParam int messageIndex, @RequestParam String user) {
		final DeferredResult<List<String>> deferredResult = new DeferredResult<List<String>>(null,
				Collections.emptyList());
		this.chatRequests.put(deferredResult, messageIndex);

		deferredResult.onCompletion(new Runnable() {
			@Override
			public void run() {
				chatRequests.remove(deferredResult);
			}
		});

		List<String> messages = this.chatRepository.getMessages(messageIndex);
		if (!messages.isEmpty())
			deferredResult.setResult(messages);

		return deferredResult;
	}

	@PostMapping
	public void postMessage(@RequestParam String message, @RequestParam String user) {
		this.chatRepository.addMessage(user + " : " + message);

		for (Entry<DeferredResult<List<String>>, Integer> entry : this.chatRequests.entrySet()) {
			List<String> messages = this.chatRepository.getMessages(entry.getValue());
			entry.getKey().setResult(messages);
		}
	}

}
