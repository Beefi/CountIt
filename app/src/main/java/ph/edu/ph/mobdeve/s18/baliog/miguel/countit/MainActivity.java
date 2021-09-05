package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityMainBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    public int NEW_WEIGHT_ACTIVITY = 100;
    public User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        ArrayList<User> userArrayList = (ArrayList<User>) bundle.getSerializable("userList");
        int userNum = intent.getIntExtra("userNum", 0);
        curUser = userArrayList.get(userNum);

        binding.tvName.setText(curUser.getName());
        binding.tvWeight.setText(new StringBuilder().append(this.curUser.getWeight()).append(" lbs").toString());

        binding.btnSetWeight.setOnClickListener(v -> {
            Intent intentSetWeight = new Intent(this, SetNewWeightActivity.class);
            Bundle bundleSetWeight = new Bundle();

            bundleSetWeight.putSerializable("userList", userArrayList);

            intentSetWeight.putExtra("bundle", bundleSetWeight);
            intentSetWeight.putExtra("data", curUser);

            startActivityForResult(intentSetWeight, NEW_WEIGHT_ACTIVITY);
        });

        binding.btnLogMeal.setOnClickListener(v -> {
            Intent intentLogMeal = new Intent(this, LogMealActivity.class);
            intentLogMeal.putExtra("data", curUser);

            startActivity(intentLogMeal);
        });

        binding.btnLogActivity.setOnClickListener(v -> {
            Intent intentLogActivity = new Intent(this, LogActivityActivity.class);
            intentLogActivity.putExtra("data", curUser);

            startActivity(intentLogActivity);
        });

        binding.btnSetGoals.setOnClickListener(v -> {
            // TO DO
            Intent intentSetGoals = new Intent(this, SetGoalsActivity.class);
            intentSetGoals.putExtra("data", curUser);

            startActivity(intentSetGoals);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // NEW_WEIGHT_ACTIVITY
        if (requestCode == NEW_WEIGHT_ACTIVITY)   {
            if (resultCode == RESULT_OK) {
                binding.tvWeight.setText(new StringBuilder().append(data.getStringExtra("weight")).append(" lbs").toString());
                curUser.setWeight(Integer.parseInt(data.getStringExtra("weight")));
            }
        }
    }
}