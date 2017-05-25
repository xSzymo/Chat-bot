package com.xszymo.hibernate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xszymo.hibernate.interfaces.QuestionsMessageDao;
import com.xszymo.hibernate.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.tables.Question;

@Service("questionMessageService")
@Transactional
public class QuestionMessageServiceImpl implements QuestionsMessageService {

	@Autowired
	@Qualifier("questionMessageDao")
	private QuestionsMessageDao questionMessageDao;

	@Override
	public Question findById(long id) {
		return questionMessageDao.findById(id);
	}
	
	@Override
	public Question findByMessage(String message) {
		return questionMessageDao.findByMessage(message);
	}

	@Override
	public void persist(Question employee) {
		questionMessageDao.persistQuestion(employee);
	}

	@Override
	public void update(Question employee) {
		questionMessageDao.updateQuestion(employee);
	}

	@Override
	public List<Question> findAll() {
		return questionMessageDao.findAllQuestions();
	}

	@Override
	public void delete(Question question) {
		questionMessageDao.deleteQuestion(question);
	}

	@Override
	public void delete(long id) {
		questionMessageDao.deleteQuestion(id);
	}
}
