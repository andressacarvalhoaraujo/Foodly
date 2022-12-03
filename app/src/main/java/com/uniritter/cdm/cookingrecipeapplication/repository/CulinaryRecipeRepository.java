package com.uniritter.cdm.cookingrecipeapplication.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestType;
import com.uniritter.cdm.cookingrecipeapplication.model.CulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.ICulinaryRecipePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CulinaryRecipeRepository {
    private static String TAG = "CulinaryRecipeRepo";
    private ICulinaryRecipeModel culinaryRecipe;
    private List<ICulinaryRecipeModel> culinaryRecipes;
    private Context context;
    private static CulinaryRecipeRepository instance;
    private RequestQueue queue;

    private CulinaryRecipeRepository(Context context) {
        super();
        this.context = context;
        this.culinaryRecipes = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        loadAllCulinaryRecipes();
    }

    public static CulinaryRecipeRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new CulinaryRecipeRepository(context);
        }
        return instance;
    }

    public void loadAllCulinaryRecipes() {
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "http://10.0.0.193:3000/culinaryRecipes",
                null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                culinaryRecipes.add(new CulinaryRecipeModel(
                                        json.getInt("id"),
                                        json.getInt("userId"),
                                        json.getString("title"),
                                        json.getString("description"),
                                        json.getString("ingredients"),
                                        json.getString("preparationMode"),
                                        json.getInt("difficultyLevelId"),
                                        json.getInt("preparationTime"),
                                        json.getInt("yield"),
                                        json.getInt("totalCalories")

                                ));
                            } catch (JSONException e) {
                                Log.e(TAG, "Error on get culinary recipes! Returned message: " + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on get culinary recipes! Returned message: " + error.getMessage());
                    }
                });

        this.queue.add(jaRequest);
    }

    public List<ICulinaryRecipeModel> getAllCulinaryRecipes() {
        return this.culinaryRecipes;
    }

    public void addCulinaryRecipe(int userId, String title, String description, String ingredients, String preparationMode, int difficultyLevelId, int preparationTime, int yield, int totalCalories, ICulinaryRecipePresenter presenter) throws JSONException {
        if (userId == 0 || title == null || TextUtils.isEmpty(title) || title == description || TextUtils.isEmpty(description)
                || ingredients == null || TextUtils.isEmpty(ingredients) || preparationMode == null || TextUtils.isEmpty(preparationMode)
                || difficultyLevelId == 0 || preparationTime == 0 || yield == 0) {
            presenter.onResult(new RequestHelper(true, RequestType.NotAcceptable, "Preencha todos os campos."));
        } else {
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("title", title);
            obj.put("description", description);
            obj.put("ingredients", ingredients);
            obj.put("preparationMode", preparationMode);
            obj.put("difficultyLevelId", difficultyLevelId);
            obj.put("preparationTime", preparationTime);
            obj.put("yield", yield);
            obj.put("totalCalories", totalCalories);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "http://10.0.0.193:3000/culinaryRecipes", obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d(TAG, "Success on add culinary recipe! Response: " + response.toString(4));
                                culinaryRecipes.add(new CulinaryRecipeModel(
                                        response.getInt("id"),
                                        response.getInt("userId"),
                                        response.getString("title"),
                                        response.getString("description"),
                                        response.getString("ingredients"),
                                        response.getString("preparationMode"),
                                        response.getInt("difficultyLevelId"),
                                        response.getInt("preparationTime"),
                                        response.getInt("yield"),
                                        response.getInt("totalCalories")
                                ));

                                presenter.onResult(new RequestHelper(true, RequestType.OK));
                            } catch (JSONException e) {
                                Log.e(TAG, "Error on add culinary recipe! Returned message: " + e.getMessage());
                                e.printStackTrace();

                                presenter.onResult(new RequestHelper(false, RequestType.BadRequest, e.getMessage()));
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error on add culinary recipe! Returned message: " + error.getMessage());
                    error.printStackTrace();

                    presenter.onResult(new RequestHelper(false, RequestType.BadRequest, error.getMessage()));
                }
            });

            this.queue.add(req);
        }
    }
}
