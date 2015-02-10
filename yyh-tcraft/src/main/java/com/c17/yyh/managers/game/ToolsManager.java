package com.c17.yyh.managers.game;

import com.c17.yyh.core.CacheService;
import com.c17.yyh.db.dao.IDumpDao;
import com.c17.yyh.db.dao.IToolsDao;
import com.c17.yyh.db.entities.purchasing.GameItemPrice;
import com.c17.yyh.models.User;
import com.c17.yyh.models.tool.Tool;
import com.c17.yyh.models.tool.ToolItem;
import com.c17.yyh.models.tool.ToolLoading;
import com.c17.yyh.exceptions.ServerException;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA. User: DRybak Date: 7/20/13 Time: 7:45 PM
 */
@Service("toolsManager")
public class ToolsManager {

    @Autowired
    private IToolsDao toolsService;

    @Autowired
    private IDumpDao dumpService;

    @Autowired
    CacheService cacheService;

    List<ToolItem> toolItems;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void initialize() {
        loadToolItems();
    }

    public void loadToolItems() {
        toolItems = toolsService.getToolItems();
        logger.info("Loaded {} tool items", toolItems.size());
    }

    public Tool usingUserTool(User user, int toolId, int level_number, int levelset_number) {
        List<Tool> tools = user.getTools();
        Tool tool = getToolByToolId(tools, toolId);

        validateLoading(user);

        int toolCount = tool.getCount();
        if (toolCount <= 0) {
            throw new ServerException(0101, "Can't use tool " + tool.getId() + ". restTime is "
                    + tool.getRestTime(), false);
        }

        tool.setCount(--toolCount);
        tool.setLastUsingTool(System.currentTimeMillis());

        if (toolCount == 0) {
            ToolLoading tLoadin = getToolItemById(toolId).getLoading();
            if(tLoadin != null) {
                tool.setRestTime(tLoadin.getTime());
            }
        }

        cacheService.updateUserInCache(user);

        dumpService.writeToolDump(user, toolId, level_number, levelset_number);

        int res = toolsService.updateUserTool(user.getUserId(), tool);
        if (res > 0) {
            return tool;
        } else {
            return null;
        }
    }

    ///ok!
    public void validateLoading(User user) {

        for (Tool tool : user.getTools()) {
            int toolId = tool.getId();
            int toolCount = tool.getCount();
            ToolItem item = getToolItemById(toolId);

            if (toolCount == 0) {
                if (item.getLoading() != null) {
                    //TODO: need add level check
                    long currentTime = System.currentTimeMillis();

                    long loadTime = item.getLoading().getTime();
                    long lastUsingTool = tool.getLastUsingTool();
                    if (loadTime == 0 || lastUsingTool == 0) {
                        continue;
                    }
                    loadTime *= 1000;

                    long loadingTime = loadTime + lastUsingTool;

                    if (currentTime > loadingTime) {
                        toolCount += item.getDefault_count();
                        tool.setCount(toolCount);
                        toolsService.increaseUserTool(user.getUserId(), toolId, toolCount);
                    } else {
                        long restTime = (lastUsingTool + loadTime - currentTime) / 1000;
                        tool.setRestTime(restTime);
                    }
                }
            }
        }
    }

    ///ok
    public Tool getToolByToolId(List<Tool> tools, int id) {
        for (Tool item : tools) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<ToolItem> getToolItems() {
        return toolItems;
    }

    public int increaseUserTool(User user, int toolId, int deltaValue) {
        addToolToUser(user, toolId, deltaValue);

        cacheService.updateUserInCache(user);

        return toolsService.increaseUserTool(user.getUserId(), toolId, deltaValue);
    }

    public ToolItem getToolItemById(int id) {
        for (ToolItem item : toolItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
    
    public GameItemPrice getToolPriceById(int toolId, int priceId) {
        for (GameItemPrice item : getToolItemById(toolId).getPrice()) {
            if (item.getId() == priceId) {
                return item;
            }
        }
        return null;
    }

    public void addToolsBatch(User user, List<Tool> tools) {
        toolsService.addToolsBatch(user.getUserId(), tools);
        for (Tool tool : tools) {
            addToolToUser(user, tool.getId(), tool.getCount());
        }
        cacheService.updateUserInCache(user);
    }

    private void addToolToUser (User user, int toolId, int count) {
        List<Tool> tools = user.getTools();
        Tool tool = getToolByToolId(tools, toolId);
        if (tool == null) {
            user.getTools().add(new Tool(toolId, count));
        } else
            tool.setCount(tool.getCount() + count);
    }
}
