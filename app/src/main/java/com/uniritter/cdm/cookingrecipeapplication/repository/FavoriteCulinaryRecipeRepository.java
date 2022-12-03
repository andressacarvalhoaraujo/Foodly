package com.uniritter.cdm.cookingrecipeapplication.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestType;
import com.uniritter.cdm.cookingrecipeapplication.model.FavoriteCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IFavoriteCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.ICulinaryRecipePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCulinaryRecipeRepository {
    private static String TAG = "FavCulinaryRecRepo";
    private IFavoriteCulinaryRecipeModel favoriteCulinaryRecipe;
    private List<IFavoriteCulinaryRecipeModel> favoriteCulinaryRecipes;
    private Context context;
    private static FavoriteCulinaryRecipeRepository instance;
    private RequestQueue queue;

    private FavoriteCulinaryRecipeRepository(Context context) {
        super();
        this.context = context;
        this.favoriteCulinaryRecipes = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        loadAllFavoriteCulinaryRecipes();
    }

    public static FavoriteCulinaryRecipeRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new FavoriteCulinaryRecipeRepository(context);
        }
        return instance;
    }

    public void loadAllFavoriteCulinaryRecipes() {
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "http://10.0.0.193:3000/favoriteCulinaryRecipes",
                null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                favoriteCulinaryRecipes.add(new FavoriteCulinaryRecipeModel(
                                        json.getInt("id"),
                                        json.getInt("userId"),
                                        json.getInt("culinaryRecipeId")
                                ));
                            } catch (JSONException e) {
                                Log.e(TAG, "Error on get favorite culinary recipes! Returned message: " + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on get favorite culinary recipes! Returned message: " + error.getMessage());
                    }
                });

        this.queue.add(jaRequest);
    }

    public IFavoriteCulinaryRecipeModel getFavoriteCulinaryRecipeByUserIdAndCulinaryRecipeId(int userId, int culinaryRecipeId) {
        IFavoriteCulinaryRecipeModel f = null;

        for(IFavoriteCulinaryRecipeModel fav : this.favoriteCulinaryRecipes) {
            if (userId == fav.getUserId() && culinaryRecipeId == fav.getCulinaryRecipeId()) {
                f = fav;
                break;
            }
        }

        return f;
    }

    public List<IFavoriteCulinaryRecipeModel> getFavoriteCulinaryRecipesByUserId(int userId) {
        List<IFavoriteCulinaryRecipeModel> f = null;

        for(IFavoriteCulinaryRecipeModel fav : this.favoriteCulinaryRecipes) {
            if (userId == fav.getUserId()) {
                f.add(fav);
            }
        }

        return f;
    }

    public void addFavoriteCulinaryRecipe(int userId, int culinaryRecipeId, ICulinaryRecipePresenter presenter) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("culinaryRecipeId", culinaryRecipeId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "http://10.0.0.193:3000/favoriteCulinaryRecipes", obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "Success on add favorite culinary recipe! Response: " + response.toString(4));
                            favoriteCulinaryRecipes.add(new FavoriteCulinaryRecipeModel(
                                    response.getInt("id"),
                                    response.getInt("userId"),
                                    response.getInt("culinaryRecipeId")
                            ));

                            presenter.onResultFavoriteCulinaryRecipe(new RequestHelper(true, RequestType.OK));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error on add favorite culinary recipe! Returned message: " + e.getMessage());
                            e.printStackTrace();

                            presenter.onResultFavoriteCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, e.getMessage()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error on add favorite culinary recipe! Returned message: " + error.getMessage());
                error.printStackTrace();

                presenter.onResultFavoriteCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, error.getMessage()));
            }
        });

        this.queue.add(req);
    }

    private void removeFavoriteCulinaryRecipeById(int favoriteCulinaryRecipeId) {
        List<IFavoriteCulinaryRecipeModel> f = this.favoriteCulinaryRecipes;

        for(IFavoriteCulinaryRecipeModel fav : f) {
            if (favoriteCulinaryRecipeId == fav.getFavoriteCulinaryRecipeId()) {
                f.remove(fav);
                break;
            }
        }

        this.favoriteCulinaryRecipes = f;
    }

    public void deleteFavoriteCulinaryRecipe(int favoriteCulinaryRecipeId, ICulinaryRecipePresenter presenter) {
        StringRequest dr = new StringRequest(Request.Method.DELETE, "http://10.0.0.193:3000/favoriteCulinaryRecipes/" + favoriteCulinaryRecipeId,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "Success on remove favorite culinary recipe! Response: " + response);
                            removeFavoriteCulinaryRecipeById(favoriteCulinaryRecipeId);

                            presenter.onResultFavoriteCulinaryRecipe(new RequestHelper(true, RequestType.OK));
                        } catch (Exception e) {
                            Log.e(TAG, "Error on remove favorite culinary recipe! Returned message: " + e.getMessage());
                            e.printStackTrace();

                            presenter.onResultFavoriteCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, e.getMessage()));
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on remove favorite culinary recipe! Returned message: " + error.getMessage());
                        error.printStackTrace();

                        presenter.onResultFavoriteCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, error.getMessage()));
                    }
                }
        );

        this.queue.add(dr);
    }
}
