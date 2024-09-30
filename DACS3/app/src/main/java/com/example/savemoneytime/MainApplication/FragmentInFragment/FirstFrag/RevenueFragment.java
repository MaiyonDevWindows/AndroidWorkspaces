package com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.savemoneytime.MainApplication.Models.CategoryRevenueModel;
import com.example.savemoneytime.utils.IClickCategoryRevenue;
import com.example.savemoneytime.MainApplication.Adapters.CategoryRevenueAdapter;
import com.example.savemoneytime.MainApplication.Adapters.IconCategoryRevenueAdapter;
import com.example.savemoneytime.MainApplication.Data.IconRevenueCategory.DataRevenueIcon;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.configs.databases.RevenueDatabase;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class RevenueFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TextView viewCalender,alert_error;
    private Spinner spinner_category;
    private EditText edt_title_category;
    private CategoryRevenueAdapter categoryAdapter;
    int idCateSelected=0;
    private RecyclerView rcvCategory;
    private SwipeRefreshLayout mSrlLayout;
    private Calendar cld_choose;
    int i = 0;
    View myView1;

    private final int[]imageCategory = {R.drawable.handsome, R.drawable.team, R.drawable.dollar,
            R.drawable.coding,R.drawable.lotus,R.drawable.father,
            R.drawable.salary,R.drawable.mother
    };
    public RevenueFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView1 =inflater.inflate(R.layout.fragment_revenue, container, false);
        if(i==0){
            GetCurrentDate(i);
            cld_choose = GetDateChoose(i);
        }
        //  * Các thứ linh tinh để làm ngày giờ
        ImageButton nextDate = myView1.findViewById(R.id.bt_next_day);
        ImageButton previousDate = myView1.findViewById(R.id.bt_previous_day);
        nextDate.setOnClickListener(v -> {
            i++;
            cld_choose = GetDateChoose(i);
            GetCurrentDate(i);
            if(i==0){
                viewCalender.setBackgroundResource(R.color.background);
            }else{
                viewCalender.setBackgroundResource(R.color.purple_200);
            }
        });
        previousDate.setOnClickListener(v -> {
            i--;
            cld_choose = GetDateChoose(i);
            GetCurrentDate(i);
            if(i==0){
                viewCalender.setBackgroundResource(R.color.background);
            }else{
                viewCalender.setBackgroundResource(R.color.teal_700);
            }
        });

        mSrlLayout= myView1.findViewById(R.id.swipe);
        mSrlLayout.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        mSrlLayout.setOnRefreshListener(this);
        rcvCategory = myView1.findViewById(R.id.flex_category);

        //nút bật dialog
        MaterialButton add_category = myView1.findViewById(R.id.add_category);
        add_category.setOnClickListener(view -> openDialog(Gravity.BOTTOM));

        clickShowThingItemCatelory();
//        add phần item category
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

//        thêm đừng viền cho các item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
        rcvCategory.addItemDecoration(itemDecoration);
        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.setAdapter(categoryAdapter);

        //  Aau khi chọn được danh mục add nó vô csdl action người dùng.
        ImageView save_action_user = myView1.findViewById(R.id.save_action_user);
        save_action_user.setOnClickListener(view -> {
            TextView Text_action = myView1.findViewById(R.id.Text_action);
            String ST_Text_action= Text_action.getText().toString();

            TextView value_text_payment = myView1.findViewById(R.id.value_text_payment);
            String ST_value_text_payment= value_text_payment.getText().toString();
            if(idCateSelected == 0 || ST_Text_action.isEmpty() || ST_value_text_payment.isEmpty()){
                Toast.makeText(getContext(),"Chưa đủ thông tin !",Toast.LENGTH_LONG).show();
            }else{
                //  Lưu vô CSDL.
                ActionUserRevenueModel actionUserModel = new ActionUserRevenueModel(ST_Text_action,cld_choose.getTime(),ST_value_text_payment,idCateSelected);
                RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().insertActionUser(actionUserModel);
                Toast.makeText(getContext(),"thêm thành công !",Toast.LENGTH_LONG).show();

                //sau khi thêm xong xoá dữ liệu các textview;
                Text_action.setText("");
            }

        });

        return myView1;
    }
    //  ? Function tính ngày giờ.
    @SuppressLint("SetTextI18n")
    public void GetCurrentDate(int index){
        viewCalender = myView1.findViewById(R.id.show_calender);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK );
        int mounthOfWeek = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        String weekDay = "";
        String weekMounth="";
        //  * Chức năng tính ngày trong tuần.
        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "Thứ hai";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "Thứ ba";
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "Thứ tư";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "Thứ năm";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "Thứ sáu";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "Thứ bảy";
        } else if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "Chủ nhật";
        }
        //  * Chức năng tính tháng.
        if (Calendar.JANUARY == mounthOfWeek) {
            weekMounth = "Tháng một";
        } else if (Calendar.FEBRUARY == mounthOfWeek) {
            weekMounth = "Tháng hai";
        } else if (Calendar.MARCH == mounthOfWeek) {
            weekMounth = "Tháng ba";
        } else if (Calendar.APRIL  == mounthOfWeek) {
            weekMounth = "Tháng tư";
        } else if (Calendar.MAY == mounthOfWeek) {
            weekMounth = "Tháng năm";
        } else if (Calendar.JUNE == mounthOfWeek) {
            weekMounth = "Tháng sáu";
        } else if (Calendar.JULY == mounthOfWeek) {
            weekMounth = "Tháng 7";
        } else if (Calendar.AUGUST == mounthOfWeek) {
            weekMounth = "Tháng tám";
        } else if (Calendar.SEPTEMBER == mounthOfWeek) {
            weekMounth = "Tháng chín";
        } else if (Calendar.OCTOBER == mounthOfWeek) {
            weekMounth = "Tháng mười";
        } else if (Calendar.NOVEMBER == mounthOfWeek) {
            weekMounth = "Tháng mười một";
        } else if (Calendar.DECEMBER == mounthOfWeek) {
            weekMounth = "Tháng mười hai";
        }
        viewCalender.setText(currentDay+"/ "+ weekMounth +"/ "+year+"( " +weekDay+" )");
    }

    public void clickShowThingItemCatelory(){
        //vừa, gọi để add  adapter vừa xử lý mỗi khi click vào mọtt imgview  của category thì hiển thị ra thông tin của category đó
        categoryAdapter = new CategoryRevenueAdapter(addListCategoryFromDatabase(), new IClickCategoryRevenue() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemCategory(CategoryRevenueModel categoryRevenueModel) {
                TextView tv_show_cate_it_click = myView1.findViewById(R.id.tv_show_cate_it_click);
                tv_show_cate_it_click.setText(onClickItem(categoryRevenueModel));
                ImageView img_cate_selected = myView1.findViewById(R.id.img_cate_selected);
                img_cate_selected.setImageResource(categoryRevenueModel.getSourceImgCategory());
                idCateSelected = categoryRevenueModel.getIdCategory();
            }
            private String onClickItem(CategoryRevenueModel categoryRevenueModel) {
                return categoryRevenueModel.getTitleCategory();
            }
        });
    }
    //hàm bật dialog
    public void openDialog(int gravity){
        final Dialog dialog= new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_category_revenue);

        Window window = dialog.getWindow();
        if(window==null){
            return;
        }else{
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes= window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);
            dialog.setCancelable(Gravity.BOTTOM == gravity);
            //  * Xử lý sự kiện nút Huỷ
            MaterialButton bt_cancel_dialog_add_category = dialog.findViewById(R.id.bt_cancel_dialog_add_category);
            bt_cancel_dialog_add_category.setOnClickListener(view -> dialog.dismiss());
            //  Dùng spinnner để add các item-view vào spinner, trong các item đó chứa cả ảnh và text
            //  Text view, vì vậy sẽ set id cho mỗi IconModel để sau này sử dụng dễ dàng
            spinner_category = dialog.findViewById(R.id.dropdown_list_cate);
            IconCategoryRevenueAdapter iconCategoryRevenueAdapter = new IconCategoryRevenueAdapter(getContext(), DataRevenueIcon.getIconlist());
            spinner_category.setAdapter(iconCategoryRevenueAdapter);
            //  Xử lý sự kiện nút lưu danh mục
            MaterialButton bt_save_category = dialog.findViewById(R.id.save_category);
            alert_error=dialog.findViewById(R.id.alert_error);
            edt_title_category=dialog.findViewById(R.id.edt_title_category);
            //  * Nút save category
            bt_save_category.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    // lấy sử liệu từ các edit text để lưu vào database,
                    // lưu thành công hiển thị thông báo thành công, và tắt dialog
                    if(edt_title_category.getText().toString().isEmpty()){
                        alert_error.setText("Chưa nhập tên danh mục");
                    }else{
                        String edt_value_title_category= edt_title_category.getText().toString().trim();
                        int indexValueImgCategory = spinner_category.getSelectedItemPosition();
                        alert_error.setText("");
                        //lưu vào cơ sở dữ liệu
                        int valueImgCate= imageCategory[indexValueImgCategory];
                        CategoryRevenueModel categoryModel = new CategoryRevenueModel(valueImgCate, edt_value_title_category);
                        RevenueDatabase.getInstance(getContext()).categoryRevenueDAO().insertCategoryRevenue(categoryModel);
                        dialog.dismiss();
                        Toast.makeText(
                                getContext(),
                                "Thêm danh mục thành công, RELOAD lại để xuất hiện danh mục!",
                                Toast.LENGTH_LONG
                        ).show();
//                        clickShowThingItemCatelory();
                    }
                }
            });
            //xử lý với spinner(drop list) khi thg này adapter 1 item view, thì giá trị nó khi lấy ra sẽ ntn
        }
        dialog.show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            setupAdapter();
            mSrlLayout.setRefreshing(false);
        }, 2500);
    }

    //hàm này xử lý việc reload gọi lại adapter từ đầu để reload lại data
    private void setupAdapter() {
        rcvCategory = myView1.findViewById(R.id.flex_category);
        categoryAdapter = new CategoryRevenueAdapter(addListCategoryFromDatabase(), categoryRevenueModel -> {
            TextView tv_show_cate_it_click = myView1.findViewById(R.id.tv_show_cate_it_click);
            tv_show_cate_it_click.setText(onClickItem(categoryRevenueModel));
            ImageView img_cate_selected = myView1.findViewById(R.id.img_cate_selected);
            img_cate_selected.setImageResource(categoryRevenueModel.getSourceImgCategory());
            idCateSelected = categoryRevenueModel.getIdCategory();
        });

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.setAdapter(categoryAdapter);

    }
    public List<CategoryRevenueModel> addListCategoryFromDatabase(){
        // móc dữ liệu từ databasse Sqlite vào đây
        //lấy dử liệu từ bảng category vào đây
        return RevenueDatabase.getInstance(getContext()).categoryRevenueDAO().getListCategoryRevenue();
    }
    private String onClickItem(CategoryRevenueModel categoryModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return categoryModel.getTitleCategory();
    }
    public Calendar GetDateChoose(int index){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        return calendar;
    }
}