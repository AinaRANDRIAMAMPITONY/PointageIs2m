package com.test.pointageis2m.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.test.pointageis2m.Model.Pointage;
import com.test.pointageis2m.Service.Callback.PostPointageCallback;
import com.test.pointageis2m.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class PointageAPIService {
    Context context;
    PointageService pointageService;
    String url = "https://backend--projet--aina.herokuapp.com";

    public PointageAPIService(Context context){
        this.context = context;
        pointageService = new PointageService(context);
    }

    public void synchronize(PostPointageCallback callback) {
        List<Pointage> pointageList = pointageService.getAllSavedPointages();
        JSONArray body = new JSONArray();

        Gson gson = new Gson();

        String listString = gson.toJson(
                pointageList,
                new TypeToken<ArrayList<Pointage>>() {}.getType());

        try {
            body =  new JSONArray(listString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest sync = new JsonArrayRequest(
                Request.Method.POST,
                url + "/pointages",
                body,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.getResponse(response);
                        Log.i("api", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.getError(error);
                        try{
                            Log.i("api", error.toString());
                        }
                        catch (Exception e){
                            Log.i("api", e.getMessage());
                        }

                    }
                }
        );
        VolleySingleton.getInstance(context).getRequestQueue().add(sync);
    }
}
