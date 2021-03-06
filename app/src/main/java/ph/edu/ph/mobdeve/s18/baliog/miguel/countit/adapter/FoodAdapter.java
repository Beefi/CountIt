package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.R;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final int limit = 10;
    private ArrayList<Food> foodList;
    private Context context;
    private TextView tvCalorieIntakeToday;
    private User curUser;
    private String food_uri;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList, TextView tvCalorieIntakeToday, User curUser) {
        this.foodList = new ArrayList<>();
        this.foodList = foodArrayList;
        this.context = context;
        this.tvCalorieIntakeToday = tvCalorieIntakeToday;
        this.curUser = curUser;
    }

    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);

        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.FoodViewHolder holder, int position) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        UserDAO userDAO = new UserDAOFirebaseImpl(context.getApplicationContext());
        ArrayList<User> userArrayList = new ArrayList<>();
        userDAO.getUsers(userArrayList);

        holder.tv_foodTitle.setText(this.foodList.get(position).getFood_name());
        holder.tv_foodFrom.setText(this.foodList.get(position).getFood_location());
        holder.tv_foodWeight.setText(this.foodList.get(position).getFood_weight());

        food_uri = this.foodList.get(position).getFood_uri();

        double foodCalories = this.foodList.get(position).getFood_calories();

        holder.tv_foodCalories.setText(new StringBuilder().append(df2.format(foodCalories)).append(" cal").toString());

        holder.food_card.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setTitle("Confirm Meal");
            builder.setMessage("Log "
                    + holder.tv_foodTitle.getText().toString()
                    + " with "
                    + holder.tv_foodCalories.getText().toString());

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Food food = new Food();
                    food.setFood_uri(food_uri);
                    food.setFood_name(holder.tv_foodTitle.getText().toString());
                    food.setFood_location(holder.tv_foodFrom.getText().toString());
                    food.setFood_weight(holder.tv_foodWeight.getText().toString());
                    food.setFood_calories(foodCalories);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    String timeStamp = dtf.format(now);

                    food.setDateTaken(timeStamp);

                    userDAO.addFood(curUser.getUid(), food);
                    curUser.getFoodIntake().add(food);

                    // Compute New Calorie Intake
                    int foodSum = 0;
                    for (int i = 0; i < curUser.getFoodIntake().size(); i++) {
                        foodSum += curUser.getFoodIntake().get(i).getFood_calories();
                    }

                    tvCalorieIntakeToday.setText(new StringBuilder().append(foodSum).append(" calories").toString());

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
        if (this.foodList.size() > limit) {
            return limit;
        }
        else {
            return this.foodList.size();
        }
    }

    public void setData(ArrayList<Food> foodList){
        this.foodList.clear();
        this.foodList.addAll(foodList);
        notifyDataSetChanged();
    }

    protected class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tv_foodTitle, tv_foodFrom, tv_foodWeight, tv_foodCalories;
        CardView food_card;

        public FoodViewHolder(View view) {
            super(view);
            tv_foodTitle = view.findViewById(R.id.tv_foodTitle);
            tv_foodFrom = view.findViewById(R.id.tv_foodFrom);
            tv_foodWeight = view.findViewById(R.id.tv_foodWeight);
            tv_foodCalories = view.findViewById(R.id.tv_foodCalories);
            food_card = view.findViewById(R.id.food_card);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.food_card.setOnClickListener(onClickListener);
        }
    }
}
