package com.example.networkt02.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.networkt02.R;
import com.example.networkt02.activity.QueryResultsActivity;
import com.example.networkt02.inter.NetworkOnResult;
import com.example.networkt02.network.NetRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class IllegalFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private static final String TAG = "IllegalFragment";


    private EditText mEtWeizhang;
    private Button mBtnFindWeiZhang;


    public IllegalFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_illegal, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findInfo();
    }

    private void initView() {
        mEtWeizhang = getView().findViewById(R.id.et_weizhang);
        mBtnFindWeiZhang = getView().findViewById(R.id.btn_find_wei_zhang);
    }

    private void findInfo() {

        mBtnFindWeiZhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String weizhangText = mEtWeizhang.getText().toString().trim();
                if (TextUtils.isEmpty(weizhangText)) {
                    return;
                }
                initText(weizhangText);
            }
        });
    }

    //预先判断能否查询到数据如果可以则携带数据跳转
    private void initText(final String infoText) {
        final JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("car_number", infoText);
            new NetRequest("WeizhangSearch")
                    .setJsonBody(jsonObj)
                    .setNetWorkOnResult(new NetworkOnResult() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Log.i(TAG, "onSuccess: " + jsonObject);
                            try {
                                if ("200".equals(jsonObject.getString("resCode"))) {
                                    Intent intent = new Intent(getActivity(), QueryResultsActivity.class);
                                    intent.putExtra("findInfo", infoText);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "没有查询到" + "鲁" + infoText +
                                            "车的违章数据！", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
