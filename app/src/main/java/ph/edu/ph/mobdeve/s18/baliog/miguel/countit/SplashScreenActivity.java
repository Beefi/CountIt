package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivitySplashScreenBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());
        ArrayList<User> userArrayList = new ArrayList<>();
        userDAO.getUsers(userArrayList);

        Intent intent2 = getIntent();
        binding.etUsername.setText(intent2.getStringExtra("username"));
        binding.etPassword.setText(intent2.getStringExtra("password"));

        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            Bundle bundle = new Bundle();
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            intent.putExtra("username", username);
            intent.putExtra("password", password);

            bundle.putSerializable("userList", userArrayList);

            intent.putExtra("bundle", bundle);

            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            // Verify User Account
            int userNum = verifyUser(username, password, userArrayList);
            if (userNum != -1) {
                Intent intent = new Intent (this, MainActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("userList", userArrayList);

                intent.putExtra("bundle", bundle);
                intent.putExtra("userNum", userNum);

                startActivity(intent);
            }
            else {
                binding.etPassword.setError("Username cannot be found / Password does not match");
            }
        });
    }

    private int verifyUser(String username, String password, ArrayList<User> userArrayList) {
        String toBeChecked;
        for (int x = 0; x < userArrayList.size(); x++) {
            toBeChecked = userArrayList.get(x).getUsername();

            if(username.equals(toBeChecked)) {
                for (int i = 0; i < userArrayList.size(); i++) {
                    toBeChecked = userArrayList.get(x).getPassword();

                    if (password.equals(toBeChecked))
                        return x; // Verified
                }
            }
        }

        return -1;
    }
}