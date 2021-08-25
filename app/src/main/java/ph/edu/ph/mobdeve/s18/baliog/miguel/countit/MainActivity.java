package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityMainBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        ArrayList<User> userArrayList = (ArrayList<User>) bundle.getSerializable("userList");
        int userNum = intent.getIntExtra("userNum", 0);
        User curUser = userArrayList.get(userNum);

        binding.tvName.setText(curUser.getName());
        binding.tvWeight.setText(new StringBuilder().append(curUser.getWeight()).append(" lbs").toString());
    }
}