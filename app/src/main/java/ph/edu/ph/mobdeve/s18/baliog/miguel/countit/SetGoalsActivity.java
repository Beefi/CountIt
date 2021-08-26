package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivitySetGoalsBinding;

public class SetGoalsActivity extends AppCompatActivity {

    private ActivitySetGoalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetGoalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.btnFinishSetGoal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}