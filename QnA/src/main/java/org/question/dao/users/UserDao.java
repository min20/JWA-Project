package org.question.dao.users;

import org.question.domain.users.User;

public interface UserDao {

	User selectByUserId(String userId);

	void insert(User user);

	void update(User user);

}