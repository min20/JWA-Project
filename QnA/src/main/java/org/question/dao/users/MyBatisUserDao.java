package org.question.dao.users;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.question.domain.users.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class MyBatisUserDao implements UserDao {
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public User selectByUserId(String userId) {
		return sqlSession.selectOne("UserMapper.selectByUserId", userId);
	}

	@Override
	public void insert(User user) {
		sqlSession.insert("UserMapper.insert", user);
	}

	@Override
	public void update(User user) {
		sqlSession.update("UserMapper.update", user);
	}

}
