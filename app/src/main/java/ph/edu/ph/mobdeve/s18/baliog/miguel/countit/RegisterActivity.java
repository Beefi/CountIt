package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        binding.etUsername.setText(intent.getStringExtra("username"));
        binding.etPassword.setText(intent.getStringExtra("password"));

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, SplashScreenActivity.class);
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            intent2.putExtra("username", username);
            intent2.putExtra("password", password);
            startActivity(intent2);
            finishAndRemoveTask();
        });
    }
}