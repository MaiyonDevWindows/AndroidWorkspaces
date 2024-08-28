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

    EditText etProductId, etProductName, etProductQuantity;
    Button btnAdd, btnUpdate, btnDel, btnShow;
    ListView lvProducts;
    ProductService productService;
    ArrayAdapter<String> adapter;
    List<String> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etProductId = findViewById(R.id.et_productId);
        etProductName = findViewById(R.id.et_productName);
        etProductQuantity = findViewById(R.id.et_productQuantity);

        btnAdd = findViewById(R.id.btn_add);
        btnUpdate = findViewById(R.id.btn_edit);
        btnDel = findViewById(R.id.btn_delete);
        btnShow = findViewById(R.id.btn_display);

        lvProducts = findViewById(R.id.listViewItems);

        productService = new ProductService(this);
        productList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        lvProducts.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = etProductId.getText().toString();
                String productName = etProductName.getText().toString();
                int productQuantity = Integer.parseInt(etProductQuantity.getText().toString());
                Product product = new Product(productId, productName, productQuantity);
                productService.addProduct(product);
                Toast.makeText(MainActivity.this, "Product added", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = etProductId.getText().toString();
                String productName = etProductName.getText().toString();
                int productQuantity = Integer.parseInt(etProductQuantity.getText().toString());
                Product product = new Product(productId, productName, productQuantity);
                productService.updateProduct(product);
                Toast.makeText(MainActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = etProductId.getText().toString();
                productService.deleteProduct(productId);
                Toast.makeText(MainActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.clear();
                List<Product> products = productService.getAllProducts();
                for (Product product : products) {
                    productList.add("ID: " + product.getProductId() + ", Name: " + product.getProductName() + ", Quantity: " + product.getProductQuantity());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}