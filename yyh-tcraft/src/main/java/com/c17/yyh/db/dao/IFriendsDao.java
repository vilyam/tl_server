package com.c17.yyh.db.dao;

import java.util.List;

import com.c17.yyh.models.friends.FriendBonusReferal;
import com.c17.yyh.models.friends.FriendInfo;
import com.c17.yyh.models.friends.FriendProgress;

public interface IFriendsDao {

	public List<FriendProgress> getFriendsProgress(String[] arrayFriendsIds);

	public List<FriendInfo> getFriendsInfo(String[] arrayFriendsIds, int levelNumber, int levelSetNumber);

	public int insertReferal(String from, String to);

	public List<FriendBonusReferal> getReferalFriends(String snId);

    int acceptReferal(String uid, String ref);
}
