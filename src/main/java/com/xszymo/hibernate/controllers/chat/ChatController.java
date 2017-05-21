package com.xszymo.hibernate.controllers.chat;

import java.util.Collections;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.xszymo.hibernate.interfaces.AnswersMessageService;
import com.xszymo.hibernate.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.interfaces.UserService;
import com.xszymo.hibernate.tables.Answer;
import com.xszymo.hibernate.tables.Question;

@Controller
@RequestMapping("/mvc/chat")
public class ChatController {

	@Resource(name = "answerMessageService")
	AnswersMessageService answer;

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;

	@Autowired
	UserService userService;

	private final Chat chatRepository = new Chat();

	private final Map<DeferredResult<List<String>>, Integer> chatRequests = new ConcurrentHashMap<DeferredResult<List<String>>, Integer>();

	@RequestMapping
	@ResponseBody
	public DeferredResult<List<String>> getMessages(@RequestParam int messageIndex) {

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

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void postMessage(@RequestParam String message) {
		Question a = question.findByMessage(message);
		String botMessage = "Not implemented yet :(";

		if (a != null) {
			System.out.println(a.getMessage());
			long b = 0;
			for (Answer x : a.getAnswers())
				if (b <= x.getCounter()) {
					botMessage = x.getMessage();
					b = x.getCounter();
				}
		}

		this.chatRepository.addMessage("You : " + message);
		this.chatRepository.addMessage("Bot : " + botMessage);

		for (Entry<DeferredResult<List<String>>, Integer> entry : this.chatRequests.entrySet()) {
			List<String> messages = this.chatRepository.getMessages(entry.getValue());
			entry.getKey().setResult(messages);
		}
	}

}
