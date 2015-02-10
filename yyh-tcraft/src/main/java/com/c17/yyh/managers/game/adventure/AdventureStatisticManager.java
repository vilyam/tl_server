package com.c17.yyh.managers.game.adventure;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.core.CacheService;
import com.c17.yyh.db.dao.IStatisticAdventureDao;
import com.c17.yyh.db.entities.adventure.Level;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.models.LevelStatistic;
import com.c17.yyh.models.User;
import com.c17.yyh.type.GameType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IDumpDao;

/**
 * @author vilyam
 *
 */
@Service("adventureStatisticManager")
public class AdventureStatisticManager {

    @Autowired
    private IStatisticAdventureDao statisticAdventureServiceSql;

    @Autowired
    private IStatisticAdventureDao statisticAdventureServiceNosql;

    @Autowired
    private IDumpDao dumpService;

    @Autowired
    private AdventureLevelManager adventureLevelManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    CacheService cacheService;

    @Autowired
    private ServerConfig serverConfig;

    public LevelStatistic startLevelStatistic(User user, int levelSetId, int levelId, GameType gameType) {
        LevelStatistic lvlStat = getLevelStatisticByIds(user, levelSetId, levelId);
        if (lvlStat == null) {
            if (serverConfig.useNoSql) {
                lvlStat = statisticAdventureServiceNosql.startLevelStatistic(user.getUserId(), levelSetId, levelId, gameType.toString());
            }

            if (serverConfig.useSql) {
                lvlStat = statisticAdventureServiceSql.startLevelStatistic(user.getUserId(), levelSetId, levelId, gameType.toString());
            }
        }

        user.getLevelsStatistic().add(lvlStat);

        cacheService.updateUserInCache(user);

        return lvlStat;
    }

    /**
     * Redirect "updateLevelsStatistic" to {@link IStatisticAdventureDao}
     *
     * @param userId
     * @param levelset_number
     * @param level_number
     * @param stars
     * @param score
     */
    public void updateLevelsStatistic(User user, int level_set_number, int level_number,
            int stars, int treasure_collected, int pet_collected, int money, int score,
            GameType game_type, boolean pd_additional_moves,
            Timestamp pd_time_start, Timestamp pd_time_stop) {

        if (stars > 0) {
            List<LevelStatistic> stats = user.getLevelsStatistic();
            LevelStatistic lvlStat = getLevelStatisticByIds(user, level_set_number, level_number);

            //update existing
            if (lvlStat != null) {
                boolean isUpdate = false;
                if (stars > lvlStat.getStars()) {
                    lvlStat.setStars(stars);
                    isUpdate = true;
                }
                if (treasure_collected > lvlStat.getTreasure_collected()) {
                    lvlStat.setTreasure_collected(treasure_collected);
                    isUpdate = true;
                }
                if (score > lvlStat.getScore()) {
                    lvlStat.setScore(score);
                    isUpdate = true;
                }

                if (isUpdate) {
                    if (serverConfig.useNoSql) {
                        statisticAdventureServiceNosql.updateLevelsStatistic(lvlStat);
                    }

                    if (serverConfig.useSql) {
                        statisticAdventureServiceSql.updateLevelsStatistic(lvlStat);
                    }
                }
                //insert new
            } else {
                lvlStat = startLevelStatistic(user, level_set_number, level_number, game_type);
                if (lvlStat != null) {
                    stats.add(lvlStat);
                }
            }

        }

        dumpService.writeStatisticDump(user.getUserId(), level_number, level_set_number,
                stars, score, game_type, treasure_collected, pet_collected, money,
                pd_additional_moves, pd_time_start, pd_time_stop);

        cacheService.updateUserInCache(user);
    }

    public Level getLevelForUserById(User user, int levelId, int levelSetId) {
        return adventureLevelManager.getLevelById(levelId, levelSetId);
    }

    public List<LevelStatistic> getLevelInLevelSet(User user, int levelSetId) {
        List<LevelStatistic> list = new ArrayList<LevelStatistic>();
        for (LevelStatistic e : user.getLevelsStatistic()) {
            if (e.getLevelset_number() == levelSetId) {
                list.add(e);
            }
        }
        return list;
    }

    public int getTotalStarsInLevelSet(User user, int levelSetId) {
        int total = 0;
        for (LevelStatistic e : getLevelInLevelSet(user, levelSetId)) {
            total += e.getStars();
        }
        return total;
    }

    public LevelStatistic getLevelStatisticByIds(User user, int levelSetId,
            int levelId) {
        for (LevelStatistic lvlStat : user.getLevelsStatistic()) {
            if (lvlStat.getLevelset_number() == levelSetId && lvlStat.getLevel_number() == levelId) {
                return lvlStat;
            }
        }
        return null;
    }

    public void getUserMaxLevel(User user, MutableInt maxLevel, MutableInt maxLevelSet) {

        for (LevelStatistic levelStatistic : user.getLevelsStatistic()) {
            if (levelStatistic.getLevelset_number() > maxLevelSet.intValue()) {
                maxLevelSet.setValue(levelStatistic.getLevelset_number());
            }
        }

        List<LevelStatistic> lvlStat = getLevelInLevelSet(user, maxLevelSet.intValue());
        for (LevelStatistic levelStatistic : lvlStat) {
            if (levelStatistic.getGame_type() != GameType.SECRET) {
                if (levelStatistic.getLevel_number() > maxLevel.intValue()) {
                    maxLevel.setValue(levelStatistic.getLevel_number());
                }
            }
        }

    }

    public boolean getUserIsPlayed(User user, int levelSetId, int levelId) {
        return null != getLevelStatisticByIds(user, levelSetId, levelId) ? true : false;
    }
}
