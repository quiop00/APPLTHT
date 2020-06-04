package com.example.modules;

public class Products {
    private int id;
    private String name;
    private int count;

    public Products(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Products(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
