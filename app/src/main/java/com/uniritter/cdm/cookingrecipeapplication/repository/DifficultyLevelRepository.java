package com.uniritter.cdm.cookingrecipeapplication.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.uniritter.cdm.cookingrecipeapplication.model.DifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DifficultyLevelRepository {
    private static String TAG = "DifficultyLevelRepo";
    private IDifficultyLevelModel difficultyLevel;
    private List<IDifficultyLevelModel> difficultyLevels;
    private Context context;
    private static DifficultyLevelRepository instance;
    private RequestQueue queue;

    private DifficultyLevelRepository(Context context) {
        super();
        this.context = context;
        this.difficultyLevels = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        loadAllDifficultyLevels();
    }

    public static DifficultyLevelRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new DifficultyLevelRepository(context);
        }
        return instance;
    }

    public void loadAllDifficultyLevels() {
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "http://10.0.0.193:3000/difficultyLevels",
                null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                difficultyLevels.add(new DifficultyLevelModel(
                                        json.getInt("id"),
                                        json.getString("name")
                                ));
                            } catch (JSONException e) {
                                Log.e(TAG, "Error on get difficulty levels! Returned message: " + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on get difficulty levels! Returned message: " + error.getMessage());
                    }
                });

        this.queue.add(jaRequest);
    }

    public IDifficultyLevelModel getDifficultyLevelById(int difficultyLevelId) {
        IDifficultyLevelModel d = null;

        for(IDifficultyLevelModel dl : this.difficultyLevels) {
            if (difficultyLevelId == dl.getDifficultyLevelId()) {
                d = dl;
                break;
            }
        }

        return d;
    }

    public List<IDifficultyLevelModel> getAllDifficultyLevels() {
        return this.difficultyLevels;
    }
}
