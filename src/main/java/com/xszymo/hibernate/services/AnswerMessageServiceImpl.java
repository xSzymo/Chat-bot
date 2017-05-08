package com.xszymo.hibernate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xszymo.hibernate.interfaces.AnswersMessageDao;
import com.xszymo.hibernate.interfaces.AnswersMessageService;
import com.xszymo.hibernate.tables.Answer;

@Transactional
@Service("answerMessageService")
public class AnswerMessageServiceImpl implements AnswersMessageService {

	@Autowired
	@Qualifier("answerMessageDao")
	private AnswersMessageDao answerMessageDao;

	@Override
	public Answer findById(long id) {
		return answerMessageDao.findById(id);
	}

	@Override
	public void persist(Answer answer) {
		answerMessageDao.persistAnswer(answer);
	}

	@Override
	public void update(Answer answer) {
		answerMessageDao.updateAnswer(answer);
	}

	@Override
	public List<Answer> findAll() {
		return answerMessageDao.findAllAnswers();
	}
	
	@Override
	public void delete(Answer answer) {
		answerMessageDao.deleteAnswer(answer);
	}
	
	@Override
	public void delete(long id) {
		answerMessageDao.deleteAnswer(id);
	}
}
