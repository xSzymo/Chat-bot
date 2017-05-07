package com.xszymo.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.xszymo.hibernate.interfaces.AnswersMessageDao;
import com.xszymo.hibernate.tables.Answer;
import com.xszymo.hibernate.tables.Question;

@Repository("answerMessageDao")
public class AnswerMessageDaoImpl extends AbstractDao<Long, Answer> implements AnswersMessageDao {


	@Override
	public Answer findById(long id) {
		return getByKey(id);
	}

	@Override
	public void persistAnswer(Answer message) {
		persist(message);
	}

	@Override
	public void updateAnswer(Answer message) {
		update(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer> findAllAnswers() {
		Criteria criteria = createEntityCriteria();
		return (List<Answer>) criteria.list();
	}

	@Override
	public void deleteAnswer(Answer message) {
		delete(message);
	}

	@Override
	public void deleteAnswer(long id) {
		delete(findById(id));
	}
}
