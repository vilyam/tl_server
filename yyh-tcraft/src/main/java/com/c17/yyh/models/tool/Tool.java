package com.c17.yyh.models.tool;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

/**
 * Created with IntelliJ IDEA. User: nord Date: 03.07.13 Time: 20:18
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tool")
@Document
public class Tool implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2479472985594059839L;

    @Id
    private int id;
    @Field
    private int count;
    @Field
    private long lastUsingTool;
    @Field
    private long restTime = 0;

    public Tool(int id, int count) {
        super();
        this.id = id;
        this.count = count;
    }
    public Tool(int id) {
        super();
        this.id = id;
    }
    public Tool() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastUsingTool() {
        return lastUsingTool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tool tool = (Tool) o;

        if (count != tool.count) {
            return false;
        }
        if (lastUsingTool != tool.lastUsingTool) {
            return false;
        }
        if (id != tool.getId()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = String.valueOf(id).hashCode();
        result = 31 * result + count;
        result = 31 * result + (int) (lastUsingTool ^ (lastUsingTool >>> 32));
        return result;
    }

    public void setLastUsingTool(long lastUsingTool) {
        this.lastUsingTool = lastUsingTool;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime = restTime;
    }

}
