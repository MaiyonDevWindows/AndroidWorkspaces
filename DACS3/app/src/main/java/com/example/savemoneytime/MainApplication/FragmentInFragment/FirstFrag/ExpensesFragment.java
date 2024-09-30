package com.example.savemoneytime.MainApplication.FragmentInFragment.FirstFrag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.example.savemoneytime.MainApplication.Models.CategoryModel;
import com.example.savemoneytime.utils.IClickCategory;
import com.example.savemoneytime.MainApplication.Adapters.CategoryAdapter;
import com.example.savemoneytime.MainApplication.Adapters.IconCategoryAdapter;
import com.example.savemoneytime.MainApplication.Data.IconCategory.DataIcon;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.configs.databases.RevenueDatabase;
import com.example.savemoneytime.configs.databases.SaveDatabase;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExpensesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private TextView viewCalender;
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private Spinner spinner_category;
    private EditText edt_title_category;
    private TextView alert_error;
    private SwipeRefreshLayout mSrlLayout;
    private Calendar cld_choose;
    private int idCateSelected=0;
    private List<ActionUserModel> L_actionUserModel;
    private List<ActionUserRevenueModel> L_actionUserRevenueModels;
    private int current_Month;

    private final int[] imageCategory = {R.drawable.icon_cate_bag,R.drawable.icon_cate_beauty,R.drawable.icon_cate_diet,
            R.drawable.icon_cate_hawaiian_shirt,R.drawable.icon_cate_house,R.drawable.icon_cate_nurses,
            R.drawable.icon_cate_studying,R.drawable.icon_cate_water
    };
    int i=0;
    View myView;

    public ExpensesFragment(){}
    private String onClickItem(CategoryModel categoryModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return categoryModel.getTitleCategory();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_expenses, container, false);
        // Tạo một channel cho thông báo (chỉ cần thực hiện một lần)
        CharSequence name = "channel_name";
        String description = "channel_description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
        channel.setDescription(description);
        // Đăng ký channel với hệ thống
        NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        if(i == 0){
            GetCurrentDate(i);
            cld_choose = GetDateChosse(i);
        }
        //các thứ linh tinh để làm ngày giờ
        ImageButton nextDate = myView.findViewById(R.id.bt_next_day);
        ImageButton previousDate = myView.findViewById(R.id.bt_previous_day);
        nextDate.setOnClickListener(v -> {
            i++;
            GetCurrentDate(i);
            cld_choose = GetDateChosse(i);
            if(i==0){
                viewCalender.setBackgroundResource(R.color.background);
            }else{
                viewCalender.setBackgroundResource(R.color.purple_200);
            }
        });
        previousDate.setOnClickListener(v -> {
            i--;
            GetCurrentDate(i);
            cld_choose = GetDateChosse(i);
            if(i==0){
                viewCalender.setBackgroundResource(R.color.background);
            }else{
                viewCalender.setBackgroundResource(R.color.teal_700);
            }
        });
        // reload data, kéo xuống để relaod
        mSrlLayout= myView.findViewById(R.id.swipe);
        mSrlLayout.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        mSrlLayout.setOnRefreshListener(this);
        rcvCategory = myView.findViewById(R.id.flex_category);
        clickShowThingItemCategory();
        //  Add phần item category
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);

        //thêm đừng viền cho các item
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
        rcvCategory.addItemDecoration(itemDecoration);
        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.setAdapter(categoryAdapter);

        //  Buttom thêm danh mục.
        MaterialButton add_category = myView.findViewById(R.id.add_category);
        add_category.setOnClickListener(view -> openDialog(Gravity.BOTTOM));
        //  Sau khi chọn được danh mục add nó vô CSDL action người dùng
        ImageView save_action_user = myView.findViewById(R.id.save_action_user);
        save_action_user.setOnClickListener(view -> {
            L_actionUserModel = SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
            L_actionUserRevenueModels = RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().getListAction();
            int sum_expenses = 0;
            for (int i = 0; i < L_actionUserModel.size(); i++) {
                Calendar cld_expenses = dateToCalendar(L_actionUserModel.get(i).getDateTimeAction());
                int get_month_db_expenses = cld_expenses.get(Calendar.MONTH);
                if (current_Month == get_month_db_expenses) {
                    sum_expenses += Integer.parseInt(L_actionUserModel.get(i).getPaymentAction());
                }
            }
            int sum_revenue = 0;
            for (int i = 0; i < L_actionUserRevenueModels.size(); i++) {
                Calendar cld_revenue = dateToCalendar(L_actionUserRevenueModels.get(i).getDateTimeAction());
                int get_month_db_revenue = cld_revenue.get(Calendar.MONTH);
                if (current_Month == get_month_db_revenue) {
                    sum_revenue += Integer.parseInt(L_actionUserRevenueModels.get(i).getPaymentAction());
                }
            }
            int total = sum_revenue - sum_expenses;
            if(total < 0){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Objects.requireNonNull(getContext()), "channel_id")
                        .setSmallIcon(R.drawable.expenses)
                        .setContentTitle("Thông báo")
                        .setContentText("Bạn đã chi quá số tiền bạn có!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                // Hiển thị thông báo
                NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(getContext());
                notificationManager1.notify(1, builder.build());
            }

            TextView Text_action = myView.findViewById(R.id.Text_action);
            String ST_Text_action= Text_action.getText().toString();
            TextView value_text_payment = myView.findViewById(R.id.value_text_payment);
            String ST_value_text_payment= value_text_payment.getText().toString();
            if(idCateSelected == 0 || ST_Text_action.isEmpty() || ST_value_text_payment.isEmpty()){
                Toast.makeText(getContext(),"Chưa đủ thông tin !",Toast.LENGTH_LONG).show();
            }else{
                //  Lưu vào CSDL
                ActionUserModel actionUserModel = new ActionUserModel(ST_Text_action, cld_choose.getTime(),ST_value_text_payment,idCateSelected);
                SaveDatabase.getInstance(getContext()).actionUserDao().insertActionUser(actionUserModel);
                Toast.makeText(getContext(),"thêm thành công !",Toast.LENGTH_LONG).show();
                //sau khi thêm xong xoá dữ liệu các textview;
                Text_action.setText("");
            }
        });
        return myView;
    }
    public void clickShowThingItemCategory(){
        //  vừa, gọi để add  adapter vừa xử lý mỗi khi click vào mọtt imgview  của category thì hiển thị ra thông tin của category đó.
        categoryAdapter = new CategoryAdapter(addListCategoryFromDatabase(), new IClickCategory() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemCategory(CategoryModel categoryModel) {
                TextView tv_show_cate_it_click = myView.findViewById(R.id.tv_show_cate_it_click);
                tv_show_cate_it_click.setText(onClickItem(categoryModel));
                ImageView img_cate_selected = myView.findViewById(R.id.img_cate_selected);
                img_cate_selected.setImageResource(categoryModel.getSourceImgCategory());
                idCateSelected = categoryModel.getIdCategory();
            }
        });
    }
    //  Hàm xử lý các sự kiện trong dialog dialog.
    public void openDialog(int gravity){
        final Dialog dialog= new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_category);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }else{
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes= window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);
            dialog.setCancelable(Gravity.BOTTOM == gravity);
            //  Xử lý sự kiện nút Huỷ
            MaterialButton bt_cancel_dialog_add_category = dialog.findViewById(R.id.bt_cancel_dialog_add_category);
            bt_cancel_dialog_add_category.setOnClickListener(view -> dialog.dismiss());
            //  Dùng spinnner để add các item-view vào spinner, trong các item đó chứa cả ảnh và text
            //  Text view, vì vậy sẽ set id cho mỗi IconModel để sau này sử dụng dễ dàng
            spinner_category = dialog.findViewById(R.id.dropdown_list_cate);
            IconCategoryAdapter iconCategoryAdapter = new IconCategoryAdapter(getContext(), DataIcon.getIconlist());
            spinner_category.setAdapter(iconCategoryAdapter);
            //  Xử lý sự kiện nút lưu danh mục.
            MaterialButton bt_save_category = dialog.findViewById(R.id.save_category);
            alert_error=dialog.findViewById(R.id.alert_error);
            edt_title_category=dialog.findViewById(R.id.edt_title_category);
            bt_save_category.setOnClickListener(view -> {
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
                   CategoryModel categoryModel = new CategoryModel(valueImgCate, edt_value_title_category);
                    SaveDatabase.getInstance(getContext()).categoryDAO().insertCategory(categoryModel);
                    dialog.dismiss();
                    Toast.makeText(
                        getContext(),
                        "Thêm danh mục thành công, RELOAD lại xuất hiện danh mục !",
                        Toast.LENGTH_LONG
                    ).show();
                    clickShowThingItemCategory();
                }
            });
            //  Xử lý với spinner(drop list) khi thg này adapter 1 item view, thì giá trị nó khi lấy ra sẽ ntn
        }
        dialog.show();
    }

    //  Hàm này để add dử liệu cào List để chuẩn bị setAdapter
    public List<CategoryModel> addListCategoryFromDatabase(){
        //  Móc dữ liệu từ database Sqlite vào đây
        //  Lấy dử liệu từ bảng category vào đây\
        return SaveDatabase.getInstance(getContext()).categoryDAO().getListCategory();
    }
    public Calendar GetDateChosse(int index){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        return calendar;
    }

    //hàm lấy ra ngày cần lấy
    @SuppressLint("SetTextI18n")
    public void GetCurrentDate(int index){
        viewCalender = myView.findViewById(R.id.show_calender);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,index);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK );
        int mounthOfWeek = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        String weekDay = "";
        String weekMounth="";
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
            weekMounth = "Tháng bảy";
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
        current_Month = mounthOfWeek;
        viewCalender.setText(currentDay+"/ "+ weekMounth +"/ "+year+"( " +weekDay+" )");
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            setupAdapter();
            mSrlLayout.setRefreshing(false);
        }, 2500);
    }
    //  Hàm này xử lý việc reload gọi lại adapter từ đầu để reload lại data.
    private void setupAdapter() {
        rcvCategory = myView.findViewById(R.id.flex_category);
        categoryAdapter = new CategoryAdapter(addListCategoryFromDatabase(), categoryModel -> {
            TextView tv_show_cate_it_click = myView.findViewById(R.id.tv_show_cate_it_click);
            tv_show_cate_it_click.setText(onClickItem(categoryModel));
            ImageView img_cate_selected = myView.findViewById(R.id.img_cate_selected);
            img_cate_selected.setImageResource(categoryModel.getSourceImgCategory());
            idCateSelected = categoryModel.getIdCategory();
        });
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.setAdapter(categoryAdapter);
    }
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}