package com.c17.yyh.db.repositories;

import com.c17.yyh.models.LevelStatistic;
import com.couchbase.client.protocol.views.Query;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LevelStatisticRepository extends CrudRepository<LevelStatistic, String> {

    //@View(designDocument="levelStatistic",viewName="byFriends")
    List<LevelStatistic> findUserInfo(Query query);
    
    List<LevelStatistic> findByUser_id(Query query);
}
