package com.xszymo.hibernate.controllers.operations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xszymo.hibernate.configuration.LoadAtStart;
import com.xszymo.hibernate.interfaces.AnswersMessageService;
import com.xszymo.hibernate.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.interfaces.UserService;
import com.xszymo.hibernate.tables.Answer;
import com.xszymo.hibernate.tables.Question;

@Controller
public class SaveDataToTxtController {
	public static final String PATH_QUESTIONS = LoadAtStart.PATH_QUESTIONS;
	
	@Resource(name = "answerMessageService")
	AnswersMessageService answer;

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;
	
	@RequestMapping("saveToTxtFile")
	public void saveAllMessages() throws IOException {
		String everything = "";
		for(Question x : question.findAll()) {
			for(Answer x1 : x.getAnswers()) {
				everything += x.getMessage() + " -.|.- " + x1.getMessage() + "\n";
			}
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_QUESTIONS));
		writer.write(everything);
		if (writer != null)
			writer.close();
	}
}
