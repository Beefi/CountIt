package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Date implements Serializable {
    private String month;
    private String day;
    private String year;
    private ArrayList<Food> foodArrayList;
    private ArrayList<Exercise> exerciseArrayList;

    public Date(String month, String day, String year, ArrayList<Food> foodArrayList, ArrayList<Exercise> exerciseArrayList) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.foodArrayList = foodArrayList;
        this.exerciseArrayList = exerciseArrayList;
    }

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
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

    public ArrayList<Food> getFoodArrayList() {
        return foodArrayList;
    }

    public void setFoodArrayList(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }
}
