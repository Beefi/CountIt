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

        binding.tvCurrentGoalWeight.setText(new StringBuilder().append(curUser.getWeightGoal()).append(" lbs").toString());

        binding.btnSetWeightGoal.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            String newGoal = binding.etNewWeight.getText().toString();

            userDAO.updateUser(curUser.getUid(), "weightGoal", newGoal);
            curUser.setWeightGoal(Integer.parseInt(newGoal));

            returnIntent.putExtra("weightGoal", newGoal);

            setResult(RESULT_OK);
            finish();
        });

        binding.btnFinishSetGoal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}