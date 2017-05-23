package com.xszymo.hibernate.configuration;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.xszymo.hibernate.interfaces.AnswersMessageService;
import com.xszymo.hibernate.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.interfaces.UserService;
import com.xszymo.hibernate.tables.Answer;
import com.xszymo.hibernate.tables.Question;

@Configuration
public class LoadAtStart {
	public static final String PATH_QUESTIONS = "/home/xszymo/Desktop/Programming/text.txt";
	public static final boolean readQuestionsAndAnswersFromTxt = false;

	@Resource(name = "answerMessageService")
	AnswersMessageService answer;

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;

	@Autowired
	UserService userService;

	@PostConstruct
	public void halo() throws IOException {
		// String everything = "";
		if (readQuestionsAndAnswersFromTxt) {
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

				// everything = sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
