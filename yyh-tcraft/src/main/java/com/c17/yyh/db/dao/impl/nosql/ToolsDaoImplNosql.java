package com.c17.yyh.db.dao.impl.nosql;

import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.c17.yyh.db.repositories.ToolRepository;
import com.c17.yyh.models.tool.Tool;
import com.couchbase.client.protocol.views.ComplexKey;
import com.couchbase.client.protocol.views.Query;


//TODO remove reduntant methods. only save should remain
@Repository("toolsServiceNosql")
public class ToolsDaoImplNosql  {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int updateUserTool(int userId, Tool tool) {
        toolRepository.save(tool);
        return 0;
    }

    public int increaseUserTools(int userId, List<Tool> tools) {
        toolRepository.save(tools);
        return 0;
    }

    public int addUserTool(int userId, Tool tool) {
        //tool.setId(UUID.randomUUID().toString());
        return 0;
    }

    public int increaseUserTool(int userId, String toolName, int deltaValue) {
        //TODO 
        String sql = "UPDATE user_tools SET " + toolName + " = (" + toolName + " + ?) WHERE user_id = ?";
        int res = jdbcTemplate.update(sql, deltaValue, userId);
        return res;
    }

    ///ok
    public List<Tool> getToolsList(int userId) {
        Query query = new Query();
        query.setKey(ComplexKey.of(userId));
        return toolRepository.findByUser_id(query);
    }

}
