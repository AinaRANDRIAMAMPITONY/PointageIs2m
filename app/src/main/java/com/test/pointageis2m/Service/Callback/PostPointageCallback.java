package com.test.pointageis2m.Service.Callback;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface PostPointageCallback {
    void getResponse(JSONArray response);
    void getError(VolleyError error);
}
