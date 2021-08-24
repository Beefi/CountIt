package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityRegisterBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());
        Intent intent = getIntent();
        binding.etUsername.setText(intent.getStringExtra("username"));
        binding.etPassword.setText(intent.getStringExtra("password"));

        binding.btnRegister.setEnabled(false);

        ArrayList<User> userArrayList = new ArrayList<>();

        userDAO.getUsers(userArrayList);

        binding.btnLogin.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, SplashScreenActivity.class);
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            intent2.putExtra("username", username);
            intent2.putExtra("password", password);
            startActivity(intent2);
            finishAndRemoveTask();
        });

        binding.btnRegister.setOnClickListener(v -> {
            User user = new User();
            user.setEmail(binding.etEmail.getText().toString());
            user.setName(binding.etScreenName.getText().toString());
            user.setPassword(binding.etPassword.getText().toString());
            user.setUsername(binding.etUsername.getText().toString());
            user.setWeight(0);

            int numUsers = userArrayList.size();;

            user.setId(numUsers+1);

            userDAO.addUser(user);

            finishAndRemoveTask();
        });

        binding.etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = binding.etPassword.getText().toString();
                String confirmPassword = binding.etConfirmPassword.getText().toString();
                if (editable.length() > 0 && password.length() > 0) {
                    if(password.equals(confirmPassword)){
                        binding.btnRegister.setEnabled(true);
                    }
                    else {
                        binding.btnRegister.setEnabled(false);
                    }
                }
            }
        });
    }
}