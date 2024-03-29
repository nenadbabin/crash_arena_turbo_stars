package com.example.pmu_projekat.media_player;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.pmu_projekat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyMusicPlayer extends Service {

    private MediaPlayer mediaPlayer;
    public static final String PLAY_TRACK = "PLAY";
    public static final String PAUSE_TRACK = "PAUSE";
    public static final String STOP_TRACK = "STOP";
    public static final String RESET_PLAYER = "RESET_PLAYER";

    private int currentPosition;
    private boolean isShuffle;
    private List<Integer> songList;
    private int currentSong;
    private int isRepeat;

    private static int tokens = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        currentPosition = 0;
        isShuffle = true;
        isRepeat = 2;

        songList = new ArrayList<>();
        songList.add(R.raw.centuries);
        songList.add(R.raw.punch_drunk_love);
        songList.add(R.raw.seven_nation_army);
        songList.add(R.raw.song_2);
        songList.add(R.raw.we_will_rock_you);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getStringExtra("action");

        switch (action) {
            case RESET_PLAYER : {
                Collections.shuffle(songList);
                currentSong = songList.get(0);
                createPlayer();
                break;
            }
            case PLAY_TRACK: {
                mediaPlayer.start();
                break;
            }
            case PAUSE_TRACK: {
                if (mediaPlayer != null)
                {
                    mediaPlayer.pause();
                }
                break;
            }
            case STOP_TRACK: {
                if (mediaPlayer != null)
                {
                    mediaPlayer.release();
                }
                break;
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void nextSong() {
        int numOfSong = songList.size();

        if (!isShuffle) // Shuffle mode is off
        {
            if (currentPosition < numOfSong - 1) {
                currentPosition++;
            } else {
                currentPosition = 0;
            }
            currentSong = songList.get(currentPosition);
            Log.d("my_log", "position = "+ currentPosition);
            playBackMusic();
        }
        else // Shuffle mode is on
        {
            Random rand = new Random();
            currentPosition = rand.nextInt(numOfSong);
            currentSong = songList.get(currentPosition);
            Log.d("my_log", "position = "+ currentPosition);
            playBackMusic();
        }
    }

    public void playBackMusic() {
        try {
            createPlayer();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endOfTheSong() {
        if (isRepeat == 1) // currently repeat one song
        {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
        else if (isRepeat == 2) // currently repeat all songs
        {
            nextSong();
        }
        else // currently no repeat
        {
            if (currentPosition != songList.size() - 1)
            {
                nextSong();
            }
        }
    }

    private void createPlayer()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, currentSong);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                endOfTheSong();
            }
        });
    }

    public static int getTokens() {
        return tokens;
    }

    public static void setTokens(int tokens) {
        MyMusicPlayer.tokens = tokens;
    }

    public static void addToken()
    {
        tokens++;
    }

    public static void removeToken()
    {
        if (tokens > 0)
        {
            tokens--;
        }
    }
}
