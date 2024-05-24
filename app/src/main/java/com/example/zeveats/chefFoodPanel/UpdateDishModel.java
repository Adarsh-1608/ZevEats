package com.example.zeveats.chefFoodPanel;

public class UpdateDishModel {
    private String Chefid;
    private String Dishes;
    private String RandomUID;
    private String Description;
    private String Quantity;
    private String Price;
    private String ImageURL;

    // Default constructor required for calls to DataSnapshot.getValue(UpdateDishModel.class)
    public UpdateDishModel() {
    }

    // Getters and setters
    public String getChefid() {
        return Chefid;
    }

    public void setChefid(String chefid) {
        Chefid = chefid;
    }

    public String getDishes() {
        return Dishes;
    }

    public void setDishes(String dishes) {
        Dishes = dishes;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
