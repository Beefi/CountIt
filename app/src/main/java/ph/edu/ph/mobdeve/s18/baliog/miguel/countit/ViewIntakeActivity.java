package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityViewIntakeBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Exercise;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class ViewIntakeActivity extends AppCompatActivity {

    private ActivityViewIntakeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewIntakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    protected void init() {
        Intent intent = getIntent();
        User curUser = (User) intent.getExtras().getSerializable("data");
        ArrayList<Food> foodIntake = curUser.getFoodIntake();
        ArrayList<Exercise> exerciseIntake = curUser.getExerciseIntake();
        ArrayList<PieEntry> intakeData = new ArrayList<>();

        String[][] dates = new String[2][]; // 0 - FOOD DATES | 1 - EXERCISE DATES

        int foodSum = 0;
        double exerciseSum = 0;

        if (exerciseIntake != null || foodIntake != null) {
            // Initialize Dates for Food
            for (int i = 0; i < foodIntake.size(); i++) {
                dates[0][i] = foodIntake.get(i).getDateTaken();
            }

            // Initialize Dates for Exercise
            for (int i = 0; i < exerciseIntake.size(); i++) {
                dates[1][i] = exerciseIntake.get(i).getDateTaken();
            }

            ArrayList<Food>[] sortedFood = new ArrayList[dates[0].length];
            for (int i = 0; i < foodIntake.size(); i++) {
                for (int x = 0; x < dates[0].length; x++) {
                    if (foodIntake.get(i).getDateTaken().equals(dates[0][x])) {
                        sortedFood[x].add(foodIntake.get(i));
                    }
                }
            }

            ArrayList<Exercise>[] sortedExercise = new ArrayList[dates[1].length];
            for (int i = 0; i < exerciseIntake.size(); i++) {
                for (int x = 0; x < dates[0].length; x++) {
                    if (exerciseIntake.get(i).getDateTaken().equals(dates[1][x])) {
                        sortedExercise[x].add(exerciseIntake.get(i));
                    }
                }
            }

            binding.tvCurrentDate.setText(sortedFood[0].get(0).getDateTaken());

            for (int i = 0; i < sortedFood[0].size(); i++) {
                foodSum += sortedFood[0].get(i).getFood_calories();
            }

            for (int i = 0; i < sortedExercise[0].size(); i++) {
                exerciseSum += sortedExercise[0].get(i).getCal_burnt();
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

        binding.btnFinishView.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}