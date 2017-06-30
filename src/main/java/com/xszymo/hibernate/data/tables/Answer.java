package com.xszymo.hibernate.data.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {
	@Id
	@GeneratedValue
	private long id;

	@Column(name = "message")
	private String message;

	@Column
	private long counter;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	public Answer() {
	}

	public Answer(String message) {
		super();
		this.message = message;
	}

	public Answer(long id, String message) {
		super();
		this.id = id;
		this.message = message;
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

	public void setAnswerMessage(Answer message) {
		this.message = message.getMessage();
		this.counter = message.getCounter();
		this.question = message.getQuestion();
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", message=" + message + ", counter=" + ", question=" + question + "]";
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}
}
