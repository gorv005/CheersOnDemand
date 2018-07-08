package com.cheersondemand.model.productList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AB on 6/17/2018.
 */

public class Sort {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private int name;
    @SerializedName("selectedImage")
    @Expose
    private int selectedImage;
    @SerializedName("sortImage")
    @Expose
    private int sortImage;


    public Sort(Integer id, int name,  int sortImage,int selectedImage) {
        this.id = id;
        this.name = name;
        this.selectedImage = selectedImage;
        this.sortImage = sortImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }

    public int getSortImage() {
        return sortImage;
    }

    public void setSortImage(int sortImage) {
        this.sortImage = sortImage;
    }
}
