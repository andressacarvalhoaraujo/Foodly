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
import com.uniritter.cdm.cookingrecipeapplication.model.INextCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.NextCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.ICulinaryRecipePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NextCulinaryRecipeRepository {
    private static String TAG = "NextCulinaryRecRepo";
    private INextCulinaryRecipeModel nextCulinaryRecipe;
    private List<INextCulinaryRecipeModel> nextCulinaryRecipes;
    private Context context;
    private static NextCulinaryRecipeRepository instance;
    private RequestQueue queue;

    private NextCulinaryRecipeRepository(Context context) {
        super();
        this.context = context;
        this.nextCulinaryRecipes = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        loadAllNextCulinaryRecipes();
    }

    public static NextCulinaryRecipeRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new NextCulinaryRecipeRepository(context);
        }
        return instance;
    }

    public void loadAllNextCulinaryRecipes() {
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "http://10.0.0.193:3000/nextCulinaryRecipes",
                null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                nextCulinaryRecipes.add(new NextCulinaryRecipeModel(
                                        json.getInt("id"),
                                        json.getInt("userId"),
                                        json.getInt("culinaryRecipeId")
                                ));
                            } catch (JSONException e) {
                                Log.e(TAG, "Error on get next culinary recipes! Returned message: " + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on get next culinary recipes! Returned message: " + error.getMessage());
                    }
                });

        this.queue.add(jaRequest);
    }

    public INextCulinaryRecipeModel getNextCulinaryRecipeByUserIdAndCulinaryRecipeId(int userId, int culinaryRecipeId) {
        INextCulinaryRecipeModel n = null;

        for(INextCulinaryRecipeModel next : this.nextCulinaryRecipes) {
            if (userId == next.getUserId() && culinaryRecipeId == next.getCulinaryRecipeId()) {
                n = next;
                break;
            }
        }

        return n;
    }

    public List<INextCulinaryRecipeModel> getNextCulinaryRecipesByUserId(int userId) {
        List<INextCulinaryRecipeModel> n = null;

        for(INextCulinaryRecipeModel next : this.nextCulinaryRecipes) {
            if (userId == next.getUserId()) {
                n.add(next);
            }
        }

        return n;
    }

    public void addNextCulinaryRecipe(int userId, int culinaryRecipeId, ICulinaryRecipePresenter presenter) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("culinaryRecipeId", culinaryRecipeId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "http://10.0.0.193:3000/nextCulinaryRecipes", obj,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "Success on add next culinary recipe! Response: " + response.toString(4));
                            nextCulinaryRecipes.add(new NextCulinaryRecipeModel(
                                    response.getInt("id"),
                                    response.getInt("userId"),
                                    response.getInt("culinaryRecipeId")
                            ));

                            presenter.onResultNextCulinaryRecipe(new RequestHelper(true, RequestType.OK));
                        } catch (JSONException e) {
                            Log.e(TAG, "Error on add next culinary recipe! Returned message: " + e.getMessage());
                            e.printStackTrace();

                            presenter.onResultNextCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, e.getMessage()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error on add next culinary recipe! Returned message: " + error.getMessage());
                error.printStackTrace();

                presenter.onResultNextCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, error.getMessage()));
            }
        });

        this.queue.add(req);
    }

    private void removeNextCulinaryRecipeById(int nextCulinaryRecipeId) {
        List<INextCulinaryRecipeModel> n = this.nextCulinaryRecipes;

        for(INextCulinaryRecipeModel next : n) {
            if (nextCulinaryRecipeId == next.getNextCulinaryRecipeId()) {
                n.remove(next);
                break;
            }
        }

        this.nextCulinaryRecipes = n;
    }

    public void deleteNextCulinaryRecipe(int nextCulinaryRecipeId, ICulinaryRecipePresenter presenter) {
        StringRequest dr = new StringRequest(Request.Method.DELETE, "http://10.0.0.193:3000/nextCulinaryRecipes/" + nextCulinaryRecipeId,
                new Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "Success on remove next culinary recipe! Response: " + response);
                            removeNextCulinaryRecipeById(nextCulinaryRecipeId);

                            presenter.onResultNextCulinaryRecipe(new RequestHelper(true, RequestType.OK));
                        } catch (Exception e) {
                            Log.e(TAG, "Error on remove next culinary recipe! Returned message: " + e.getMessage());
                            e.printStackTrace();

                            presenter.onResultNextCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, e.getMessage()));
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on remove next culinary recipe! Returned message: " + error.getMessage());
                        error.printStackTrace();

                        presenter.onResultNextCulinaryRecipe(new RequestHelper(false, RequestType.BadRequest, error.getMessage()));
                    }
                }
        );

        this.queue.add(dr);
    }
}
