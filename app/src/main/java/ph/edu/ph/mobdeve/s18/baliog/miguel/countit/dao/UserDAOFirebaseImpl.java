package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class UserDAOFirebaseImpl implements UserDAO{

    private final String PATH = "users";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(PATH);

    public UserDAOFirebaseImpl() {

    }

    public UserDAOFirebaseImpl(Context context0) {

    }

    @Override
    public long addUser(User user) {
        final long[] result = {-1};
        myRef.push().setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    Log.d("ERROR", "ERROR: " + error.getMessage());
                }
                else {
                    Log.d("SUCCESS", "DATA INSERTED");
                    result[0] = 1L;
                }
            }
        });
        return result[0];
    }

    @Override
    public void getUsers(ArrayList<User> userArrayList) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    User user = new User();
                    String uid = data.getKey();
                    user.setUid(uid);
                    user.setId(data.child("id").getValue(Integer.class));
                    user.setName(data.child("name").getValue(String.class));
                    user.setEmail(data.child("email").getValue(String.class));
                    user.setUsername(data.child("username").getValue(String.class));
                    user.setPassword(data.child("password").getValue(String.class));
                    user.setWeight(data.child("weight").getValue(Integer.class));
                    user.setIntake(data.child("Intake").getValue(ArrayList.class));

                    userArrayList.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Override
    public void getUser(User user) {
    }

    @Override
    public int updateUser(String uID, String toUpdate, String newValue) {
        if (toUpdate.equals("weight")) {
            database.getReference().child(PATH).child(uID).child(toUpdate).setValue(Integer.parseInt(newValue));
        }
        else {
            database.getReference().child(PATH).child(uID).child(toUpdate).setValue(newValue);
        }

        return 1;
    }

    @Override
    public int deleteUser(int userid) {
        return 0;
    }

    @Override
    public long addFood(String uID, Food food) {
        final long[] result = {-1};
        myRef.child(uID).child("intake").child("0").child("foodList").push().setValue(food, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    Log.d("ERROR", "ERROR: " + error.getMessage());
                }
                else {
                    Log.d("SUCCESS", "DATA INSERTED");
                    result[0] = 1L;
                }
            }
        });
        return result[0];
    }
}
