package com.c17.yyh.db.repositories;

import com.c17.yyh.models.tool.Tool;
import com.couchbase.client.protocol.views.Query;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ToolRepository extends CrudRepository<Tool, String> {

	List<Tool> findByUser_id(Query query);

}
