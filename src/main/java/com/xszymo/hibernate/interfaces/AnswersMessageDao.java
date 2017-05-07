package com.xszymo.hibernate.interfaces;

import java.util.List;
import com.xszymo.hibernate.tables.Answer;

public interface AnswersMessageDao {

	Answer findById(long id);

	void persistAnswer(Answer message);
	
	void deleteAnswer(Answer message);
	
	void deleteAnswer(long id);
	
	List<Answer> findAllAnswers();

	void updateAnswer(Answer message);

}
