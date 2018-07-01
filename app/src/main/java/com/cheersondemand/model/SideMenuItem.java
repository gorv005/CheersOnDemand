package com.cheersondemand.model;

public class SideMenuItem {

    private  int nameResourse;
    private  int imageNameResource;
    private String tag;

    public SideMenuItem(int nameResourse, int imageNameResource, String tag) {
        this.nameResourse = nameResourse;
        this.imageNameResource = imageNameResource;
        this.tag=tag;
    }

    public int getNameResourse() {
        return nameResourse;
    }

    public void setNameResourse(int nameResourse) {
        this.nameResourse = nameResourse;
    }

    public int getImageNameResource() {
        return imageNameResource;
    }

    public void setImageNameResource(int imageNameResource) {
        this.imageNameResource = imageNameResource;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
