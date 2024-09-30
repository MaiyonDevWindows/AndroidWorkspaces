package com.example.savemoneytime.MainApplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.savemoneytime.MainApplication.Models.CategoryRevenueModel;
import com.example.savemoneytime.utils.IClickCategoryRevenue;
import com.example.savemoneytime.R;

import java.util.List;

public class CategoryRevenueAdapter extends RecyclerView.Adapter<CategoryRevenueAdapter.CategoryViewHolder>{
    private final List<CategoryRevenueModel> mCategory;
    private final IClickCategoryRevenue iClickCategory;
    public CategoryRevenueAdapter(List<CategoryRevenueModel> mCategory, IClickCategoryRevenue iClickCategory ) {
        this.mCategory = mCategory;
        this.iClickCategory = iClickCategory;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_category, parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryRevenueModel categoryModel = mCategory.get(position);
        if(categoryModel == null){
            return;
        }
        holder.imgCategory.setImageResource(categoryModel.getSourceImgCategory());
        holder.titleCategory.setText(categoryModel.getTitleCategory());
        holder.lo_contain_ct.setOnClickListener(view -> iClickCategory.onClickItemCategory(categoryModel));
    }


    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgCategory;
        private final TextView titleCategory;
        private final LinearLayout lo_contain_ct;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            titleCategory = itemView.findViewById(R.id.title_category);
            lo_contain_ct = itemView.findViewById(R.id.lo_contain_ct);
        }
    }
}
