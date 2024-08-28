package com.example.basiccalc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText input1, input2;
    private TextView result;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        input1 = findViewById(R.id.input_first_num);
        input2 = findViewById(R.id.input_sec_num);
        result = findViewById(R.id.output_result);
        btnAdd = findViewById(R.id.add_button);
        btnSubtract = findViewById(R.id.subtract_button);
        btnMultiply = findViewById(R.id.multiply_button);
        btnDivide = findViewById(R.id.divide_button);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Gán sự kiện cho các nút
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('+');
            }
        });
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('-');
            }
        });
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('*');
            }
        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation('/');
            }
        });
    }
    private void performOperation(char operator) {
        String num1 = input1.getText().toString();
        String num2 = input2.getText().toString();
        if (!num1.isEmpty() && !num2.isEmpty()) {
            double n1 = Double.parseDouble(num1);
            double n2 = Double.parseDouble(num2);
            double res = 0;
            switch (operator) {
                case '+':
                    res = n1 + n2;
                    break;
                case '-':
                    res = n1 - n2;
                    break;
                case '*':
                    res = n1 * n2;
                    break;
                case '/':
                    if (n2 != 0) {
                        res = n1 / n2;
                    } else {
                        result.setText("Cannot divide by zero");
                        return;
                    }
                    break;
            }
            result.setText(String.valueOf(res));
        } else {
            result.setText("Please enter both numbers");
        }
    }
}