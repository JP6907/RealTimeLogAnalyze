package com.jp.streaminglog.domain;

import java.io.Serializable;

//映射到mysql
public class SearchEngine implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;  //英文名
    private String name; //中文名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SearchEngine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
