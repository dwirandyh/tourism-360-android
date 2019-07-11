package com.dwirandyh.wisatalampung.view.vr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.utils.Constant;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class VRActivity extends AppCompatActivity {

    private static final String TAG = "VRActivity";
    private VrPanoramaView vrPanoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);

        vrPanoramaView = (VrPanoramaView) findViewById(R.id.vrPanoramaView);
        loadImage();
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
        vrPanoramaView.pauseRendering();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        vrPanoramaView.shutdown();
        super.onDestroy();
    }
}
