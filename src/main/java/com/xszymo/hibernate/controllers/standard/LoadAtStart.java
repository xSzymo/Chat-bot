package com.xszymo.hibernate.controllers.standard;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.xszymo.hibernate.data.interfaces.AnswersMessageService;
import com.xszymo.hibernate.data.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.data.interfaces.UserService;
import com.xszymo.hibernate.data.tables.Answer;
import com.xszymo.hibernate.data.tables.Question;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoadAtStart {
	public static final String PATH_QUESTIONS = "D:/ProjectsSL/Chat-botV6/src/main/resources/text.txt";

	@Resource(name = "answerMessageService")
	AnswersMessageService answer;

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;

	@GetMapping("load/questions/from/data/base")
	public RedirectView loadQuestions() throws IOException {
			File file = new File(PATH_QUESTIONS);
			if (!(file.exists() && !file.isDirectory())) {
				Writer writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(PATH_QUESTIONS), "utf-8"));
				writer.write("Test -.|.- test :) ");
				if (writer != null)
					writer.close();
			}

			try (BufferedReader br = new BufferedReader(new FileReader(PATH_QUESTIONS))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					boolean a = false;
					String hey = "";
					String hey1 = "";
					char[] chars = line.toCharArray();
					for (int i = 0; i < chars.length; i++) {
						int k = i;
						if (chars[k++] == '-' && i < chars.length - 1)
							if (chars[k++] == '.')
								if (chars[k++] == '|')
									if (chars[k++] == '.')
										if (chars[k++] == '-') {
											a = true;
											i = k + 1;
										}
						if(i >= chars.length)
							i = chars.length-1;
						
						if (!a)
							hey += chars[i];
						else
							hey1 += chars[i];
					}
					Question questuerinio = new Question(hey.substring(0, hey.length()-1));
					Answer answerino = null;
					if(!hey1.equals(" ")) {
					answerino = new Answer(hey1);
					answerino.setQuestion(questuerinio);
					questuerinio.getAnswers().add(answerino);
					}
					question.persist(questuerinio);
					if(!hey1.equals(" ")) {
						answer.persist(answerino);
					}
				    
					line = br.readLine();
				}

				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();

			} catch (IOException e) {
				e.printStackTrace();
			}

		return new RedirectView("localhost:8080");
	}
}
