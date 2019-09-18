package com.example.networkt02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.networkt02.R;
import com.example.networkt02.beans.FindInfoBean;

import java.util.ArrayList;
import java.util.List;

public class FindInfoItem4Adapter extends BaseAdapter {
    private final String TAG = "FindInfoItem4Adapter";
    private List<FindInfoBean> objects = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    /**
     * 对外提供访问接口，其他类可通过实现该OnRemoveClick抽象方法 实现删除违章消息的效果
     */
    public interface OnRemoveClickListener {
        void OnRemoveClick(int pos);
    }

    private OnRemoveClickListener onRemoveClickListener;

    public void setOnRemoveClickListener(OnRemoveClickListener onRemoveClickListener) {
        this.onRemoveClickListener = onRemoveClickListener;
    }

    public FindInfoItem4Adapter(List<FindInfoBean> objects, Context context) {
        this.objects = objects;
        this.layoutInflater = layoutInflater.from(context);
        this.context = context;
    }

    public void setObjects(List<FindInfoBean> objects) {
        this.objects = objects;
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
            convertView = layoutInflater.inflate(R.layout.find_layout_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        /**
         * 显示具体的违章信息
         */
        initViews((FindInfoBean) getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initViews(final FindInfoBean object, final ViewHolder holder, final int pos) {
        holder.tvCarId.setText(object.getCar_number() + "");
        holder.tvFakuan.setText("未处理违章" + object.getDispose_number() + "次");
        holder.tvWeichuliWeizhang.setText("扣" + object.getDeduction() + "分     罚款" + object.getAmerce() + "元");
        holder.ivRemoveItem.setOnClickListener(new View.OnClickListener() {
            /**
             * 清除违章信息
             * @param v
             */
            @Override
            public void onClick(View v) {
                //Log.d("Clean", "违章清除");
                onRemoveClickListener.OnRemoveClick(pos);
            }
        });
    }

    protected class ViewHolder {
        private TextView tvCarId;
        private TextView tvWeichuliWeizhang;
        private TextView tvFakuan;
        private ImageView ivRemoveItem;

        public ViewHolder(View view) {
            tvCarId = (TextView) view.findViewById(R.id.tv_car_id);
            tvWeichuliWeizhang = (TextView) view.findViewById(R.id.tv_weichuli_weizhang);
            tvFakuan = (TextView) view.findViewById(R.id.tv_fakuan);
            ivRemoveItem = (ImageView) view.findViewById(R.id.iv_remove_item);
        }
    }
}
