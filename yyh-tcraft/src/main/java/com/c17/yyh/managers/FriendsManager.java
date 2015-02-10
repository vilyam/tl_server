package com.c17.yyh.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IConfigDao;
import com.c17.yyh.db.dao.IFriendsDao;
import com.c17.yyh.db.entities.friends.FriendBonusMin;
import com.c17.yyh.db.entities.friends.FriendBonusItem;
import com.c17.yyh.db.entities.friends.FriendBonusItems;
import com.c17.yyh.db.entities.purchasing.ComplexItem;
import com.c17.yyh.db.entities.purchasing.store.StoreItem;
import com.c17.yyh.managers.game.ToolsManager;
import com.c17.yyh.managers.purchasing.StoreManager;
import com.c17.yyh.models.User;
import com.c17.yyh.models.friends.FriendBonusItemWrapper;
import com.c17.yyh.models.friends.FriendBonusReferal;
import com.c17.yyh.models.friends.FriendInfo;
import com.c17.yyh.models.friends.FriendProgress;
import com.c17.yyh.mvc.message.outbound.friends.FriendsCheckBonusResponse;
import com.c17.yyh.mvc.message.outbound.friends.FriendsGetBonusResponse;
import com.c17.yyh.mvc.message.outbound.friends.FriendsLevelInfoResponse;
import com.c17.yyh.mvc.message.outbound.friends.FriendsProgressResponse;

@Service
@SuppressWarnings("unchecked")
public class FriendsManager {
    @Autowired
    private IFriendsDao friendsService;

    @Autowired
    private IConfigDao configService;

    @Autowired
    private StoreManager storeManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ToolsManager toolsManager;

    private Map<Integer,FriendBonusItem> items;
    private FriendBonusItems bonusItems;
    private FriendBonusMin min;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void initialize() {
        loadItems();
    }

    public void loadItems() {
        items = new HashMap<Integer, FriendBonusItem>();
        bonusItems = configService.getFriendBonusItems();

        min = bonusItems.getMin();
        List<FriendBonusItem> itemsDirty = bonusItems.getList();

        for (FriendBonusItem item : itemsDirty) {
            for (ComplexItem complexItem : item.getItems()) {
                complexItem.setStoreItem(storeManager.getItem(complexItem.getStore_item()));
            }
            items.put(item.getId(), item);
        }

        logger.info("Loaded {} friends bonus items", items.keySet().size());
    }
    
    public FriendsLevelInfoResponse getFriendsInfo(String[] arrayFriendsIds, int levelNumber, int levelSetNumber) {
    	FriendsLevelInfoResponse response = new FriendsLevelInfoResponse();
    	List<FriendInfo> list = null;
    	if (arrayFriendsIds == null || arrayFriendsIds.length == 0) {
    		list = Collections.emptyList();
        } else {
        	list = friendsService.getFriendsInfo(arrayFriendsIds, levelNumber, levelSetNumber);
        }
    	response.setFriendInfos(list);
    	return response;
    }

    public FriendsProgressResponse getFriendsProgress(String[] arrayFriendsIds) {
    	FriendsProgressResponse response = new FriendsProgressResponse();
    	List<FriendProgress> list = null;
    	if (arrayFriendsIds == null || arrayFriendsIds.length == 0) {
    		list = Collections.emptyList();
    	} else {
        	list = friendsService.getFriendsProgress(arrayFriendsIds);
        }
    	response.setFriendsProgress(list);
    	return response;
    }
    
    public int insertReferal(String from, String to) {
        return friendsService.insertReferal(from, to);
    }

    public List<FriendBonusReferal> getReferalFriends(String snId) {
        return friendsService.getReferalFriends(snId);
    }
    
    public int acceptReferal(String uid, String ref) {
        return friendsService.acceptReferal(uid, ref);
    }

    public FriendsCheckBonusResponse checkBonus(User user) {
    	FriendsCheckBonusResponse response = new FriendsCheckBonusResponse();
    	Object[] resp = checkBonusList(user);
        
        response.setBonus((List<FriendBonusReferal>) resp[0]);
        response.setOld_count((Integer) resp[1]);
        return response;
    }
    
    private Object[] checkBonusList(User user) {
    	Object[] resp = new Object[2];
        List<FriendBonusReferal> maybeRefs = new ArrayList<FriendBonusReferal>();
        
        int oldCount = 0; //сколько уже взято бонусов - точка отсчета
        
        for (FriendBonusReferal ref : getReferalFriends(user.getSnId())) {
        	if (ref.getStatus() == 0 && (ref.getMax_levelset_number() > min.getLevelset() 
            		|| (ref.getMax_levelset_number() == min.getLevelset() 
            		&& ref.getMax_level_number() >= min.getLevel()))) {
                maybeRefs.add(ref);
            }
            if (ref.getStatus() > 0) {
                oldCount++;
            }
        }
        
        resp[1] = oldCount;
        
        for (FriendBonusReferal friendsBonusReferal : maybeRefs) {
            oldCount++;
            friendsBonusReferal.setBonus(items.get(oldCount));
        }
        resp[0] = maybeRefs;
        return resp;
    }

	public FriendsGetBonusResponse acceptBonus(User user) {
    	FriendsGetBonusResponse response = new FriendsGetBonusResponse();
        List<FriendBonusItemWrapper> result = new ArrayList<FriendBonusItemWrapper>();
        Object[] resp = checkBonusList(user);
        
        for (FriendBonusReferal friendsBonusReferal : (List<FriendBonusReferal>)resp[0]) {
            FriendBonusItem bonus = friendsBonusReferal.getBonus();
            if(bonus != null) {
                for (ComplexItem cxItem : bonus.getItems()) {
                    StoreItem sItem = cxItem.getStoreItem();
                    int value = cxItem.getValue();
                    switch (sItem.getResource()) {
                        case CRYSTALS:
                            userManager.incrementCrystals(user, value, true);
                        break;
                        case LIFE:
                            userManager.incrementLives(user, value, true);
                        break;
                        case MONEY:
                            userManager.incrementMoney(user, value, true);
                        break;
                        case TOOL:
                            //toolsManager.increaseUserTool(user, cxItem.getInner_id(), value);
                            break;
                        default:
                            break;
                    }
                }
                acceptReferal(user.getSnId(), friendsBonusReferal.getSnId());
                result.add(new FriendBonusItemWrapper(friendsBonusReferal.getSnId(), bonus));
            }
        }
        response.setBonus(result);
        return response;
    }

    public FriendBonusItems getBonusItems() {
        return bonusItems;
    }

    public void setBonusItems(FriendBonusItems bonusItems) {
        this.bonusItems = bonusItems;
    }
}
