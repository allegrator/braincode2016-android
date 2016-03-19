package com.zakaprov.braincode2016.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category_id")
    private String categoryId;
    private String name;

    public Category(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
