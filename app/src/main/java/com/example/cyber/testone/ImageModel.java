package com.example.cyber.testone;

/**
 * Created by Cyber on 26/04/2018.
 */

public class ImageModel {

    private String id;
    private String path;
    private Boolean isChecked = false;
    private int order;

    public ImageModel(String id, String path, Boolean isChecked) {
        this.id = id;
        this.path = path;
        this.isChecked = isChecked;
    }

    public ImageModel(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
