package com.xszymo.hibernate.data.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users")
@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String username;

	@Column
	private String password;

	public long getId() {
		return id;
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

	public User() {
		super();
	}

	public User(long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return username;
	}

	public void setLogin(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(User user) {
		this.username = user.getLogin();
		this.password = user.getPassword();
	}
}
