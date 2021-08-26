package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityLogActivityBinding;

public class LogActivityActivity extends AppCompatActivity {

    private ActivityLogActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        binding.btnFinishLogActivity.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}