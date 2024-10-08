package com.example.quanlytaichinh.MainApplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.quanlytaichinh.MainApplication.FragmentInFragment.SecondFrag.ListExpensesFragment;
import com.example.quanlytaichinh.MainApplication.FragmentInFragment.SecondFrag.ListSpendingAdapter;
import com.example.quanlytaichinh.R;
import com.google.android.material.tabs.TabLayout;

public class SecondFragment extends Fragment {
    View myListSpendingFragment;
    ViewPager mviewPager;
    TabLayout mtabLayout;
    public SecondFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myListSpendingFragment= inflater.inflate(R.layout.fragment_second, container, false);
        //set theo id của view đó luôn
        mviewPager=myListSpendingFragment.findViewById(R.id.view_pager_list_spending);
        mtabLayout = myListSpendingFragment.findViewById(R.id.tab_layout_list_spending);
        mtabLayout.setupWithViewPager(mviewPager);
        ListSpendingAdapter listspendingAdapter = new ListSpendingAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        listspendingAdapter.addFragment(new ListExpensesFragment(), "Dữ liệu");
        mviewPager.setAdapter(listspendingAdapter);
        return myListSpendingFragment;
    }

}