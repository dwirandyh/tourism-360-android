package com.dwirandyh.wisatalampung.view.vr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.utils.Constant;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class VRActivity extends AppCompatActivity {

    private static final String TAG = "VRActivity";
    private VrPanoramaView vrPanoramaView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);

        vrPanoramaView = findViewById(R.id.vrPanoramaView);
        loadImage();
        playOverviewAudio();
    }

    private void playOverviewAudio() {
        String fileName = getIntent().getStringExtra("overviewFileName");
        if (fileName.isEmpty()) {
            return;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //gallery.setDirectionFile("lembah_hijau_waterboom.mp3"); //TODO: Remove this lane after audio is ready in database
            String audioFileUrl = Constant.BASE_AUDIO_URL + fileName;
            mediaPlayer.setDataSource(audioFileUrl);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlaying();
                }
            });
            mediaPlayer.prepareAsync();
            Toast.makeText(VRActivity.this, "Prepare Audio File", Toast.LENGTH_LONG).show();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    Toast.makeText(VRActivity.this, "Playing Audio", Toast.LENGTH_SHORT).show();
                }
            });

            //mediaPlayer.start();
        } catch (IOException e) {
            // Catch the exception
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void loadImage() {
        String fileName = getIntent().getStringExtra("galleryFileName");
        String url = Constant.BASE_IMAGE_URL + fileName;
        new DownloadImageAsync(vrPanoramaView).execute(url);
    }

    private class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {
        VrPanoramaView panoramaView;

        public DownloadImageAsync(VrPanoramaView panoramaView) {
            this.panoramaView = panoramaView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            VrPanoramaView.Options options = new VrPanoramaView.Options();
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            panoramaView.loadImageFromBitmap(bitmap, options);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        vrPanoramaView.pauseRendering();
    }

    @Override
    protected void onResume() {
        super.onResume();

        vrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
        vrPanoramaView.shutdown();
        super.onDestroy();
    }


}
