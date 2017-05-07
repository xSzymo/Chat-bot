package com.xszymo.hibernate.tables;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {
	@Id
	@GeneratedValue
	private long id;

	@Column(name = "message")
	private String message;

	@Column
	private long counter;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private Collection<Answer> answers;

	public Question() {
		answers = new LinkedList<Answer>();
	}

	public Question(String message) {
		this.message = message;
		answers = new LinkedList<Answer>();
	}

	public Question(long id, String message) {
		this.id = id;
		this.message = message;
		answers = new LinkedList<Answer>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setQuestionMessage(Question message) {
		this.message = message.getMessage();
		this.counter = message.getCounter();
		this.answers = message.getAnswers();
	}

	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		answers = new LinkedList<Answer>();
		answers.addAll(answers);
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", message=" + message + ", answers=" + answers + "]";
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}
}
