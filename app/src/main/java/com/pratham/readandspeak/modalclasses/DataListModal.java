package com.pratham.readandspeak.modalclasses;

import java.util.List;

public class DataListModal {

    public String category;

    public String categoryImage;


    public List<InnerDataListModal> dataList;

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

    public List<InnerDataListModal> getDataList() {
        return dataList;
    }

    public void setDataList(List<InnerDataListModal> dataList) {
        this.dataList = dataList;
    }
}
