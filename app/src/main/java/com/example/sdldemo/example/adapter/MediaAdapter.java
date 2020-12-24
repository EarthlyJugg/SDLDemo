package com.example.sdldemo.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sdldemo.R;
import com.example.sdldemo.example.bean.MediaBean;

import java.util.List;

public class MediaAdapter extends BaseAdapter {

    List<MediaBean> list;
    private Context context;
    private LayoutInflater inflater;

    public MediaAdapter(Context context, List<MediaBean> list) {

        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodle viewHodle;
        if (view == null) {
            viewHodle = new ViewHodle();
            view = inflater.inflate(R.layout.item_media_layout, null);
            viewHodle.name = view.findViewById(R.id.name);
            viewHodle.path = view.findViewById(R.id.path);
            viewHodle.item = view.findViewById(R.id.list_item);
            view.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) view.getTag();
        }

        final MediaBean bean = list.get(i);
        viewHodle.name.setText(bean.name == null ? "name" : bean.name);
        viewHodle.path.setText(bean.data);
        viewHodle.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(i, bean);
            }
        });

        return view;
    }

    private class ViewHodle {

        private View item;
        private TextView name;
        private TextView path;

    }

    OnItemClickListener onClickListener;

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position, MediaBean bean);

    }

}
