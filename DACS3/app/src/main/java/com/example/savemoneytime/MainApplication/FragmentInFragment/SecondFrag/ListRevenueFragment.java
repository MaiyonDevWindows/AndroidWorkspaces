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

import com.example.savemoneytime.Interface.IclickAction;
import com.example.savemoneytime.MainApplication.Adapters.ListRevenueAdapter; // Changed adapter to ListRevenueAdapter
import com.example.savemoneytime.MainApplication.Models.ActionUserModel;
import com.example.savemoneytime.MainApplication.Models.ActionUserRevenueModel;
import com.example.savemoneytime.MainApplication.Models.Setting;
import com.example.savemoneytime.R;
import com.example.savemoneytime.database.SaveDatabase;
import com.google.android.material.button.MaterialButton;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListRevenueFragment extends Fragment  {

}
