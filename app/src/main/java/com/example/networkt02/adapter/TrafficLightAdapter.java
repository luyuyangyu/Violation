package com.example.networkt02.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.networkt02.R;
import com.example.networkt02.beans.TrafficLightBean;

/**
 * Created by SongZhihao on 2019/9/11.
 */
public class TrafficLightAdapter extends BaseAdapter {

	// 存放网络请求后获取的数据
	private List<TrafficLightBean> objects = new ArrayList<>();

	private Context mContext;
	private LayoutInflater layoutInflater;

	// 设置一个监听的接口
	public interface OnsettingListener {
		void OnSetting(View view, int pos);
	}

	// 创建监听接口对象
	private OnsettingListener onsettingListener;

	private List<TrafficLightBean> selectList = new ArrayList<>();

	public List<TrafficLightBean> getSelectList() {
		return selectList;
	}

	// 构造函数，在碎片中，创建adapter对象时，需要传入context对象，和list（带红绿灯数据的）数组
	public TrafficLightAdapter(Context context, List<TrafficLightBean> objects) {
		this.objects = objects;
		this.mContext = context;
		this.layoutInflater = LayoutInflater.from(context);
	}


	public List<TrafficLightBean> getObjects() {
		return objects;
	}

	public void setObjects(List<TrafficLightBean> objects) {
		this.objects = objects;
	}

	public void setOnsettingListener(OnsettingListener onsettingListener) {
		this.onsettingListener = onsettingListener;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.traffic_light_list_item, null);
			convertView.setTag(new ViewHolder(convertView));
		}
		initializeViews((TrafficLightBean) getItem(position), (ViewHolder) convertView.getTag(), position);
		return convertView;
	}

	// 设置数据，并设置监听事件
	private void initializeViews(final TrafficLightBean object, ViewHolder holder, final int position) {

		// 将网络请求获取到的数据，放到ui里
		holder.tvTrafficId.setText(object.getId() + "");
		holder.tvTrafficRed.setText(object.getRed_light() + "");
		holder.tvTrafficGreen.setText(object.getGreen_light() + "");
		holder.tvTrafficYellow.setText(object.getYellow_light() + "");

		// 复选框的监听事件
		holder.cbTrafficSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 将该对象添加到已选择列表中
					selectList.add(object);
				} else {
					selectList.remove(object);
				}
			}
		});

		// button 的监听事件
		holder.btnTrafficSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onsettingListener.OnSetting(v, position);
			}
		});

	}

	protected class ViewHolder {
		private TextView tvTrafficId;
		private TextView tvTrafficRed;
		private TextView tvTrafficGreen;
		private TextView tvTrafficYellow;
		private CheckBox cbTrafficSelect;
		private Button btnTrafficSet;

		public ViewHolder(View view) {
			tvTrafficId = view.findViewById(R.id.tv_traffic_id);
			tvTrafficRed = view.findViewById(R.id.tv_traffic_red);
			tvTrafficGreen = view.findViewById(R.id.tv_traffic_green);
			tvTrafficYellow = view.findViewById(R.id.tv_traffic_yellow);
			cbTrafficSelect = view.findViewById(R.id.cb_traffic_select);
			btnTrafficSet = view.findViewById(R.id.btn_traffic_set);
		}
	}
}
