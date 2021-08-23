package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public interface UserDAO {
    long addUser(User user);
    ArrayList<User> getUsers();
    User getUser(int userid);
    int updateUser(User user);
    int deleteUser(int userid);
}