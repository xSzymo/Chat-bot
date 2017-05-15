package com.xszymo.hibernate.interfaces;

import java.util.LinkedList;
import java.util.List;
import com.xszymo.hibernate.tables.Question;

public interface QuestionsMessageService {

	Question findById(long l);
	
	void persist(Question message);

	List<Question> findAll();

	void delete(Question question);

	void delete(long id);

	void update(Question employee);
}
