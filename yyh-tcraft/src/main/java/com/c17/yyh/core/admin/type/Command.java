package com.c17.yyh.core.admin.type;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sigurd, vilyam
 */
public abstract class Command {

    protected Logger logger = LoggerFactory.getLogger("admin.operation");

    abstract public void execute(Map<String, String> data);
}
