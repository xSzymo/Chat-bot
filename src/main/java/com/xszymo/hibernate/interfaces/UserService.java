package com.xszymo.hibernate.interfaces;

import java.util.Collection;
import com.xszymo.hibernate.tables.User;


public interface UserService {

	User findById(int id);
	
	void persist(User user);

	Collection<User> findAllUsers();

	void delete(User question);

	void delete(long id); 
}
