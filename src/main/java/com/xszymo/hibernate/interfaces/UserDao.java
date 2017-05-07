package com.xszymo.hibernate.interfaces;

import java.util.Collection;

import com.xszymo.hibernate.tables.User;

public interface UserDao {

	User findById(long id);

	void persistUser(User user);

	void deleteUser(User user);

	void deleteUser(long id);
	
	Collection<User> findAllUsers();
	
}
