package com.xszymo.hibernate.data.dao;

import java.util.Collection;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.xszymo.hibernate.data.interfaces.UserDao;
import com.xszymo.hibernate.data.tables.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

	@Override
	public User findById(long id) {
		return getByKey(id);
	}

	@Override
	public void persistUser(User user) {
		persistUser(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findAllUsers() {
		Criteria criteria = createEntityCriteria();
		return (Collection<User>) criteria.list();
	}

	@Override
	public void deleteUser(User user) {
		delete(user);
	}

	@Override
	public void deleteUser(long id) {
		delete(findById(id));
	}
}
