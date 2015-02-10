package com.c17.yyh.managers.game;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IConfigDao;
import com.c17.yyh.db.entities.adventure.Boss;

@Service("bossManager")
public class BossManager {

    @Autowired
    private IConfigDao configService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Boss> bosses;

    @PostConstruct
    protected void initialize() {
        loadBosses();
    }

    public void loadBosses() {
        bosses = configService.getBosses();
        logger.info("Loaded {} bosses", bosses.size());
    }

    public List<Boss> getBosses() {
        return bosses;
    }

    public void setBosses(List<Boss> bosses) {
        this.bosses = bosses;
    }

    public Boss getBossById(int boss_id) {
        for (Boss boss : bosses) {
            if (boss.getId() == boss_id) {
                return boss;
            }
        }
        return null;
    }

}
