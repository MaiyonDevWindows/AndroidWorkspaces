package com.example.savemoneytime.MainApplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.savemoneytime.MainApplication.Models.CategoryModel;
import com.example.savemoneytime.utils.IClickAction;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.configs.databases.SaveDatabase;

import java.util.List;

public class ListExpensesAdapter extends RecyclerView.Adapter<ListExpensesAdapter.ListExpensesViewHolder> {
    private final List<ActionUserModel> l_actionuser;
    private final IClickAction iclickAction;
    private Context context;

    public ListExpensesAdapter(List<ActionUserModel> l_actionuser, IClickAction iclickAction) {
        this.iclickAction=iclickAction;
        this.l_actionuser = l_actionuser;
    }

    @NonNull
    @Override
    public ListExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_list_expenses, parent,false);
        return new ListExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListExpensesViewHolder holder, int position) {
        ActionUserModel actionUserModel = l_actionuser.get(position);
        if(actionUserModel==null){
            return;
        }
         List<CategoryModel>l_category;
        //gọi lại từ thằng dababase để lấy ảnh ra
        l_category = SaveDatabase.getInstance(context).categoryDAO().getListByIdCategory(actionUserModel.getIdCategory());

        CategoryModel categoryModel = l_category.get(0);
        holder.imgAction.setImageResource(categoryModel.getSourceImgCategory());

        holder.titleExpenses.setText(actionUserModel.getTitleAction());
        holder.ExpensesText.setText(actionUserModel.getPaymentAction());
        holder.dateText.setText(String.valueOf(actionUserModel.getDateTimeAction()));
        holder.del_action_user.setOnClickListener(view -> iclickAction.onClickItemAction(actionUserModel));
    }

    @Override
    public int getItemCount() {
        return l_actionuser.size();
    }

    public static class ListExpensesViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgAction;
        private final TextView titleExpenses;
        private final TextView ExpensesText;
        private final TextView dateText;
        private final ImageButton del_action_user;
        public ListExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAction = itemView.findViewById(R.id.img_expenses);
            titleExpenses = itemView.findViewById(R.id.title_action);
            ExpensesText = itemView.findViewById(R.id.expenses_text);
            dateText = itemView.findViewById(R.id.date);
            del_action_user = itemView.findViewById(R.id.del_action_user);

        }
    }
}
