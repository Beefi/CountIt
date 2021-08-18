package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}