package com.example.networkt02.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.networkt02.R;
import com.example.networkt02.adapter.TrafficLightAdapter;
import com.example.networkt02.beans.TrafficLightBean;
import com.example.networkt02.inter.NetworkOnResult;
import com.example.networkt02.network.NetRequest;
import com.example.networkt02.util.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by SongZhihao on 2019/9/10.
 */
public class TrafficLightFragment extends Fragment {

	private static final String TAG = "TrafficLightFragment";
	private Context mContext;
	private Spinner mSpinner;
	private ListView mListView;
	private Button mBtnSearch, mBtnSet;

	private String SortArr[] = {
			"路口升序", "路口降序",
			"红灯升序", "红灯降序",
			"绿灯升序", "绿灯降序",
			"黄灯升序", "黄灯降序"
	};

	private List<TrafficLightBean> mLightBeans = new ArrayList<>();

	private TrafficLightAdapter mLightAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	/**
	 * 返回加载界面
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_traffic_light, container, false);
	}

	/**
	 * 加载控件
	 *
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initEvent();
		initData();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	/**
	 * 初始化控件，设置加载适配器
	 */
	private void initView() {
		mSpinner = getView().findViewById(R.id.sp_select_sort);
		mBtnSearch = getView().findViewById(R.id.btn_search_traffic_light);
		mBtnSet = getView().findViewById(R.id.btn_volume_set);
		mListView = getView().findViewById(R.id.lv_traffic_light);
		mSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, SortArr));
		mLightAdapter = new TrafficLightAdapter(getActivity(), mLightBeans);
		mListView.setAdapter(mLightAdapter);
	}


	/**
	 * 初始化红绿灯列表
	 */
	private void initData() {
		//发送网络请求，获取json
		new NetRequest("TrafficLightSearch")  // action：请求网址头
				.setNetWorkOnResult(new NetworkOnResult() {  // 设置接口监听
					@Override
					public void onSuccess(JSONObject jsonObject) {  // 请求成功
						try {
							Log.d(TAG, "onSuccess的json：" + jsonObject);
							if ("200".equals(jsonObject.getString("resCode"))) {
								JSONArray jsonArray = jsonObject.getJSONArray("list");
								Log.d(TAG, "jsonArray：" + jsonArray);
								mLightBeans = GsonUtil.gsonTOList(jsonArray.toString(), TrafficLightBean.class);

								Log.i(TAG, "LightBeans的值为 " + mLightBeans);
								mLightAdapter.setObjects(mLightBeans);
								mLightAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError() {

					}
				});
	}

	/**
	 * 选项排序，按钮单击事件
	 */
	private void initEvent() {

		// 下拉选择框的监听事件
		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// 设置排序方式Id
				TrafficLightBean.select = position;
				// java 知识，使用集合排序
				Collections.sort(mLightBeans);
				// 将排序后的结果传入到适配器
				mLightAdapter.setObjects(mLightBeans);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// 调用适配器的监听事件接口，实现ListView中的button功能
		mLightAdapter.setOnsettingListener(new TrafficLightAdapter.OnsettingListener() {
			@Override
			public void OnSetting(View view, int pos) {
				ArrayList<Integer> arr = new ArrayList<>();
				// 将 选中行 的 position 位置 存入 集合
				arr.add(pos + 1);
				// 打开设置时长  对话框，进行设置处理
				showSettingLightDialog(arr);
			}
		});

		// 查询按钮，用来更新排序操作
		mBtnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 刷新ListView的固定写法
				mLightAdapter.notifyDataSetChanged();
			}
		});

		// 批量设置按钮
		mBtnSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 新建集合 用来保存 要设置路口 的id
				ArrayList<Integer> arr = new ArrayList<Integer>();
				// 通过调用 adapter 中的 getSelectList()，获取需要修改的路口集合
				List<TrafficLightBean> list = mLightAdapter.getSelectList();
				for (int i = 0; i < list.size(); i++) {
					TrafficLightBean lightBean = list.get(i);
					arr.add(lightBean.getId());
				}
				// 打开设置时长  对话框，进行设置处理
				showSettingLightDialog(arr); //显示设置对话框
			}
		});
	}

	/**
	 * 设置时长对话框
	 */

	private void showSettingLightDialog(final ArrayList<Integer> arr) {
		// 创建一个自定义View对话框的固定写法
		final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
		alertDialog.setTitle("红绿灯设置");
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.traffic_light_dialog, null);
		// 初始化自定义对话框中的控件
		final EditText etRedLight = view.findViewById(R.id.et_set_red_light);
		final EditText etGreenLight = view.findViewById(R.id.et_set_green_light);
		final EditText etYellowLight = view.findViewById(R.id.et_set_yellow_light);
		Button btnDetermine = view.findViewById(R.id.btn_queding);
		Button btnCancel = view.findViewById(R.id.btn_quxiao);

		// 取消按钮监听事件
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});
		// 设置按钮
		btnDetermine.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String redLight = etRedLight.getText().toString().trim();
				String greenLight = etGreenLight.getText().toString().trim();
				String yellowLight = etYellowLight.getText().toString().trim();

				final JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("red_light", redLight);
					jsonObj.put("green_light", greenLight);
					jsonObj.put("yellow_light", yellowLight);
					for (int i = 0; i < arr.size(); i++) {
						int id = arr.get(i);
						jsonObj.put("id" + i, "" + id);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.d(TAG, "发送的json：" + jsonObj);
				new NetRequest("TrafficLightSet")
						.setJsonBody(jsonObj)
						.setNetWorkOnResult(new NetworkOnResult() {
							@Override
							public void onSuccess(JSONObject jsonObject) throws JSONException {
								if ("200".equals(jsonObject.getString("resCode"))) {
									Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
									alertDialog.dismiss();
									initData();
								} else {
									Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
								}
							}

							@Override
							public void onError() {

							}
						});
			}
		});
		alertDialog.setView(view);
		alertDialog.show();
	}

}
