package com.c17.yyh.db.dao;

import java.util.HashMap;

import com.c17.yyh.db.entities.XmlDailyBonus;
import com.c17.yyh.models.User;

public interface IDailyBonusDao {

	HashMap<Integer, XmlDailyBonus> getDailyBonuses(int limit);

	int updateDailyBonusState(User user);
}
