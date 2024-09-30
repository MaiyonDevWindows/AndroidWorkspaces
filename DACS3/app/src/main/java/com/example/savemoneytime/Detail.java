package com.example.savemoneytime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.savemoneytime.MainApplication.Models.CategoryModel;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            CategoryModel categoryModel =(CategoryModel) bundle.get("object_cate");
            TextView textView = findViewById(R.id.tv_show_cate);
            assert categoryModel != null;
            textView.setText(categoryModel.getTitleCategory());
        }
    }
}