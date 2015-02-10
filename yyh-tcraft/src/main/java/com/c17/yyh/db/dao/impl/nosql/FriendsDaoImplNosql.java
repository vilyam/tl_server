package com.c17.yyh.db.dao.impl.nosql;

import com.c17.yyh.db.dao.IFriendsDao;
import com.c17.yyh.models.friends.FriendBonusReferal;
import com.c17.yyh.models.friends.FriendInfo;
import com.c17.yyh.models.friends.FriendProgress;
import com.couchbase.client.protocol.views.Query;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class FriendsDaoImplNosql implements IFriendsDao {

    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Override
//    public List<FriendInfo> getFriendsInfo(Map<Integer, FriendInfo> friends, int levelNumber, int levelSetNumber) {
//        if (friends.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<FriendInfo> friendsList = new LinkedList<>();
//        JSONArray keys = new JSONArray();
//        for (FriendInfo friendInfo : friends.values()) {
//            JSONArray key = new JSONArray();
//            key.put(friendInfo.getUserId());
//            key.put(levelSetNumber);
//            key.put(levelNumber);
//            keys.put(key);
//        }
//        Query query = new Query();
//        query.setKeys(keys.toString());
//        long startTime = System.currentTimeMillis();
////        List<LevelStatistic> statistics = levelStatisticRepository.findUserInfo(query);
////        long operTime = System.currentTimeMillis() - startTime;
////        logger.info("Select by 3 keys time " + operTime);
////        for (LevelStatistic statistic : statistics) {
////            FriendInfo friendInfo = friends.get(statistic.getUser_id());
////            if (friendInfo != null) {
////                friendInfo.setStars(statistic.getStars());
////                friendInfo.setScore(statistic.getScore());
////                friendsList.add(friendInfo);
////            }
////        }
//        return friendsList;
//    }
//
//    @Override
//    public List<FriendProgress> getFriendsProgress(Map<Integer, FriendProgress> friends) {
//        if (friends.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<FriendProgress> friendsList = new LinkedList<>();
//
//        StringBuilder sqlSb = new StringBuilder("SELECT user_max_level.* FROM user_max_level WHERE user_id IN (");
//
//        for (Integer id : friends.keySet()) {
//            sqlSb.append(id);
//            sqlSb.append(", ");
//        }
//        sqlSb.deleteCharAt(sqlSb.length() - 1);
//        sqlSb.deleteCharAt(sqlSb.length() - 1);
//        sqlSb.append(") ");
//
//        SqlRowSet rws = jdbcTemplate.queryForRowSet(sqlSb.toString());
//
//        while (rws.next()) {
//            int userId = rws.getInt("user_id");
//            FriendProgress friendPr = friends.get(userId);
//            friendPr.setLevelNumber(rws.getInt("max_level_number"));
//            friendPr.setLevelSetNumber(rws.getInt("max_levelset_number"));
//            friendPr.setDate(rws.getTimestamp("date"));
//            friendsList.add(friendPr);
//        }
//
//        return friendsList;
//    }

    @Override
    public int insertReferal(String from, String to) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<FriendBonusReferal> getReferalFriends(String snId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int acceptReferal(String uid, String ref) {
        // TODO Auto-generated method stub
        return 0;
    }

	@Override
	public List<FriendProgress> getFriendsProgress(String[] arrayFriendsIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FriendInfo> getFriendsInfo(String[] arrayFriendsIds,
			int levelNumber, int levelSetNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
