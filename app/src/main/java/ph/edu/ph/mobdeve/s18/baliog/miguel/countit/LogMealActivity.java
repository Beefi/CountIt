package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter.FoodAdapter;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityLogMealBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;

public class LogMealActivity extends AppCompatActivity {

    private ActivityLogMealBinding binding;
    private ArrayList<Food> foodList;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        Intent intent = getIntent();

        foodList = new ArrayList<>();

        foodList.add(new Food("URI_TEST", "Margherita Pizza Slice", "Shakey's Pizza", "1 slice", 200, 1));
        foodList.add(new Food("URI_TEST", "Margherita Pizza", "Shakey's Pizza", "1 pie", 2000, 2));
        foodList.add(new Food("URI_TEST", "Pineapple Pizza", "Shakey's Pizza", "1 slice", 180, 3));
        foodList.add(new Food("URI_TEST", "Pepperoni Pizza", "Shakey's Pizza", "1 pie", 2500, 4));
        foodList.add(new Food("URI_TEST", "Meatlovers Pizza", "Shakey's Pizza", "1 slice", 300, 5));
        foodList.add(new Food("URI_TEST", "Cheese Pizza", "Shakey's Pizza", "1 pie", 1500, 6));

        foodAdapter = new FoodAdapter(getApplicationContext(), foodList, intent);

        binding.rvTodayMeals.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.rvTodayMeals.setAdapter(foodAdapter);

        binding.btnFinishLogMeal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}