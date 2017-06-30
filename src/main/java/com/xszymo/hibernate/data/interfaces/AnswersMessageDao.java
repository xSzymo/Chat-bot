package com.xszymo.hibernate.data.interfaces;

import java.util.List;
import com.xszymo.hibernate.data.tables.Answer;

public interface AnswersMessageDao {

	Answer findById(long id);

	void persistAnswer(Answer message);
	
	void deleteAnswer(Answer message);
	
	void deleteAnswer(long id);
	
	List<Answer> findAllAnswers();

	void updateAnswer(Answer message);

}
