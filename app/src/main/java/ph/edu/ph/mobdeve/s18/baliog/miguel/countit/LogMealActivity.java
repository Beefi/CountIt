package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityLogMealBinding;

public class LogMealActivity extends AppCompatActivity {

    private ActivityLogMealBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.btnFinishLogMeal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}