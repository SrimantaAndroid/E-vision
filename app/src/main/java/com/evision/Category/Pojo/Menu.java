package com.evision.Category.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menu {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
    @SerializedName("category_image")
    @Expose
    private String category_image;
@SerializedName("sub")
@Expose
private List<Sub> sub = null;


    private boolean isOpen=false;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
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

public List<Sub> getSub() {
return sub;
}

public void setSub(List<Sub> sub) {
this.sub = sub;
}

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }
}