package ph.edu.ph.mobdeve.s18.baliog.miguel.countit;

import android.content.Intent;
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
    private User curUser;
    private Intent intent;

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

        int calorieIntake = 0;
        for (int i = 0; i < curUser.getFoodIntake().size(); i++) {
            calorieIntake += curUser.getFoodIntake().get(i).getFood_calories();
        }

        binding.tvCalorieIntakeToday.setText(new StringBuilder().append(calorieIntake).append(" calories").toString());

        foodData = new ArrayList<>();

        foodAdapter = new FoodAdapter(LogMealActivity.this, foodData, binding.tvCalorieIntakeToday, curUser);

        binding.rvTodayMeals.setLayoutManager(new LinearLayoutManager(LogMealActivity.this));

        binding.rvTodayMeals.setAdapter(foodAdapter);

        binding.btnMealSearch.setOnClickListener(v -> {
            String query = binding.etMealSearch.getText().toString();

            foodSearch(query);
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

    private void foodSearch(String query) {
        foodData.clear();

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
                        int index = 0;
                        try {
                            foodJSON = (JSONArray) response.get("items");
                            for (int i = 0; i < foodJSON.length(); i++) {
                                JSONObject jsonObject;
                                jsonObject = foodJSON.getJSONObject(i);
                                foodData.add(new Food(
                                        jsonObject.optString("id"),
                                        jsonObject.optString("description"),
                                        jsonObject.optString("product"),
                                        jsonObject.optString("quantity"),
                                        calories));
                            }

                            for (int i = 0; i < foodData.size(); i++) {
                                foodSetCalories(foodData.get(i).getFood_uri(), i, foodData.size()-1);
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
    }

    private void foodSetCalories(String uri, int index, int maxSize) {
        String URL = "https://nutrition-api.esha.com/food/" + uri;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            foodJSON = (JSONArray) response.get("nutrient_data");
                            JSONObject jsonObject;
                            jsonObject = foodJSON.getJSONObject(0);
                            foodData.get(index).setFood_calories(jsonObject.optDouble("value"));

                            // Set Adapter
                            if (index == maxSize) {
                                ArrayList<Food> foodList = new ArrayList<>();
                                foodList.addAll(foodData);

                                binding.rvTodayMeals.setAdapter(new FoodAdapter(LogMealActivity.this, foodList, binding.tvCalorieIntakeToday, curUser));

                                foodAdapter.setData(foodList);
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
    }
}