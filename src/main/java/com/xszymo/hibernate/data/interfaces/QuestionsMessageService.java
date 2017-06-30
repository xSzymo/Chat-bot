package com.xszymo.hibernate.data.interfaces;

import java.util.List;
import com.xszymo.hibernate.data.tables.Question;

public interface QuestionsMessageService {

	Question findById(long l);

	void persist(Question message);

	List<Question> findAll();

	void delete(Question question);

	void delete(long id);

	void update(Question employee);

	Question findByMessage(String message);
}
