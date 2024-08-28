package com.example.sqliteexample;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtProductId, txtProductName, txtQuantity;
    Button btnAdd, btnEdit, btnDel, btnDisplay;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ProductDAO productDAO;
    List<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtProductId = findViewById(R.id.et_productId);
        txtProductName = findViewById(R.id.et_productName);
        txtQuantity = findViewById(R.id.et_productQuantity);

        btnAdd = findViewById(R.id.btn_add);
        btnEdit = findViewById(R.id.btn_edit);
        btnDel = findViewById(R.id.btn_delete);
        btnDisplay = findViewById(R.id.btn_display);

        listView = findViewById(R.id.listViewItems);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button display.
        strings.clear();
        productDAO = new ProductDAO(MainActivity.this);
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, strings);
        listView.setAdapter(arrayAdapter);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strings.clear();
                productDAO = new ProductDAO(MainActivity.this);
                arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, strings);
                listView.setAdapter(arrayAdapter);
            }
        });

        // Button add.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                product.setProductId(txtProductId.getText().toString());
                product.setProductName(txtProductName.getText().toString());
                product.setProductQuantity(Integer.parseInt(txtQuantity.getText().toString()));
                int result = productDAO.insertProduct(product);
                String resultToast = result == 1 ? "Thành công" : "Thất bại";
                Toast.makeText(MainActivity.this, resultToast, Toast.LENGTH_LONG).show();
            }
        });
    }
}