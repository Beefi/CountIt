package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.adapter.FoodAdapter;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.databinding.ActivityLogMealBinding;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.Food;
import ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model.User;

public class LogMealActivity extends AppCompatActivity {

    private ActivityLogMealBinding binding;
    private JSONArray foodJSON;
    private ArrayList<Food> foodData = new ArrayList<>();
    private FoodAdapter foodAdapter;
    private ProgressDialog progressDialog;
    private double GET_Calories = 0.0;
    private User curUser;
    private Intent intent;
    private FoodSearchTask foodSearchTask;
    private FoodSetCaloriesTask foodSetCaloriesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        intent = getIntent();
        curUser = (User) intent.getExtras().getSerializable("data");
        foodSearchTask = new FoodSearchTask();
        foodSetCaloriesTask = new FoodSetCaloriesTask();

        int calorieIntake = 0;
        for (int i = 0; i < curUser.getFoodIntake().size(); i++) {
            calorieIntake += curUser.getFoodIntake().get(i).getFood_calories();
        }

        binding.tvCalorieIntakeToday.setText(new StringBuilder().append(calorieIntake).append(" calories").toString());

        foodData = new ArrayList<>();

        foodData.add(new Food("URI_TEST", "Margherita Pizza Slice", "Shakey's Pizza", "1 slice", 200));
        foodData.add(new Food("URI_TEST", "Margherita Pizza", "Shakey's Pizza", "1 pie", 2000));
        foodData.add(new Food("URI_TEST", "Pineapple Pizza", "Shakey's Pizza", "1 slice", 180));
        foodData.add(new Food("URI_TEST", "Pepperoni Pizza", "Shakey's Pizza", "1 pie", 2500));
        foodData.add(new Food("URI_TEST", "Meatlovers Pizza", "Shakey's Pizza", "1 slice", 300));
        foodData.add(new Food("URI_TEST", "Cheese Pizza", "Shakey's Pizza", "1 pie", 1500));

        foodAdapter = new FoodAdapter(LogMealActivity.this, foodData, binding.tvCalorieIntakeToday, curUser);

        binding.rvTodayMeals.setLayoutManager(new LinearLayoutManager(LogMealActivity.this));

        binding.rvTodayMeals.setAdapter(foodAdapter);

        binding.btnMealSearch.setOnClickListener(v -> {
            foodData.clear();
            String query = binding.etMealSearch.getText().toString();
            new FoodSearchTask().execute(query);
        });

        binding.btnFinishLogMeal.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        binding.btnViewGraph.setOnClickListener(v -> {
            Intent viewGraphIntent = new Intent(this, ViewIntakeActivity.class);
            viewGraphIntent.putExtra("data", curUser);

            startActivity(viewGraphIntent);
        });
    }

    class FoodSearchTask extends AsyncTask<String, Integer, ArrayList<Food>> {

        public void restart() {
            foodSearchTask = new FoodSearchTask();
        }

        @Override
        protected void onPostExecute(ArrayList<Food> foodArrayList) {
            super.onPostExecute(foodArrayList);

            foodSearchTask.restart();

            Food[] foodList = new Food[foodData.size()];

            for (int i = 0; i < foodData.size(); i++) {
                foodList[i] = foodData.get(i);
            }

            new FoodSetCaloriesTask().execute(foodList);
        }

        @Override
        protected ArrayList<Food> doInBackground(String... searchTerms) {
            int progress;

            String query = searchTerms[0];
            String URL = "https://nutrition-api.esha.com/foods?query=" + query + "&start=0&count=10&spell=true";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            double calories = 0;
                            try {
                                foodJSON = (JSONArray) response.get("items");
                                for (int i = 0; i < 10; i++) {
                                    JSONObject jsonObject;
                                    jsonObject = foodJSON.getJSONObject(i);
                                    foodData.add(new Food(
                                            jsonObject.optString("id"),
                                            jsonObject.optString("description"),
                                            jsonObject.optString("product"),
                                            jsonObject.optString("quantity"),
                                            calories));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    String key = "c609de7862b24353a9bcabc36c24780f";
                    headers.put("Accept", "application/json");
                    headers.put("Ocp-Apim-Subscription-Key", key);
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);

            return foodData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LogMealActivity.this);
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setMessage("Searching for your food...");

            progressDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }

    class FoodSetCaloriesTask extends AsyncTask<Food, Integer, ArrayList<Food>> {

        public void restart() {
            foodSetCaloriesTask = new FoodSetCaloriesTask();
        }

        @Override
        protected void onPostExecute(ArrayList<Food> foodArrayList) {
            super.onPostExecute(foodArrayList);

            ArrayList<Food> foodList = new ArrayList<>();

            foodList.addAll(foodData);

            binding.rvTodayMeals.setAdapter(new FoodAdapter(LogMealActivity.this, foodList, binding.tvCalorieIntakeToday, curUser));

            foodAdapter.setData(foodList);

            foodSetCaloriesTask.restart();

            progressDialog.dismiss();
        }

        @Override
        protected ArrayList<Food> doInBackground(Food... foods) {
            for (int i = 0; i < foods.length; i++) {
                String uri = foods[i].getFood_uri();
                String URL = "https://nutrition-api.esha.com/food/" + uri;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                GET_Calories = 0;
                                try {
                                    foodJSON = (JSONArray) response.get("nutrient_data");
                                    JSONObject jsonObject;
                                    jsonObject = foodJSON.getJSONObject(0);
                                    GET_Calories = jsonObject.optDouble("value");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        String key = "c609de7862b24353a9bcabc36c24780f";
                        headers.put("Accept", "application/json");
                        headers.put("Ocp-Apim-Subscription-Key", key);
                        return headers;
                    }
                };

                requestQueue.add(jsonObjectRequest);

                foodData.get(i).setFood_calories(GET_Calories);
            }

            publishProgress(100);

            return foodData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            publishProgress(50);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }
}