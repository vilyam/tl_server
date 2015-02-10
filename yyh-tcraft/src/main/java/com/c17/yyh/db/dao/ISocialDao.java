package com.c17.yyh.db.dao;

public interface ISocialDao {

	int getAward(int userId);

	int updateState(int userId, String state);

}
