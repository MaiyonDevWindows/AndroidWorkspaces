package com.example.quanlytaichinh.MainApplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.quanlytaichinh.Interface.IclickAction;
import com.example.quanlytaichinh.Interface.IclickRevenueAction;
import com.example.quanlytaichinh.MainApplication.Adapters.ListExpensesAdapter;
import com.example.quanlytaichinh.MainApplication.Adapters.ListRevenueAdapter;
import com.example.quanlytaichinh.MainApplication.Models.ActionUserModel;
import com.example.quanlytaichinh.MainApplication.Models.ActionUserRevenueModel;
import com.example.quanlytaichinh.R;
import com.example.quanlytaichinh.database.RevenueDB.RevenueDatabase;
import com.example.quanlytaichinh.database.SaveDatabase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThirdFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private View myView;
    private Spinner mspinner;
    private RecyclerView rcl_tk_expenses, rcv_tk_revenue;
    private int mounthSelect = 0;
    private List<ActionUserModel> L_actionUserModel;
    private List<ActionUserRevenueModel> L_actionUserRevenueModels;
    private ListRevenueAdapter listRevenueAdapter;
    private ListExpensesAdapter listExpensesAdapter;
    public int IdActionClick = 0;
    public int IdActionClickE = 0;

    private PieChart pieChartChiTieu, pieChartThuNhap;


    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_third, container, false);
        mspinner = myView.findViewById(R.id.dropdown_list_mounth);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mounth, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(arrayAdapter);
        mspinner.setOnItemSelectedListener(this);

//        xu ly do thi ow day
        rcl_tk_expenses = myView.findViewById(R.id.rcl_tk_expenses);
        rcv_tk_revenue = myView.findViewById(R.id.rcv_tk_revenue);
        pieChartChiTieu = myView.findViewById(R.id.chartChiTieu);
        pieChartThuNhap = myView.findViewById(R.id.chartThuThap);

        Calendar calendar = Calendar.getInstance();
        int current_Mounth = calendar.get(Calendar.MONTH);
        TextView tv_tk_mounth = (TextView) myView.findViewById(R.id.tv_tk_mounth);
        tv_tk_mounth.setText(tv_tk_mounth.getText() + "tháng " + String.valueOf(current_Mounth));
        L_actionUserModel = SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
        L_actionUserRevenueModels = RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().getListAction();
        int sum_expenses = 0;
        for (int i = 0; i < L_actionUserModel.size(); i++) {
            Calendar cld_expenses = dateToCalendar(L_actionUserModel.get(i).getDateTimeAction());
            int get_month_db_expenses = cld_expenses.get(Calendar.MONTH);
            if (current_Mounth == get_month_db_expenses) {
                sum_expenses += Integer.valueOf(L_actionUserModel.get(i).getPaymentAction());
            }
        }
        TextView tv_show_expenses = (TextView) myView.findViewById(R.id.tv_show_expenses);
        tv_show_expenses.setText(String.valueOf(sum_expenses) + " vnd");

        int sum_revenue = 0;
        for (int i = 0; i < L_actionUserRevenueModels.size(); i++) {
            Calendar cld_revenue = dateToCalendar(L_actionUserRevenueModels.get(i).getDateTimeAction());
            int get_mounth_db_revune = cld_revenue.get(Calendar.MONTH);
            if (current_Mounth == get_mounth_db_revune) {
                sum_revenue += Integer.valueOf(L_actionUserRevenueModels.get(i).getPaymentAction());
            }
        }
        TextView tv_revenus_show = (TextView) myView.findViewById(R.id.tv_revenus_show);
        tv_revenus_show.setText(String.valueOf(sum_revenue) + " vnd");
        SetUpAdapter();
        SetUpAdapterE();

        TextView tv_sd = (TextView) myView.findViewById(R.id.tv_sd);
        if (sum_revenue > sum_expenses) {
            tv_sd.setText("Chi tiêu tốt, vẫn còn dư: " + String.valueOf(sum_revenue - sum_expenses) + "vnd");
        } else if (sum_revenue == sum_expenses) {
            tv_sd.setText("Coi lại cách chi tiêu, còn 0 đồng ");
        } else {
            tv_sd.setText("Cảnh cáo hành vi này, bị âm " + String.valueOf(sum_expenses - sum_revenue) + "vnd");
        }

        addDataPieChartChiTieu();
        addDataPieChartThuNhap();
//        TextView day_best_epenses  = (TextView) myView.findViewById(R.id.day_best_epenses);
//        day_best_epenses.setText(String.valueOf(getDateMoseExpenses(L_actionUserModel,current_Mounth)));
//
//        TextView tv_show_best_action_expenses = (TextView)myView.findViewById(R.id.tv_show_best_action_expenses);
//        ImageView img_src_cate = (ImageView)myView.findViewById(R.id.img_src_cate);

//        String  actionUM = getBestActionExpenses(L_actionUserModel,current_Mounth);
//
//        tv_show_best_action_expenses.setText(actionUM);
        return myView;
    }

    //của thằng spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object itemMounth = adapterView.getItemAtPosition(i);
        if (itemMounth != null) {
            Toast.makeText(getContext(), itemMounth.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        addDataPieChartChiTieu();
        addDataPieChartThuNhap();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public List<ActionUserRevenueModel> addListActionFromDatabase(List<ActionUserRevenueModel> LactionUserModels) {
        LactionUserModels = RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().getListAction();
        //lấy dử liệu từ bảng category vào đây\
        return LactionUserModels;
    }

    public List<ActionUserModel> addListActionFromDatabaseE(List<ActionUserModel> LacUserModels) {
        LacUserModels = SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
        return LacUserModels;
    }

    public void SetUpAdapterE() {
        {
            listExpensesAdapter = new ListExpensesAdapter(addListActionFromDatabaseE(L_actionUserModel), new IclickAction() {

                @Override
                public void onClickItemAction(ActionUserModel actionUserModel) {
                    IdActionClickE = onClickItemE(actionUserModel);
                    //lấy id này rồi xoá chết mẹ nó
                    SaveDatabase.getInstance(getContext()).actionUserDao().deleteById(IdActionClickE);
                    Toast.makeText(getContext(), "Xoá thành công !!, reload ", Toast.LENGTH_SHORT).show();
                    //reload lại các list
                    SetUpAdapterE();
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcl_tk_expenses.setLayoutManager(layoutManager);
            rcl_tk_expenses.setAdapter(listExpensesAdapter);
        }

    }

    public void SetUpAdapter() {
        {
            listRevenueAdapter = new ListRevenueAdapter(addListActionFromDatabase(L_actionUserRevenueModels), new IclickRevenueAction() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClickItemAction(ActionUserRevenueModel actionUserModel) {
                    IdActionClick = onClickItem(actionUserModel);
                    //lấy id này rồi xoá chết mẹ nó
                    RevenueDatabase.getInstance(getContext()).actionUserRevenueDao().deleteById(IdActionClick);
                    Toast.makeText(getContext(), "Xoá thành công !!, reload ", Toast.LENGTH_SHORT).show();
                    //reload lại các list
                    SetUpAdapter();
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_tk_revenue.setLayoutManager(layoutManager);
            rcv_tk_revenue.setAdapter(listRevenueAdapter);
        }

    }

    private int onClickItem(ActionUserRevenueModel actionUserModel) {
        //xử lý sự kiện click item ở recycleview ở đây
        return actionUserModel.getIdAction();
    }

    private int onClickItemE(ActionUserModel actionUserModel) {
        return actionUserModel.getIdAction();
    }
//    //lấy ra ngày tiêu nhiều tiền nhất trong tháng
//    private Calendar getDateMoseExpenses(List<ActionUserModel> LaActionUserModels, int mounth){
//        int x =0;
//        Calendar bestDayExpenses = Calendar.getInstance();
//        for(int  i=0; i< LaActionUserModels.size(); i++){
//           Calendar cld_mounth = dateToCalendar(LaActionUserModels.get(i).getDateTimeAction());
//           if(mounth == cld_mounth.get(Calendar.MONTH)){
//               if(x <=  Integer.parseInt(LaActionUserModels.get(i).getPaymentAction())){
//                   x = Integer.parseInt(LaActionUserModels.get(i).getPaymentAction());
//                   bestDayExpenses= dateToCalendar(LaActionUserModels.get(i).getDateTimeAction());
//               }
//           }
//        }
//        return  bestDayExpenses;
//    }
//    //lấy ra khoản tiêu nhiều nhất
//    private String getBestActionExpenses(List<ActionUserModel>l_actionUserModel,int mounth){
//        int x=0;
//        String y="";
//        for(int i = 0; i<= l_actionUserModel.size(); i++){
//            Calendar cld_mounth = dateToCalendar(l_actionUserModel.get(i).getDateTimeAction());
//            if(mounth == cld_mounth.get(Calendar.MONTH)){
//                if(x <= Integer.parseInt(l_actionUserModel.get(i).getPaymentAction())){
//                    x = Integer.parseInt(l_actionUserModel.get(i).getPaymentAction());
//                    y=String.valueOf(l_actionUserModel.get(i).getTitleAction());
//                }
//            }
//        }
//        return y;
//    }

    //thấy list chi tiêu thêm vào piechart
    private void addDataPieChartChiTieu() {
        List<PieEntry> dataEntries = new ArrayList<>();

        //chia ra từng loại chi tiêu
        //lấy ra loại chi tiêu không trùng nhau
        List<Integer> listCate = new ArrayList<>();
        for (int i = 0; i < L_actionUserModel.size(); i++) {
            if (!listCate.contains(L_actionUserModel.get(i).getIdCategory())) {
                listCate.add(L_actionUserModel.get(i).getIdCategory());
            }
        }
        //tính tổng tiền của từng loại chi tiêu
        for (int i = 0; i < listCate.size(); i++) {
            int sum = 0;
            for (int j = 0; j < L_actionUserModel.size(); j++) {
                if (listCate.get(i) == L_actionUserModel.get(j).getIdCategory()) {
                    sum += Integer.parseInt(L_actionUserModel.get(j).getPaymentAction());
                }
            }
            dataEntries.add(new PieEntry(sum, SaveDatabase.getInstance(getContext()).categoryDAO().getListByIdCategory(listCate.get(i)).get(0).getTitleCatelogy()));
        }

        PieDataSet dataSet = new PieDataSet(dataEntries, "Chi tiêu");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        pieChartChiTieu.setData(data);
        pieChartChiTieu.invalidate();


    }

    //thêm vào list thu nhập vào piechart
    private void addDataPieChartThuNhap() {
        List<PieEntry> dataEntries = new ArrayList<>();

        //chia ra từng loại thu nhập
        //lấy ra loại thu nhập không trùng nhau
        List<Integer> listCate = new ArrayList<>();
        for (int i = 0; i < L_actionUserRevenueModels.size(); i++) {
            if (!listCate.contains(L_actionUserRevenueModels.get(i).getIdCategory())) {
                listCate.add(L_actionUserRevenueModels.get(i).getIdCategory());
            }
        }
        //tính tổng tiền của từng loại thu nhập
        for (int i = 0; i < listCate.size(); i++) {
            int sum = 0;
            for (int j = 0; j < L_actionUserRevenueModels.size(); j++) {
                if (listCate.get(i) == L_actionUserRevenueModels.get(j).getIdCategory()) {
                    sum += Integer.parseInt(L_actionUserRevenueModels.get(j).getPaymentAction());
                }
            }
            dataEntries.add(new PieEntry(sum, RevenueDatabase.getInstance(getContext()).categoryRevenueDAO().getListByIdCategoryRevenue(listCate.get(i)).get(0).getTitleCatelogy()));
        }


        PieDataSet dataSet = new PieDataSet(dataEntries, "Thu nhập");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        pieChartThuNhap.setData(data);
        pieChartThuNhap.invalidate();
    }
}