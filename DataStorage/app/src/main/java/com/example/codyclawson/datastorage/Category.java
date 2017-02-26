package com.example.codyclawson.datastorage;

/**
 * Created by CodyClawson on 2/26/2017.
 */

public class Category {
    private int id;
    private String name;


    public Category()
    {
    }

    public Category(int id, String name)
    {
        this.id=id;
        this.name=name;
    }
    public Category(String name)
    {
        this.name=name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

}
