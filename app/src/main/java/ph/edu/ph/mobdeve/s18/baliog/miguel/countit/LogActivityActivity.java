package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter.ExerciseAdapter;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityLogActivityBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Exercise;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class LogActivityActivity extends AppCompatActivity {

    private ActivityLogActivityBinding binding;
    private ArrayList<Exercise> exerciseList;
    private ExerciseAdapter exerciseAdapter;
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        Intent intent = getIntent();
        curUser = (User) intent.getExtras().getSerializable("data");

        int caloriesBurnt = 0;
        for (int i = 0; i < curUser.getExerciseIntake().size(); i++) {
            caloriesBurnt += curUser.getExerciseIntake().get(i).getCal_burnt();
        }

        binding.tvCaloriesBurnt.setText(new StringBuilder().append(caloriesBurnt).append(" cal").toString());

        exerciseList = new ArrayList<>();

        exerciseList.add(new Exercise("Jumping Jacks", 600));
        exerciseList.add(new Exercise("Push-ups", 420));
        exerciseList.add(new Exercise("Weight Lifting", 180));
        exerciseList.add(new Exercise("Burpies", 600));
        exerciseList.add(new Exercise("Walking", 210));
        exerciseList.add(new Exercise("Squats", 194));
        exerciseList.add(new Exercise("Jogging", 398));
        exerciseList.add(new Exercise("Lunges", 180));

        exerciseAdapter = new ExerciseAdapter(getApplicationContext(), exerciseList, intent, binding.tvCaloriesBurnt, curUser);

        binding.rvActivities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.rvActivities.setAdapter(exerciseAdapter);

        binding.btnFinishLogActivity.setOnClickListener(v -> {
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