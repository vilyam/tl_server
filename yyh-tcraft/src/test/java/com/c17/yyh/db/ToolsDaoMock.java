package com.c17.yyh.db;

import com.c17.yyh.db.dao.IToolsDao;
import com.c17.yyh.models.tool.Tool;
import com.c17.yyh.models.tool.ToolItem;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ToolsDaoMock extends IToolsDao {

    @Override
    public int updateUserTool(int userId, Tool tool) {
        return 0;
    }

    @Override
    public List<ToolItem> getToolItems() {
        return Collections.emptyList();
    }

    @Override
    public int increaseUserTool(int userId, int toolId, int deltaValue) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int[] addToolsBatch(int userId, List<Tool> tools) {
        // TODO Auto-generated method stub
        return null;
    }
}
