package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;

public class Food implements Serializable {
    private String food_uri = "";
    private String food_name = "";
    private String food_location = "";
    private String food_weight = "";
    private int food_calories = -1;
    private int food_id = -1;

    public Food() {
        this.food_uri = "";
        this.food_name = "";
        this.food_location = "";
        this.food_weight = "";
        this.food_calories = -1;
        this.food_id = -1;
    }

    public Food(String food_uri, String food_name, String food_location, String food_weight, int food_calories, int food_id) {
        this.food_uri = food_uri;
        this.food_name = food_name;
        this.food_location = food_location;
        this.food_weight = food_weight;
        this.food_calories = food_calories;
        this.food_id = food_id;
    }

    public String getFood_uri() {
        return food_uri;
    }

    public void setFood_uri(String food_uri) {
        this.food_uri = food_uri;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_location() {
        return food_location;
    }

    public void setFood_location(String food_location) {
        this.food_location = food_location;
    }

    public String getFood_weight() {
        return food_weight;
    }

    public void setFood_weight(String food_weight) {
        this.food_weight = food_weight;
    }

    public int getFood_calories() {
        return food_calories;
    }

    public void setFood_calories(int food_calories) {
        this.food_calories = food_calories;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
}
