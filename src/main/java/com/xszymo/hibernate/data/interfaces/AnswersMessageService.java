package com.xszymo.hibernate.data.interfaces;

import java.util.List;
import com.xszymo.hibernate.data.tables.Answer;

public interface AnswersMessageService {

	Answer findById(long id);
	
	void persist(Answer message);

	List<Answer> findAll();

	void delete(long id);

	void delete(Answer answer);

	void update(Answer answer); 
}
