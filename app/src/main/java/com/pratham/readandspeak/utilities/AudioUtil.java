package com.pratham.readandspeak.utilities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.pratham.readandspeak.ui.reading_screen.ReadingScreenActivity;

import java.io.IOException;


public class AudioUtil {

    private static MediaRecorder mRecorder;
    private static MediaPlayer mPlayer;

    public static void startRecording(String filePath) {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(filePath);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopRecording() {
        try {
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecorder = null;
    }

    public static void playRecording(String filePath) {
        try {
            if (mPlayer != null && mPlayer.isPlaying())
                mPlayer.stop();

            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(filePath);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayingAudio();
                  /*  if (activity instanceof ReadingScreenActivity)
                        ((ReadingScreenActivity) activity).audioStopped();
                   */
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopPlayingAudio() {
        try {
            if (mPlayer != null)
                mPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer = null;
    }

}
