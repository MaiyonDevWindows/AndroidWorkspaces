package com.example.savemoneytime.MainApplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.savemoneytime.MainApplication.FragmentInFragment.SecondFrag.ListExpensesFragment;
import com.example.savemoneytime.MainApplication.FragmentInFragment.SecondFrag.ListSpendingAdapter;
import com.example.savemoneytime.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class SecondFragment extends Fragment {
    View myListSpendingFragment;
    ViewPager mviewPager;
    TabLayout mtabLayout;
    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myListSpendingFragment= inflater.inflate(R.layout.fragment_second, container, false);
        //  Set theo id của view đó luôn
        mviewPager=myListSpendingFragment.findViewById(R.id.view_pager_list_spending);
        mtabLayout = myListSpendingFragment.findViewById(R.id.tab_layout_list_spending);
        mtabLayout.setupWithViewPager(mviewPager);
        ListSpendingAdapter listspendingAdapter = new ListSpendingAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        listspendingAdapter.addFragment(new ListExpensesFragment(), "Dữ liệu");
        mviewPager.setAdapter(listspendingAdapter);
        return myListSpendingFragment;
    }

}