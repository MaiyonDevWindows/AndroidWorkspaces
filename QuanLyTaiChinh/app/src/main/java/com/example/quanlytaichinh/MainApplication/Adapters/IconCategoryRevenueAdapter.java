package com.example.quanlytaichinh.MainApplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.quanlytaichinh.MainApplication.Data.IconRevenueCategory.IconRevenue;
import com.example.quanlytaichinh.R;

import java.util.List;

public class IconCategoryRevenueAdapter extends BaseAdapter {
    private Context context;
    private List<IconRevenue> listIcon;

    public IconCategoryRevenueAdapter(Context context, List<IconRevenue> listIcon) {
        this.context = context;
        this.listIcon = listIcon;
    }

    @Override
    public int getCount() {
        return listIcon!= null ? listIcon.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_container_icon_category_revenue, viewGroup, false);
        ImageView  img_icon_category = rootView.findViewById(R.id.img_icon_category);
        TextView title_icon_category = rootView.findViewById(R.id.title_icon_category);
        img_icon_category.setImageResource(listIcon.get(i).getSrcIcon());
        title_icon_category.setText(listIcon.get(i).getTitleIcon());
        return rootView;
    }
}
