package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class UserDAOFirebaseImpl implements UserDAO {

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
    public ArrayList<User> getUsers() {
        ArrayList<User> result = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    User user = new User();
                    user.setId(data.child("id").getValue(Integer.class));
                    user.setName(data.child("name").getValue(String.class));
                    user.setEmail(data.child("email").getValue(String.class));

                    result.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return result;
    }

    @Override
    public User getUser(int username) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(int userid) {
        return 0;
    }
}
