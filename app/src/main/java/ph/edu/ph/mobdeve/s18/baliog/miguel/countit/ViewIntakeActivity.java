package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityViewIntakeBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Exercise;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

import static android.content.ContentValues.TAG;

public class ViewIntakeActivity extends AppCompatActivity {

    private ActivityViewIntakeBinding binding;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ArrayList<PieEntry> intakeData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewIntakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void init() {
        Intent intent = getIntent();
        User curUser = (User) intent.getExtras().getSerializable("data");
        ArrayList<Food> foodIntake = curUser.getFoodIntake();
        ArrayList<Exercise> exerciseIntake = curUser.getExerciseIntake();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dtf.format(now);

        binding.tvCurrentDate.setText(timeStamp);

        loadIntakeData(foodIntake, exerciseIntake, timeStamp);

        binding.btnFinishView.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        binding.tvCurrentDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(ViewIntakeActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (view, year, month, day) -> {
            Log.d(TAG, "onDateSet: date: " + month + "/" + day + "/" + year);

            month++;
            String date = month + "/" + day + "/" + year;
            binding.tvCurrentDate.setText(date);

            intakeData.clear();
            binding.pieChart.invalidate();
            binding.pieChart.clear();

            loadIntakeData(foodIntake, exerciseIntake, date);
        };
    }
    
    protected void loadIntakeData(ArrayList<Food> foodIntake, ArrayList<Exercise> exerciseIntake, String date) {
        int foodSum = 0;
        double exerciseSum = 0;

        if (exerciseIntake != null || foodIntake != null) {
            for (int i = 0; i < foodIntake.size(); i++) {
                if (foodIntake.get(i).getDateTaken().equals(date))
                    foodSum += foodIntake.get(i).getFood_calories();
            }

            for (int i = 0; i < exerciseIntake.size(); i++) {
                if (exerciseIntake.get(i).getDateTaken().equals(date))
                    exerciseSum += exerciseIntake.get(i).getCal_burnt();
            }

            intakeData.add(new PieEntry(foodSum, "Eaten"));
            intakeData.add(new PieEntry((float) exerciseSum, "Burnt"));

            PieDataSet pieDataSet = new PieDataSet(intakeData, "Calories");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16f);

            PieData pieData = new PieData(pieDataSet);

            binding.pieChart.setData(pieData);
            binding.pieChart.getDescription().setEnabled(false);
            binding.pieChart.setCenterText("Calories");
            binding.pieChart.animate();
        }
    }
}