package com.xszymo.hibernate.data.services;

import java.util.Collection;
//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xszymo.hibernate.data.interfaces.UserDao;
import com.xszymo.hibernate.data.interfaces.UserService;
import com.xszymo.hibernate.data.tables.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public void persist(User user) {
		userDao.persistUser(user);
	}

	@Override
	public Collection<User> findAllUsers() {
		return userDao.findAllUsers();
	}
	
	@Override
	public void delete(User question) {
		userDao.deleteUser(question);
	}

	@Override
	public void delete(long id) {
		userDao.deleteUser(id);
	}

}
