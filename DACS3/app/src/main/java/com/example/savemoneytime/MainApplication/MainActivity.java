package com.example.savemoneytime.MainApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.savemoneytime.R;
import com.example.savemoneytime.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  * Lần đầu tiên sẽ xuất hiện ra firstfragment.
        replaceFragment(new FirstFragment());
        binding.buttonNavigation.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.secondFragment:
                    replaceFragment(new SecondFragment());
                    break;
                case R.id.thirdFragment:
                    replaceFragment(new ThirdFragment());
                    break;
                default:
                    replaceFragment(new FirstFragment());
                    break;
            }
            return true;
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}