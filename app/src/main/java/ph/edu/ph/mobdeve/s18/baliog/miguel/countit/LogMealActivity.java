package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter.FoodAdapter;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityLogMealBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class LogMealActivity extends AppCompatActivity {

    private ActivityLogMealBinding binding;
    private ArrayList<Food> foodList;
    private FoodAdapter foodAdapter;
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        Intent intent = getIntent();
        curUser = (User) intent.getExtras().getSerializable("data");

        int calorieIntake = 0;
        for (int i = 0; i < curUser.getFoodIntake().size(); i++) {
            calorieIntake += curUser.getFoodIntake().get(i).getFood_calories();
        }

        binding.tvCalorieIntakeToday.setText(new StringBuilder().append(calorieIntake).append(" calories").toString());

        foodList = new ArrayList<>();

        foodList.add(new Food("URI_TEST", "Margherita Pizza Slice", "Shakey's Pizza", "1 slice", 200, 1));
        foodList.add(new Food("URI_TEST", "Margherita Pizza", "Shakey's Pizza", "1 pie", 2000, 2));
        foodList.add(new Food("URI_TEST", "Pineapple Pizza", "Shakey's Pizza", "1 slice", 180, 3));
        foodList.add(new Food("URI_TEST", "Pepperoni Pizza", "Shakey's Pizza", "1 pie", 2500, 4));
        foodList.add(new Food("URI_TEST", "Meatlovers Pizza", "Shakey's Pizza", "1 slice", 300, 5));
        foodList.add(new Food("URI_TEST", "Cheese Pizza", "Shakey's Pizza", "1 pie", 1500, 6));

        foodAdapter = new FoodAdapter(getApplicationContext(), foodList, intent, binding.tvCalorieIntakeToday, curUser);

        binding.rvTodayMeals.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.rvTodayMeals.setAdapter(foodAdapter);

        binding.btnFinishLogMeal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        binding.btnViewGraph.setOnClickListener(v -> {
            Intent viewGraphIntent = new Intent(this, ViewIntakeActivity.class);
            viewGraphIntent.putExtra("data", curUser);

            startActivity(viewGraphIntent);
        });
    }
}