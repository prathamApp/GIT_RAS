package com.pratham.readandspeak.modalclasses;

import java.util.List;

public class CategoryModal {

    public String category;

    public String categoryImage;


    public List<DataListModal> dataList;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<DataListModal> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListModal> dataList) {
        this.dataList = dataList;
    }
}
