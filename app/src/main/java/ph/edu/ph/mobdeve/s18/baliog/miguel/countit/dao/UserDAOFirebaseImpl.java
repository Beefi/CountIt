package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Exercise;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class UserDAOFirebaseImpl implements UserDAO{

    private final String PATH = "users";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(PATH);

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

//                    ArrayList<Food> foodList = new ArrayList<>();
//                    ArrayList<Exercise> exerciseList = new ArrayList<>();
//
//                    ArrayList<Food> tempFoodList = new ArrayList<>();
//                    ArrayList<Exercise> tempExerciseList = new ArrayList<>();
//                    tempFoodList = (ArrayList<Food>) data.child("foodIntake").getValue();
//                    tempExerciseList = (ArrayList<Exercise>) data.child("exerciseIntake").getValue();
//
//                    Food tempFood;
//                    Exercise tempExercise;
//
//                    for (int i = 0; i < tempFoodList.size(); i++) {
//                        tempFood = tempFoodList.get(i);
//                        foodList.add(tempFood);
//                    }
//
//                    for (int i = 0; i < tempFoodList.size(); i++) {
//                        tempExercise = tempExerciseList.get(i);
//                        exerciseList.add(tempExercise);
//                    }
//
//                    user.setFoodIntake(foodList);
//                    user.setExerciseIntake(exerciseList);
//
//                    userArrayList.add(user);

//                    HashMap<String, Food> foodMap = new HashMap<>();
//
//                    ArrayList<Food> tempFoodList = (ArrayList<Food>) data.child("foodIntake").getValue();
//
//                    for (int i = 0; i < tempFoodList.size(); i++) {
//                        foodMap = tempFoodList.get(i);
//                    }
//
//                    ArrayList<Food> foodList = new ArrayList<>();
//                    Food tempFood;
//
//                    Iterator hashIterator = foodMap.entrySet().iterator();
//
//                    if (foodMap.values().size() != 0) {
//                        while (hashIterator.hasNext()) {
//                            Map.Entry mapElement = (Map.Entry) hashIterator.next();
//                            String MAP_PATH = mapElement.getKey().toString();
//
//                            tempFood = data.child("foodIntake").child(MAP_PATH).getValue(Food.class);
//                            foodList.add(tempFood);
//                        }
//
//                        user.setFoodIntake(foodList);
//                    }
//
//                    HashMap <String, Exercise> exerciseMap;
//                    exerciseMap = (HashMap<String, Exercise>) data.child("exerciseIntake").getValue();
//
//                    ArrayList<Exercise> exerciseList = new ArrayList<>();
//                    Exercise tempExercise;
//
//                    hashIterator = exerciseMap.entrySet().iterator();
//
//                    if (exerciseMap.values().size() != 0) {
//                        while (hashIterator.hasNext()) {
//                            Map.Entry mapElement = (Map.Entry) hashIterator.next();
//                            String MAP_PATH = mapElement.getKey().toString();
//
//                            tempExercise = data.child("exerciseIntake").child(MAP_PATH).getValue(Exercise.class);
//                            exerciseList.add(tempExercise);
//                        }
//
//                        user.setExerciseIntake(exerciseList);
//                    }
//
//                    userArrayList.add(user);

//                    HashMap<String, Food> foodMap;
//                    foodMap = (HashMap<String, Food>) data.child("foodIntake").getValue();
//
//                    ArrayList<Food> foodList = new ArrayList<>();
//                    Food tempFood;
//
//                    Iterator hashIterator = foodMap.entrySet().iterator();
//
//                    if (foodMap.values().size() != 0) {
//                        while (hashIterator.hasNext()) {
//                            Map.Entry mapElement = (Map.Entry) hashIterator.next();
//                            String MAP_PATH = mapElement.getKey().toString();
//
//                            tempFood = data.child("foodIntake").child(MAP_PATH).getValue(Food.class);
//                            foodList.add(tempFood);
//                        }
//
//                        user.setFoodIntake(foodList);
//                    }
//
//                    HashMap <String, Exercise> exerciseMap;
//                    exerciseMap = (HashMap<String, Exercise>) data.child("exerciseIntake").getValue();
//
//                    ArrayList<Exercise> exerciseList = new ArrayList<>();
//                    Exercise tempExercise;
//
//                    hashIterator = exerciseMap.entrySet().iterator();
//
//                    if (exerciseMap.values().size() != 0) {
//                        while (hashIterator.hasNext()) {
//                            Map.Entry mapElement = (Map.Entry) hashIterator.next();
//                            String MAP_PATH = mapElement.getKey().toString();
//
//                            tempExercise = data.child("exerciseIntake").child(MAP_PATH).getValue(Exercise.class);
//                            exerciseList.add(tempExercise);
//                        }
//
//                        user.setExerciseIntake(exerciseList);
//                    }
//
//                    userArrayList.add(user);
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
        myRef.child(uID).child("foodIntake").push().setValue(food, new DatabaseReference.CompletionListener() {
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
    public long addExercise(String uID, Exercise exercise) {
        final long[] result = {-1};
        myRef.child(uID).child("exerciseIntake").push().setValue(exercise, new DatabaseReference.CompletionListener() {
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
