package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Date implements Serializable {
    private String month;
    private String day;
    private String year;
    private ArrayList<Food> foodList;
    private ArrayList<Exercise> exerciseList;

    public Date(String month, String day, String year, ArrayList<Food> foodList, ArrayList<Exercise> exerciseList) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.foodList = foodList;
        this.exerciseList = exerciseList;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }
}
