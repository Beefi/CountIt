package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.R;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<Food> foodArrayList = new ArrayList<>();
    private Context context;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
        this.context = context;
    }

    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);

        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(FoodAdapter.FoodViewHolder holder, int position) {
        holder.iv_foodPicture.setImageResource(R.drawable.sample);
        holder.tv_foodTitle.setText(this.foodArrayList.get(position).getFood_name());
        holder.tv_foodFrom.setText(this.foodArrayList.get(position).getFood_location());
        holder.tv_foodWeight.setText(this.foodArrayList.get(position).getFood_weight());
        holder.tv_foodCalories.setText(new StringBuilder().append(this.foodArrayList.get(position).getFood_calories()).append(" cal").toString());

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
        return this.foodArrayList.size();
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
