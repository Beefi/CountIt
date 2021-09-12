package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivitySetGoalsBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class SetGoalsActivity extends AppCompatActivity {

    private ActivitySetGoalsBinding binding;
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetGoalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());
        Intent intent = getIntent();
        curUser = (User) intent.getExtras().getSerializable("data");

        double weightInKG = (curUser.getWeightGoal() / 2.205);

        double computation = ((weightInKG * 24) * 0.95) * 1.3;

        int caloriesNeeded = (int) computation;

        binding.tvRecommendedCalories.setText(new StringBuilder().append(caloriesNeeded).append(" cal").toString());

        binding.tvCurrentGoalWeight.setText(new StringBuilder().append(curUser.getWeightGoal()).append(" lbs").toString());

        binding.btnSetWeightGoal.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            String newGoal = binding.etNewWeight.getText().toString();

            userDAO.updateUser(curUser.getUid(), "weightGoal", newGoal);
            curUser.setWeightGoal(Integer.parseInt(newGoal));

            double post_comp = (((curUser.getWeightGoal() / 2.205) * 24) * 0.95) * 1.3;
            int post_calories = (int) post_comp;

            binding.tvCurrentGoalWeight.setText(new StringBuilder().append(curUser.getWeightGoal()).append(" lbs").toString());
            binding.tvRecommendedCalories.setText(new StringBuilder().append(post_calories).append(" cal").toString());

            returnIntent.putExtra("weightGoal", newGoal);

            setResult(RESULT_OK, returnIntent);
            finish();
        });

        binding.btnFinishSetGoal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}