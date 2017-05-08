package com.xszymo.hibernate.interfaces;

import java.util.List;
import com.xszymo.hibernate.tables.Answer;

public interface AnswersMessageService {

	Answer findById(long id);
	
	void persist(Answer message);

	List<Answer> findAll();

	void delete(long id);

	void delete(Answer answer);

	void update(Answer answer); 
}
