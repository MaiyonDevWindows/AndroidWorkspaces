package com.example.musicapplication;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class PlaySongActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        TextView songTitleTextView = findViewById(R.id.songTitle);
        TextView songAuthorTextView = findViewById(R.id.songAuthor);
        VideoView videoView = findViewById(R.id.songVideoView);
        Button stopButton = findViewById(R.id.stopButton);
        Button backButton = findViewById(R.id.backButton);

        // Lấy tên bài hát và tác giả từ Intent
        Intent intent = getIntent();
        String songTitle = intent.getStringExtra("songTitle");
        String songAuthor = intent.getStringExtra("songAuthor");

        songTitleTextView.setText(songTitle);
        songAuthorTextView.setText(songAuthor);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.demo_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start();

        stopButton.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        });

        backButton.setOnClickListener(v -> {
            finish();
        });
    }

}
