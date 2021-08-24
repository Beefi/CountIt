package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;

public class User implements Serializable {
    private String username = "";
    private String password = "";
    private String name = "";
    private String email = "";
    private int id = -1;
    private int weight = -1;

    public User() {

    }

    public User(String name, String email, int id) {
        this.id = id;
        this.name = name;
        this.email = email;
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

