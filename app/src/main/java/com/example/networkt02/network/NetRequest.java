package com.example.networkt02.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.networkt02.app.AppClient;
import com.example.networkt02.app.AppConfig;
import com.example.networkt02.inter.NetworkOnResult;

import org.json.JSONException;
import org.json.JSONObject;

public class NetRequest extends Thread {
    private String TAG = "NetRequest";
    private JSONObject jsonBody;
    private String action;
    private NetworkOnResult networkOnResult;
    private boolean isLoop = false;
    private long loopTime = 1000;
    private ProgressDialog mProDialog;


    /**
     * 是否显示网络请求对话框
     *
     * @param context
     * @return
     */
    public NetRequest showDialog(Context context) {
        mProDialog = new ProgressDialog(context);
        mProDialog.show();
        return this;
    }


    /**
     * 关闭请求进度对话框
     */
    public void cancelDialog() {
        if (mProDialog != null) {
            mProDialog.cancel();
        }
    }

    public NetRequest(String action) {
        this.action = action;
    }

    public NetRequest setJsonBody(JSONObject jsonBody) {
        this.jsonBody = jsonBody;
        return this;
    }

    public NetRequest setNetWorkOnResult(NetworkOnResult netWorkOnResult) {
        this.networkOnResult = netWorkOnResult;
        start();
        return this;
    }

    public NetRequest setLoop(boolean loop) {
        isLoop = loop;
        return this;
    }

    public NetRequest setLoopTime(long loopTime) {
        this.loopTime = loopTime;
        return this;
    }

    public void request() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL + action, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            networkOnResult.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                networkOnResult.onError();
            }
        });
        AppClient.getRequestmQueue().add(jsonObjectRequest);

    }

    @Override
    public void run() {
        super.run();
        do {
            Log.i(TAG, "run()运行了！");
            request();
            try {
                Thread.sleep(loopTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (isLoop);
        cancelDialog();
    }

    public void clean() {
        isLoop = false;
    }
}
