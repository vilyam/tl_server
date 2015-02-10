package com.c17.yyh.db.dao;

import java.sql.Timestamp;

import com.c17.yyh.models.User;
import com.c17.yyh.type.GameType;

public interface IDumpDao {

    public void writeStatisticDump(int user_id, int level_id, int level_set_id,
            int stars, int score, GameType game_type, int treasure_collected,
            int p_pet_collected, int p_money, boolean pd_additional_moves,
            Timestamp pd_time_start, Timestamp pd_time_stop);

    public void writeLoginDump(User user, int bDay);

    public void writeToolDump(User user, int tool_id, int level_number, int levelset_number);

    void updateDbName();
}
