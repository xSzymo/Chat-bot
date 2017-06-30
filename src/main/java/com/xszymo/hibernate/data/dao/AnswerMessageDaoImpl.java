package com.xszymo.hibernate.data.dao;

import java.util.List;

import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.xszymo.hibernate.data.interfaces.AnswersMessageDao;
import com.xszymo.hibernate.data.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.data.tables.Answer;
import com.xszymo.hibernate.data.tables.Question;

@Repository("answerMessageDao")
public class AnswerMessageDaoImpl extends AbstractDao<Long, Answer> implements AnswersMessageDao {

	@Resource(name = "questionMessageService")
	QuestionsMessageService question;

	@Override
	public Answer findById(long id) {
		return getByKey(id);
	}

	@Override
	public void persistAnswer(Answer message) {

		List<Question> all = question.findAll();
		for (Question x : all) {
			if (message.getQuestion().getMessage().equals(x.getMessage())) {

				Question que = question.findById(x.getId());
				for (Answer x1 : que.getAnswers())
					if (x1.getMessage().equals(message.getMessage())) {
						x1.setQuestion(que);
						x1.setCounter(x1.getCounter() + 1);
						question.update(que);
						update(x1);
						return;
					}
				que.getAnswers().add(message);
				message.setQuestion(que);
				question.update(que);
				persist(message);
				return;
			}
		}
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
		try {
			delete(message);
		} catch (NullPointerException e) {
			return;
		}
	}

	@Override
	public void deleteAnswer(long id) {
		try {
			delete(findById(id));
		} catch (NullPointerException e) {
			return;
		}
	}
}
