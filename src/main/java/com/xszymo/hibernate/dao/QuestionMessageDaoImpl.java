package com.xszymo.hibernate.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
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

	/*
	 * no implement yet
	 */
	@Override
	 public Question findByMessage(String message) {
//	 Query query = sessionFactory.getCurrentSession().createQuery("from
//	 questions where message=:message");
//	 query.setParameter("message", message);
//	 Question category = (Question) query.uniqueResult();
	
	 return null;
	 }

	@Override
	public void persistQuestion(Question message) {
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
		List<Question> a = (List<Question>) criteria.list();
		return a;
	}

	@Override
	public void deleteQuestion(Question entity) {
		delete(entity);
	}

	@Override
	public void deleteQuestion(long id) {
		Question entity = findById(id);
		delete(entity);
	}
}
