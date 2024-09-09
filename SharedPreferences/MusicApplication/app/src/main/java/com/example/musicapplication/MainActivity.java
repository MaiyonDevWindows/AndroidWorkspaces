package com.example.musicapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapplication.entity.Song;
import com.example.musicapplication.service.SongAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Song> songList;
    private SongAdapter adapter;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "SongData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        songList = new ArrayList<>();
        loadSongs();

        adapter = new SongAdapter(this, songList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_song) {
            Intent intent = new Intent(MainActivity.this, MusicActivity.class);
            startActivity(intent);
            return true;
        } else if(item.getItemId() == R.id.exit) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.edit_song) {
            showEditDialog(info.position);
            return true;
        } else if(item.getItemId() == R.id.delete_song) {
            deleteSong(info.position);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void loadSongs() {
        int size = sharedPreferences.getInt("SongSize", 0);
        for (int i = 0; i < size; i++) {
            String songTitle = sharedPreferences.getString("SongTitle_" + i, "Unknown");
            String songAuthor = sharedPreferences.getString("SongAuthor_" + i, "Unknown");
            String songDuration = sharedPreferences.getString("Duration_" + i, "Unknown");
            int songImage = sharedPreferences.getInt("SongImage_" + i, R.drawable.music0);
            songList.add(new Song(songTitle, songAuthor, songDuration, songImage));
        }
    }

    private void deleteSong(int position) {
        // Xóa bài hát khỏi danh sách và lưu lại trong SharedPreferences
        songList.remove(position);
        saveSongs();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Đã xóa bài hát", Toast.LENGTH_SHORT).show();
    }

    private void showEditDialog(int position) {
        // Hiển thị một dialog để người dùng có thể chỉnh sửa bài hát
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_song, null);

        final EditText editTitleEditText = view.findViewById(R.id.editTitleEditText);
        final EditText editAuthorEditText = view.findViewById(R.id.editAuthorEditText);
        final EditText editDurationEditText = view.findViewById(R.id.editDurationEditText);
        Button saveEditButton = view.findViewById(R.id.saveEditButton);

        // Lấy thông tin hiện tại của bài hát để hiển thị lên dialog
        Song currentSong = songList.get(position);
        editTitleEditText.setText(currentSong.getTitle());
        editAuthorEditText.setText(currentSong.getAuthor());
        editDurationEditText.setText(currentSong.getDuration());

        builder.setView(view);
        AlertDialog dialog = builder.create();

        saveEditButton.setOnClickListener(v -> {
            // Cập nhật bài hát sau khi người dùng chỉnh sửa
            String newTitle = editTitleEditText.getText().toString();
            String newAuthor = editAuthorEditText.getText().toString();
            String newDuration = editDurationEditText.getText().toString();
            currentSong.setTitle(newTitle);
            currentSong.setAuthor(newAuthor);
            currentSong.setDuration(newDuration);
            saveSongs();
            adapter.notifyDataSetChanged();
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Đã sửa bài hát", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    private void saveSongs() {
        // Lưu lại danh sách bài hát vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("SongSize", songList.size());
        for (int i = 0; i < songList.size(); i++) {
            Song song = songList.get(i);
            editor.putString("SongTitle_" + i, song.getTitle());
            editor.putString("SongAuthor_" + i, song.getAuthor());
            editor.putString("Duration_" + i, song.getDuration());
            editor.putInt("SongImage_" + i, song.getImageResId());
        }
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Xóa dữ liệu cũ và tải lại danh sách bài hát
        songList.clear(); // Xóa danh sách bài hát hiện tại
        loadSongs(); // Tải lại bài hát từ SharedPreferences
        adapter.notifyDataSetChanged(); // Cập nhật ListView
    }
}