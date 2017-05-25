package com.xszymo.hibernate.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xszymo.hibernate.interfaces.AnswersMessageDao;
import com.xszymo.hibernate.interfaces.QuestionsMessageDao;
import com.xszymo.hibernate.tables.Answer;
import com.xszymo.hibernate.tables.Question;

@Repository("questionMessageDao")
public class QuestionMessageDaoImpl extends AbstractDao<Long, Question> implements QuestionsMessageDao {

	@Autowired
	@Qualifier("answerMessageDao")
	private AnswersMessageDao answerMessageDao;

	@Override
	public Question findById(long id) {
		return getByKey(id);
	}

	@Override
	public Question findByMessage(String message) {
		for(Question x : findAllQuestions())
			if(x.getMessage().equals(message))
				return x;
		return null;
	}

	@Override
	public void persistQuestion(Question message) {
		List<Question> all = findAllQuestions();
		for (Question x : all)
			if (x.getMessage().equals(message.getMessage())) {
				Question a = findById(x.getId());
				a.setAnswers(message.getAnswers());
				a.setCounter(a.getCounter() + 1);
				update(a);
				return;
			}
		persist(message);
	}

	@Override
	public void updateQuestion(Question message) {
		update(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> findAllQuestions() {
		Criteria criteria = createEntityCriteria();
		System.out.println(criteria.list().size());
		List<Question> a = (List<Question>) criteria.list();
		LinkedList<Question> b = new LinkedList<Question>();
		boolean c;
		for (Question x : a) {
			c = false;
			for (Question x1 : b) {
				if (x1.getId() == x.getId())
					c = true;
			}
			if (!c)
				b.add(x);
		}
		return b;
	}

	@Override
	public void deleteQuestion(Question entity) {
		try {
			if (!entity.getAnswers().isEmpty())
				for (Answer x : entity.getAnswers())
					answerMessageDao.deleteAnswer(x.getId());
			delete(entity);
		} catch (NullPointerException e) {
			return;
		}
	}

	@Override
	public void deleteQuestion(long id) {
		Question entity = findById(id);
		try {
			if (!entity.getAnswers().isEmpty())
				for (Answer x : entity.getAnswers())
					answerMessageDao.deleteAnswer(x.getId());
			delete(entity);
		} catch (NullPointerException e) {
			return;
		}
	}
}
