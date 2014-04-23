package com.semantica.mandelbrot.persistence.repository;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.CommandResult;
import com.semantica.mandelbrot.persistence.domain.User;

@Repository
public class UserRepository extends MandelbrotRepository<User> {

	public UserRepository(Class<? extends User> type) {
		super(type);
	}

	public UserRepository() {
		super(User.class);
	}

	public User findByUserName(String userName) {
		return mongoTemplate.findOne(
				new Query(Criteria.where("userName").is(userName)), User.class);
	}

	public CommandResult getLastError() {
		return mongoTemplate.getDb().getLastError();
	}
}
