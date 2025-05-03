package com.example.demo.pojo;


import java.util.List;


public class Menu {
    private Integer id;
    private Integer pid;
    private String name;
    private String path;
    private String icon;
    private String description;

    private List<Menu> children;




    //构造函数
    public Menu() {
    }

    public Menu(Integer id, Integer pid, String name, String path, String icon, String description, List<Menu> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.path = path;
        this.icon = icon;
        this.description = description;
        this.children = children;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getPid() {
        return pid;
    }


    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getIcon() {
        return icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public List<Menu> getChildren() {
        return children;
    }


    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String toString() {
        return "Menu{id = " + id + ", pid = " + pid + ", name = " + name + ", path = " + path + ", icon = " + icon + ", description = " + description + ", children = " + children + "}";
    }
}
