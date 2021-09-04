package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public interface UserDAO {
    long addUser(User user);
    void getUsers(ArrayList<User> userArrayList);
    void getUser(User user);
    int updateUser(String uID, String toUpdate, String newValue);
    int deleteUser(int userid);
    long addFood(String uID, Food food);
}