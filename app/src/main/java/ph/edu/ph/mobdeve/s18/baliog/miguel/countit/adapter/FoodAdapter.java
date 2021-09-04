package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.R;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAO;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.dao.UserDAOFirebaseImpl;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<Food> foodList = new ArrayList<>();
    private Context context;
    private Intent intent;
    private String userID;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList, Intent intent) {
        this.foodList = foodArrayList;
        this.context = context;
        this.intent = intent;
    }

    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);

        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.FoodViewHolder holder, int position) {
        UserDAO userDAO = new UserDAOFirebaseImpl(context.getApplicationContext());
        ArrayList<User> userArrayList = new ArrayList<>();
        userDAO.getUsers(userArrayList);
        User curUser = (User) this.intent.getExtras().getSerializable("data");

        holder.iv_foodPicture.setImageResource(R.drawable.sample);
        holder.tv_foodTitle.setText(this.foodList.get(position).getFood_name());
        holder.tv_foodFrom.setText(this.foodList.get(position).getFood_location());
        holder.tv_foodWeight.setText(this.foodList.get(position).getFood_weight());
        holder.tv_foodCalories.setText(new StringBuilder().append(this.foodList.get(position).getFood_calories()).append(" cal").toString());

        int foodCalories = this.foodList.get(position).getFood_calories();

        holder.food_card.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setTitle("Confirm Meal");
            builder.setMessage("Log "
                    + holder.tv_foodTitle.getText().toString()
                    + " with "
                    + holder.tv_foodCalories.getText().toString());

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Food food = new Food();
                    food.setFood_uri(holder.iv_foodPicture.getDrawable().toString());
                    food.setFood_name(holder.tv_foodTitle.getText().toString());
                    food.setFood_location(holder.tv_foodFrom.getText().toString());
                    food.setFood_weight(holder.tv_foodWeight.getText().toString());
                    food.setFood_calories(foodCalories);

                    userDAO.addFood(curUser.getUid(), food);
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
        return this.foodList.size();
    }

    protected class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_foodPicture;
        TextView tv_foodTitle, tv_foodFrom, tv_foodWeight, tv_foodCalories;
        CardView food_card;

        public FoodViewHolder(View view) {
            super(view);
            iv_foodPicture = view.findViewById(R.id.iv_foodPicture);
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
