package com.example.savemoneytime.MainApplication.FragmentInFragment.SecondFrag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.savemoneytime.utils.IClickAction;
import com.example.savemoneytime.MainApplication.Adapters.ListExpensesAdapter;
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.Setting;
import com.example.savemoneytime.R;
import com.example.savemoneytime.configs.databases.SaveDatabase;
import com.google.android.material.button.MaterialButton;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ListExpensesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View myview;
    RecyclerView rcl_contain_list_expenses;
    private EditText edt_weekless, edt_normal, edt_strong;
    private SwipeRefreshLayout reload_list_action;
    private List<Setting> L_setting;

    public int IdActionClick = 0;

    public ListExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_list_expenses, container, false);

        // Set up the adapter
        SetUpAdapter();

        // Reload data on swipe
        reload_list_action = myview.findViewById(R.id.reload_list_action);
        reload_list_action.setColorSchemeResources(R.color.colorTextPrimary, R.color.ColorActive, R.color.design_default_color_primary_dark);
        reload_list_action.setOnRefreshListener(this);

        // Calendar setup using applandeo material calendar view
        CalendarView calendarView = myview.findViewById(R.id.custom_calender);

        // Fetching action data
        List<ActionUserModel> actions = SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
        if (!actions.isEmpty()) {
            List<EventDay> events = new ArrayList<>();
            for (ActionUserModel action : actions) {
                Calendar calendar = dateToCalendar(action.getDateTimeAction());
                events.add(new EventDay(calendar, R.drawable.bg_spinner_category)); // Custom icon for events
            }
            calendarView.setEvents(events);
        }

        // Button for settings
        ImageButton bt_setting_user = myview.findViewById(R.id.bt_setting_user);
        bt_setting_user.setOnClickListener(view -> OpenDialog(Gravity.BOTTOM));

        return myview;
    }

    // Setting up the adapter with data from the database
    public void SetUpAdapter() {
        // Delete action by id
        ListExpensesAdapter listExpensesAdapter = new ListExpensesAdapter(addListActionFromDatabase(), new IClickAction() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClickItemAction(ActionUserModel actionUserModel) {
                IdActionClick = onClickItem(actionUserModel);
                // Delete action by id
                SaveDatabase.getInstance(getContext()).actionUserDao().deleteById(IdActionClick);
                Toast.makeText(getContext(), "Deleted successfully! Please reload.", Toast.LENGTH_LONG).show();
                SetUpAdapter();
            }
        });
        rcl_contain_list_expenses = myview.findViewById(R.id.rcl_contain_list_expenses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_contain_list_expenses.setLayoutManager(layoutManager);
        rcl_contain_list_expenses.setAdapter(listExpensesAdapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            SetUpAdapter();
            reload_list_action.setRefreshing(false);
        }, 2500);
    }

    // Handling settings dialog
    public void OpenDialog(int gravity) {
        final Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_user);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);

            dialog.setCancelable(Gravity.BOTTOM == gravity);

            edt_weekless = dialog.findViewById(R.id.edt_weekless);
            edt_normal = dialog.findViewById(R.id.edt_normal);
            edt_strong = dialog.findViewById(R.id.edt_strong);

            TextView tv_lv1 = dialog.findViewById(R.id.tv_lv1);
            TextView tv_lv2 = dialog.findViewById(R.id.tv_lv2);
            TextView tv_lv3 = dialog.findViewById(R.id.tv_lv3);

            MaterialButton save_setting_user = dialog.findViewById(R.id.save_setting_user);

            L_setting = SaveDatabase.getInstance(getContext()).aSettingUserDAO().getsetting();
            if (!L_setting.isEmpty()) {
                Setting setting = L_setting.get(0);
                edt_weekless.setText(String.valueOf(setting.getWeekless()));
                edt_normal.setText(String.valueOf(setting.getNormal()));
                edt_strong.setText(String.valueOf(setting.getStrong()));
            }

            save_setting_user.setOnClickListener(view -> {
                String s1 = edt_weekless.getText().toString();
                String s2 = edt_normal.getText().toString();
                String s3 = edt_strong.getText().toString();

                int m1 = Integer.parseInt(s1);
                int m2 = Integer.parseInt(s2);
                int m3 = Integer.parseInt(s3);

                if (L_setting.isEmpty() && !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty()) {
                    Setting setting = new Setting(m1, m2, m3);
                    SaveDatabase.getInstance(getContext()).aSettingUserDAO().insertSetting(setting);
                    Toast.makeText(getContext(), "Settings added successfully!", Toast.LENGTH_LONG).show();
                } else {
                    SaveDatabase.getInstance(getContext()).aSettingUserDAO().update(m1, m2, m3);
                    Toast.makeText(getContext(), "Settings updated successfully!", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            });

            dialog.show();
        }
    }

    // Converting Date to Calendar
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    // Fetch list from the database
    public List<ActionUserModel> addListActionFromDatabase() {
        return SaveDatabase.getInstance(getContext()).actionUserDao().getListAction();
    }

    // On item click
    private int onClickItem(ActionUserModel actionUserModel) {
        return actionUserModel.getIdAction();
    }
}
