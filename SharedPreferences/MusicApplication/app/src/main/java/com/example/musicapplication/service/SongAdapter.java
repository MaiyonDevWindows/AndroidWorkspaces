package com.example.musicapplication.service;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicapplication.PlaySongActivity;
import com.example.musicapplication.R;
import com.example.musicapplication.entity.Song;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {
    private final List<Song> songs;

    public SongAdapter(Context context, List<Song> songs) {
        super(context, 0, songs);
        this.songs = songs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem View đã được tạo chưa
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_song, parent, false);
        }

        // Lấy thông tin bài hát
        Song song = songs.get(position);
        ImageView songImageView = convertView.findViewById(R.id.songImageView);
        TextView songTitleTextView = convertView.findViewById(R.id.songTitleTextView);
        TextView songAuthorTextView = convertView.findViewById(R.id.songAuthorTextView);
        TextView songDurationTextView = convertView.findViewById(R.id.songDurationTextView);

        ImageButton playButton = convertView.findViewById(R.id.playButton);

        // Thiết lập hình ảnh, tên bài hát và tác giả
        songImageView.setImageResource(song.getImageResId());
        songTitleTextView.setText(song.getTitle());
        songAuthorTextView.setText(song.getAuthor());
        songDurationTextView.setText(song.getDuration());

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PlaySongActivity.class);
            intent.putExtra("songTitle", song.getTitle());
            intent.putExtra("songAuthor", song.getAuthor());
            intent.putExtra("songDuration", song.getDuration());
            getContext().startActivity(intent);
        });

        convertView.setOnLongClickListener(v -> {
            return false;
        });

        return convertView;
    }
}
