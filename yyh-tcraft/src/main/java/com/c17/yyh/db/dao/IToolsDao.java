package com.c17.yyh.db.dao;

import com.c17.yyh.db.dao.impl.xml.XMLConfigLoader;
import com.c17.yyh.models.tool.Tool;
import com.c17.yyh.models.tool.ToolItem;
import com.c17.yyh.models.tool.ToolItems;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA. User: DRybak Date: 7/11/13 Time: 1:14 AM
 */
public abstract class IToolsDao {

    @Autowired
    private XMLConfigLoader xmlConfigLoader;

    public abstract int updateUserTool(int userId, Tool tool);

    public abstract int increaseUserTool(int userId, int toolId, int deltaValue);

    public List<ToolItem> getToolItems() {
        return xmlConfigLoader.loadData(ToolItems.class);
    }

    public abstract int[] addToolsBatch(int userId, List<Tool> tools);

}
