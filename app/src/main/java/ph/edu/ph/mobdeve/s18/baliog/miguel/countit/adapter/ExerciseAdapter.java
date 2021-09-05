package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.R;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Exercise;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private ArrayList<Exercise> exerciseList = new ArrayList<>();
    private Context context;
    private Intent intent;
    private TextView tvCaloriesBurnt;
    private User curUser;

    public ExerciseAdapter(Context context, ArrayList<Exercise> exerciseList, Intent intent, TextView tvCaloriesBurnt, User curUser) {
        this.exerciseList = exerciseList;
        this.context = context;
        this.intent = intent;
        this.tvCaloriesBurnt = tvCaloriesBurnt;
        this.curUser = curUser;
    }

    @Override
    public ExerciseAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        ExerciseAdapter.ExerciseViewHolder exerciseViewHolder = new ExerciseAdapter.ExerciseViewHolder(view);

        return exerciseViewHolder;
    }

    @Override
    public void onBindViewHolder(ExerciseAdapter.ExerciseViewHolder holder, int position) {
        UserDAO userDAO = new UserDAOFirebaseImpl(context.getApplicationContext());
        ArrayList<User> userArrayList = new ArrayList<>();
        userDAO.getUsers(userArrayList);

        holder.tv_exerciseTitle.setText(this.exerciseList.get(position).getEx_name());

        double exerciseCalories = this.exerciseList.get(position).getCal_burnt();

        holder.tv_exerciseCalories.setText(new StringBuilder().append(exerciseCalories).append(" burnt per hour").toString());

        holder.exercise_card.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setTitle("Confirm Meal");
            builder.setMessage("Log "
                    + holder.tv_exerciseTitle.getText().toString()
                    + " with "
                    + holder.tv_exerciseCalories.getText().toString() + " burnt per hour");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Exercise exercise = new Exercise();
                    exercise.setCal_burnt(exerciseCalories);
                    exercise.setEx_name(holder.tv_exerciseTitle.getText().toString());

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    String timeStamp = dtf.format(now);

                    exercise.setDateTaken(timeStamp);

                    userDAO.addExercise(curUser.getUid(), exercise);
                    curUser.getExerciseIntake().add(exercise);

                    // Compute New Calorie Intake
                    int exSum = 0;
                    for (int i = 0; i < curUser.getExerciseIntake().size(); i++) {
                        exSum += curUser.getExerciseIntake().get(i).getCal_burnt();
                    }

                    tvCaloriesBurnt.setText(new StringBuilder().append(exSum).append(" calories").toString());

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();

            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return this.exerciseList.size();
    }

    protected class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exerciseTitle, tv_exerciseCalories;
        CardView exercise_card;

        public ExerciseViewHolder(View view) {
            super(view);
            tv_exerciseTitle = view.findViewById(R.id.tv_exerciseTitle);
            tv_exerciseCalories = view.findViewById(R.id.tv_exerciseCalories);
            exercise_card = view.findViewById(R.id.exercise_card);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.exercise_card.setOnClickListener(onClickListener);
        }
    }
}
