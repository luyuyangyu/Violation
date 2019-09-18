package com.example.networkt02.inter;

import org.json.JSONException;
import org.json.JSONObject;

public interface NetworkOnResult {
    void onSuccess(JSONObject jsonObject) throws JSONException;

    void onError();
}
