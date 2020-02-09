package com.smartwater.demo.domain;

public class Device {
    private Integer id;
    private Integer parent_id;
    private Integer brother_id;
    private String name;
    private String model;
    private String entry_time;
    private Integer type;
    private String function_id;
    private String image_name;
    private String image_name_error;
    private String top;
    private String leftu;
    private String background_name;

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getBrother_id() {
        return brother_id;
    }

    public void setBrother_id(int brother_id) {
        this.brother_id = brother_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFunction_id() {
        return function_id;
    }

    public void setFunction_id(String function_id) {
        this.function_id = function_id;
    }
    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getLeftu() {
        return leftu;
    }

    public void setLeftu(String leftu) {
        this.leftu = leftu;
    }

    public String getBackground_name() {
        return background_name;
    }

    public void setBackground_name(String background_name) {
        this.background_name = background_name;
    }

    public String getImage_name_error() {
        return image_name_error;
    }

    public void setImage_name_error(String image_name_error) {
        this.image_name_error = image_name_error;
    }
}
