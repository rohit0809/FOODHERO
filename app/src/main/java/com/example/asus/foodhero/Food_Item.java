package com.example.asus.foodhero;

/**
 * Created by Saurav on 4/30/2017.
 */

public class Food_Item {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }

    public String getRequestid() {
        return Requestid;
    }

    public void setRequestid(String requestid) {
        Requestid = requestid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShelflife() {
        return shelflife;
    }

    public void setShelflife(String shelflife) {
        this.shelflife = shelflife;
    }

    String FoodType;
    String Requestid;
    String quantity;
    String shelflife;
}
