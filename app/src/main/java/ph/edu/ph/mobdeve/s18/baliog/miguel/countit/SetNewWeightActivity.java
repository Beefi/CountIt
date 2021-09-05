package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivitySetNewWeightBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class SetNewWeightActivity extends AppCompatActivity {

    private ActivitySetNewWeightBinding binding;
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetNewWeightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        UserDAO userDAO = new UserDAOFirebaseImpl(getApplicationContext());
        Intent intent = getIntent();
        curUser = (User) intent.getExtras().getSerializable("data");

        binding.tvCurrentWeight.setText(new StringBuilder().append(curUser.getWeight()).append(" lbs").toString());

        binding.btnSubmitWeight.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            String newWeight = binding.etNewWeight.getText().toString();

            userDAO.updateUser(curUser.getUid(),"weight",newWeight);
            curUser.setWeight(Integer.parseInt(newWeight));

            returnIntent.putExtra("weight", newWeight);

            setResult(RESULT_OK, returnIntent);
            finish();
        });

        binding.btnCancelWeight.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}