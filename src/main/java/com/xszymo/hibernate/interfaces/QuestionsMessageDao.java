package com.xszymo.hibernate.interfaces;

import java.util.List;
import com.xszymo.hibernate.tables.Question;

public interface QuestionsMessageDao {

	Question findById(long id);

	void persistQuestion(Question message);
	
	void deleteQuestion(Question message);
	
	void deleteQuestion(long id);
	
	List<Question> findAllQuestions();

	void updateQuestion(Question message);

	Question findByMessage(String message);

}
