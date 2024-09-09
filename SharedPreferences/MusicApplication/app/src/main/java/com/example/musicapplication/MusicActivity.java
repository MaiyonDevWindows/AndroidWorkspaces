package com.example.musicapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MusicActivity extends AppCompatActivity {
    private EditText titleEditText, authorEditText, durationEditText;
    private ImageView songImageView;
    private Button saveButton, backButton;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "SongData";

    private int[] imageResources = {
        R.drawable.music1, R.drawable.music2, R.drawable.music3,
        R.drawable.music4, R.drawable.music5, R.drawable.music6,
        R.drawable.music7, R.drawable.music8, R.drawable.music9,
        R.drawable.music10, R.drawable.music11, R.drawable.music12,
        R.drawable.music13, R.drawable.music14, R.drawable.music15,
        R.drawable.music16, R.drawable.music17, R.drawable.music18,
        R.drawable.music19, R.drawable.music20
    }; // Thêm các hình ảnh vào drawable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        durationEditText = findViewById(R.id.durationEditText);
        songImageView = findViewById(R.id.songImageView);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        // Chọn ngẫu nhiên một hình ảnh từ danh sách
        Random random = new Random();
        int randomImage = imageResources[random.nextInt(imageResources.length)];
        songImageView.setImageResource(randomImage);
        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String author = authorEditText.getText().toString();
            String duration = durationEditText.getText().toString();
            if (!title.isEmpty() && !author.isEmpty() && !duration.isEmpty()) {
                saveSong(title, author, duration, randomImage);  // Gọi phương thức `saveSong` mới
                Toast.makeText(MusicActivity.this, "Đã thêm bài hát", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại MainActivity
            } else {
                Toast.makeText(MusicActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void saveSong(String title, String author, String duration, int imageResId) {
        int size = sharedPreferences.getInt("SongSize", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SongTitle_" + size, title);
        editor.putString("SongAuthor_" + size, author);
        editor.putString("Duration_" + size, duration);
        editor.putInt("SongImage_" + size, imageResId);
        editor.putInt("SongSize", size + 1);
        editor.apply();
    }
}