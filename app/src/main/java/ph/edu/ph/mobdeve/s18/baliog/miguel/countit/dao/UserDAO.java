package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Exercise;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User_DB;

public interface UserDAO {
    long addUser(User user);
    long addUser_DB(User_DB user);
    void getUsers(ArrayList<User> userArrayList);
    void getUser(User user);
    int updateUser(String uID, String toUpdate, String newValue);
    int deleteUser(int userid);
    long addFood(String uID, Food food);
    long addExercise(String uID, Exercise exercise);
}