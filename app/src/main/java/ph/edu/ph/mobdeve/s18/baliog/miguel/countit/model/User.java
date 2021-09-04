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
    private int weight = -1;
    private ArrayList<Date> Intake;

    public ArrayList<Date> getIntake() {
        return Intake;
    }

    public void setIntake(ArrayList<Date> Intake) {
        this.Intake = Intake;
    }

    public User() {

    }

    public User(String username, String password, String name, String email, int id, int weight) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.name = name;
        this.email = email;
        this.weight = weight;
        this.Intake = new ArrayList<>();
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
}

