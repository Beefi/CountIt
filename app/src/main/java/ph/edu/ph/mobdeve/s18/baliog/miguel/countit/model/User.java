package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String uid = "";
    private String username = "";
    private String password = "";
    private String name = "";
    private String email = "";
    private int id = -1;
    private int weight = 0;
    private int weightGoal = 0;
    private ArrayList<Food> foodIntake = new ArrayList<Food>();
    private ArrayList<Exercise> exerciseIntake = new ArrayList<Exercise>();

    public User() {

    }

    public User(String username, String password, String name, String email, int id, int weight, int weightGoal) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.name = name;
        this.email = email;
        this.weight = weight;
        this.weightGoal = weightGoal;
        this.foodIntake = new ArrayList<>();
        this.exerciseIntake = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(int weightGoal) {
        this.weightGoal = weightGoal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Food> getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(ArrayList<Food> foodIntake) {
        this.foodIntake = foodIntake;
    }

    public ArrayList<Exercise> getExerciseIntake() {
        return exerciseIntake;
    }

    public void setExerciseIntake(ArrayList<Exercise> exerciseIntake) {
        this.exerciseIntake = exerciseIntake;
    }
}

