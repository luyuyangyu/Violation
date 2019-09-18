package com.example.networkt02.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.networkt02.R;
import com.example.networkt02.adapter.FindInfoItem4Adapter;
import com.example.networkt02.adapter.FindSelectInfoInfosAdapter;
import com.example.networkt02.app.AppClient;
import com.example.networkt02.beans.FaKuanInfoBean;
import com.example.networkt02.beans.FindInfoBean;
import com.example.networkt02.inter.NetworkOnResult;
import com.example.networkt02.network.NetRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.networkt02.app.AppClient.listInfos;

public class QueryResultsActivity extends AppCompatActivity implements FindInfoItem4Adapter.OnRemoveClickListener {
    private final String TAG = "QueryResultsActivity";
    private String infoText;
    private ImageView mIvAddbutton;
    private ListView mLvFindList;
    private ListView mLvFindListInfo;
    private FindInfoItem4Adapter findInfoItem4Adapter;
    private FindSelectInfoInfosAdapter findSelectInfoInfosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);
        getInfo();
        initView();
        initData();
        initText();
        initEvent();
    }

    private void log() {
        Log.i(TAG, "log: " + AppClient.listInfos.size());
        for (int i = 0; i < AppClient.listInfos.size(); i++) {
            Log.i(TAG, "log: " + AppClient.listInfos.get(i));
        }
    }

    private void initEvent() {
        mLvFindList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FaKuanInfoBean> infos = listInfos.get(position);
                findSelectInfoInfosAdapter.setObjects(infos);
                findSelectInfoInfosAdapter.notifyDataSetChanged();
            }
        });
        mLvFindListInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mIvAddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QueryResultsActivity.this, MainActivity.class));
                QueryResultsActivity.this.finish();

            }
        });
    }

    private void initList() {
        findInfoItem4Adapter = new FindInfoItem4Adapter(AppClient.lists, this);
        mLvFindList.setAdapter(findInfoItem4Adapter);
        findInfoItem4Adapter.setOnRemoveClickListener(this);
        findSelectInfoInfosAdapter = new FindSelectInfoInfosAdapter(AppClient.listInfos.get(0), this);
        mLvFindListInfo.setAdapter(findSelectInfoInfosAdapter);
    }


    private void initText() {
        final JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("car_number", infoText);
            new NetRequest("WeizhangSearch")
                    .showDialog(this)
                    .setJsonBody(jsonObj)
                    .setNetWorkOnResult(new NetworkOnResult() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {
                                if ("200".equals(jsonObject.getString("resCode"))) {
                                    Log.i(TAG, "onSuccess: jsonObj的值为" + jsonObj);

                                    JSONObject jsonObject2 = jsonObject.getJSONObject("map");
                                    //解析json得到实体类
                                    FindInfoBean findInfoBean = new Gson().fromJson(jsonObject2.toString(), FindInfoBean.class);
                                    AppClient.lists.add(findInfoBean);
                                    JSONArray jsonArr = jsonObject.getJSONArray("list");
                                    ArrayList<FaKuanInfoBean> info = new ArrayList<>();
                                    Log.i(TAG, "onSuccess111:info的值为 " + info);
                                    if (info == null) {
                                        AppClient.listInfos.add(info);
                                    } else {
                                        for (int i = 0; i < jsonArr.length(); i++) {
                                            JSONObject jsonObjects = jsonArr.getJSONObject(i);
                                            FaKuanInfoBean fakuanInfoBean = new Gson().fromJson(jsonObjects.toString(), FaKuanInfoBean.class);
                                            info.add(fakuanInfoBean);
                                            AppClient.setString("isInfoIf","Yes");
                                            System.out.println(info.size());

                                            Log.i(TAG, "onSuccess222: info添加之后的值为" + info);
                                        }
                                        for (Object obj:info
                                             ) {
                                            System.out.println(obj);

                                        }
                                        AppClient.listInfos.add(info);
                                    }
                                    log();
                                }
                                initList();
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

    /**
     * 初始化数据
     */
    private void initData() {
        AppClient.lists = new ArrayList<>();
        listInfos = new ArrayList<>();
    }

    /**
     * 获取跳转后的信息
     */
    public void getInfo() {
        Intent intent = getIntent();
        infoText = intent.getStringExtra("findInfo");
    }

    private void initView() {
        mIvAddbutton = (ImageView) findViewById(R.id.iv_addbutton);
        mLvFindList = (ListView) findViewById(R.id.lv_find_list);
        mLvFindListInfo = (ListView) findViewById(R.id.lv_find_list_info);
    }

    @Override
    public void OnRemoveClick(int pos) {
        AppClient.lists.remove(pos);
        listInfos.remove(pos);
        findInfoItem4Adapter.setObjects(AppClient.lists);
        findInfoItem4Adapter.notifyDataSetChanged();

    }
}
