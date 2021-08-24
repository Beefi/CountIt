package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public interface UserDAO {
    long addUser(User user);
    ArrayList<User> getUsers();
    User getUser(int username);
    int updateUser(User user);
    int deleteUser(int userid);
}