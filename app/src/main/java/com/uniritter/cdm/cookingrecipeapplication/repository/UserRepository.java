package com.uniritter.cdm.cookingrecipeapplication.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response.Listener;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestType;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;
import com.uniritter.cdm.cookingrecipeapplication.model.UserModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.IUserPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRepository {
    private static String TAG = "UserRepository";
    private IUserModel user;
    private List<IUserModel> users;
    private Context context;
    private static UserRepository instance;
    private RequestQueue queue;

    private UserRepository(Context context) {
        super();
        this.context = context;
        this.users = new ArrayList<>();
        this.queue = Volley.newRequestQueue(context);
        getAllUsers();
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public void getAllUsers() {
        JsonArrayRequest jaRequest = new JsonArrayRequest(Request.Method.GET,
                "http://10.0.0.193:3000/users",
                null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                users.add(new UserModel(
                                        json.getInt("id"),
                                        json.getString("name"),
                                        json.getString("email"),
                                        json.getString("password")
                                ));
                            } catch (JSONException e) {
                                Log.e(TAG, "Error on get users! Returned message: " + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on get users! Returned message: " + error.getMessage());
                    }
                });

        this.queue.add(jaRequest);
    }

    public IUserModel getUserById(int userId) {
        IUserModel u = null;

        for(IUserModel us : this.users) {
            if (userId == us.getUserId()) {
                u = us;
                break;
            }
        }

        return u;
    }

    public IUserModel getUserByEmail(String userEmail) {
        IUserModel u = null;

        for(IUserModel us : this.users) {
            if (userEmail.equals(us.getUserEmail())) {
                u = us;
                break;
            }
        }

        return u;
    }

    public RequestHelper validateUserCredentials(String userEmail, String userPassword) {
        if (userEmail == null || TextUtils.isEmpty(userEmail) || userPassword == null || TextUtils.isEmpty(userPassword))
            return new RequestHelper(true, RequestType.NotAcceptable, "Preencha todos os campos.");

        IUserModel u = this.getUserByEmail(userEmail);
        if (u == null)
            return new RequestHelper(true, RequestType.NotFound, "O usu??rio n??o foi encontrado.");

        if (userPassword.equals(u.getUserPassword())) {
            this.user = u;
            return new RequestHelper(true, RequestType.OK);
        }

        return new RequestHelper(false, RequestType.BadRequest, "A senha est?? incorreta.");
    }

    public void addUser(String userName, String userEmail, String userPassword, String userPasswordConfirm, IUserPresenter presenter) {
        if (userName == null || TextUtils.isEmpty(userName) || userEmail == null || TextUtils.isEmpty(userEmail) ||
                userPassword == null || TextUtils.isEmpty(userPassword) || userPasswordConfirm == null || TextUtils.isEmpty(userPasswordConfirm)) {
            presenter.onResult(new RequestHelper(true, RequestType.NotAcceptable, "Preencha todos os campos."));
        } else {
            if (userPassword != userPasswordConfirm) {
                presenter.onResult(new RequestHelper(true, RequestType.Conflict, "As senhas s??o diferentes."));
            } else {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", userName);
                params.put("email", userEmail);
                params.put("password", userPassword);

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "http://10.0.0.193:3000/users", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d(TAG, "Success on add user! Response: " + response.toString(4));
                                    users.add(new UserModel(
                                            response.getInt("id"),
                                            response.getString("name"),
                                            response.getString("email"),
                                            response.getString("password")
                                    ));

                                    presenter.onResult(new RequestHelper(true, RequestType.OK));
                                } catch (JSONException e) {
                                    Log.e(TAG, "Error on add user! Returned message: " + e.getMessage());
                                    e.printStackTrace();

                                    presenter.onResult(new RequestHelper(false, RequestType.BadRequest, e.getMessage()));
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error on add user! Returned message: " + error.getMessage());
                        error.printStackTrace();

                        presenter.onResult(new RequestHelper(false, RequestType.BadRequest, error.getMessage()));
                    }
                });

                this.queue.add(req);
            }
        }
    }
}
