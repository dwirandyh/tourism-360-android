package com.dwirandyh.wisatalampung.adapter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dwirandyh.wisatalampung.R;
import com.dwirandyh.wisatalampung.databinding.AttractionGalleryItemBinding;
import com.dwirandyh.wisatalampung.model.Gallery;
import com.dwirandyh.wisatalampung.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private OnItemClickListener onItemClickListener;
    private ArrayList<Gallery> galleries = new ArrayList<>();

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AttractionGalleryItemBinding attractionGalleryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.attraction_gallery_item, parent, false);
        return new GalleryViewHolder(attractionGalleryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Gallery attractionGallery = galleries.get(position);
        holder.attractionGalleryItemBinding.setGallery(attractionGallery);
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    public void setGallerys(ArrayList<Gallery> galleries) {
        this.galleries = galleries;
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {
        AttractionGalleryItemBinding attractionGalleryItemBinding;

        private MediaPlayer mediaPlayer;

        GalleryViewHolder(@NonNull final AttractionGalleryItemBinding attractionGalleryItemBinding) {
            super(attractionGalleryItemBinding.getRoot());

            this.attractionGalleryItemBinding = attractionGalleryItemBinding;
            this.attractionGalleryItemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();

                    if (onItemClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(galleries.get(clickedPosition));
                    }
                }
            });

            this.attractionGalleryItemBinding.btnPlaySound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gallery gallery = galleries.get(getAdapterPosition());
                    if (gallery.getDirectionFile().isEmpty()){
                        Toast.makeText(itemView.getContext(), "Tidak bisa memutar audio", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (mediaPlayer != null && mediaPlayer.isPlaying()){
                        stopPlaying();
                        return;
                    }

                    stopPlaying();

                    attractionGalleryItemBinding.btnPlayIcon.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_stop_black_24dp));

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        //gallery.setDirectionFile("lembah_hijau_waterboom.mp3"); //TODO: Remove this lane after audio is ready in database
                        String audioFileUrl = Constant.BASE_AUDIO_URL + gallery.getDirectionFile();
                        mediaPlayer.setDataSource(audioFileUrl);
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                stopPlaying();
                            }
                        });
                        mediaPlayer.prepareAsync();
                        Toast.makeText(attractionGalleryItemBinding.getRoot().getContext(), "Prepare Audio File", Toast.LENGTH_LONG).show();

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                                Toast.makeText(attractionGalleryItemBinding.getRoot().getContext(), "Playing Audio", Toast.LENGTH_SHORT).show();
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

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            attractionGalleryItemBinding.btnPlayIcon.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_play));
                        }
                    });
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                            attractionGalleryItemBinding.btnPlayIcon.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_play));
                            return false;
                        }
                    });
                }
            });


            this.attractionGalleryItemBinding.btnGetDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gallery gallery = galleries.get(getAdapterPosition());

                    if (gallery.getLatitude().isEmpty() || gallery.getLongitude().isEmpty()) {
                        gallery.setLatitude(gallery.getAttraction().getLatitude());
                        gallery.setLongitude(gallery.getAttraction().getLongitude());
                    }

                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", gallery.getLatitude(), gallery.getLongitude(), "Location");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    try {
                        view.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        try {
                            Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            view.getContext().startActivity(unrestrictedIntent);
                        } catch (ActivityNotFoundException innerEx) {
                            Toast.makeText(view.getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

        private void stopPlaying() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                attractionGalleryItemBinding.btnPlayIcon.setImageDrawable(itemView.getContext().getDrawable(R.drawable.ic_play));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Gallery attractionGallery);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
