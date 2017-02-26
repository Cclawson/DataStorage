package com.example.codyclawson.datastorage;

/**
 * Created by CodyClawson on 2/22/2017.
 */

public class Task {
    private int id;
    private int parentID;
    private String name;
    private String color;
    private long time;
    private int categoryId;
    private int priority;
    private int completed;


    public Task()
    {
    }

    public Task(int id,int parentID, String name, String color, long time, int categoryId, int priority, int completed)
    {
        this.id=id;
        this.parentID=parentID;
        this.name=name;
        this.color=color;
        this.time=time;
        this.categoryId=categoryId;
        this.priority=priority;
        this.completed=completed;
    }
    public Task(int parentID, String name, String color, long time, int categoryId, int priority, int completed)
    {
        this.parentID=parentID;
        this.name=name;
        this.color=color;
        this.time=time;
        this.categoryId=categoryId;
        this.priority=priority;
        this.completed=completed;
    }
    public Task(String name, String color, long time, int categoryId, int priority, int completed)
    {
        this.parentID=-1;
        this.name=name;
        this.color=color;
        this.time=time;
        this.categoryId=categoryId;
        this.priority=priority;
        this.completed=completed;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setParentId(int id) {
        this.parentID = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public void setCategoryId(int id) {
        this.categoryId = id;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setCompleted(int completed) {
        this.completed = completed;
    }



    public int getId() {
        return id;
    }
    public int getParentId() {
        return parentID;
    }
    public String getName() {
        return name;
    }
    public String getColor() {return color;}
    public long getTime() {return time;}
    public long getCategoryId() {return categoryId;}
    public long getPriority() {return priority;}
    public long getCompleted() {return completed;}


}
