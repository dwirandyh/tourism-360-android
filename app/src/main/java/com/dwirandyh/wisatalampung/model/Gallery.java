package com.dwirandyh.wisatalampung.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Gallery implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("directionFile")
    @Expose
    private String directionFile;
    @SerializedName("overviewFile")
    @Expose
    private String overviewFile;
    @SerializedName("touristAttractionId")
    @Expose
    private Integer touristAttractionId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("attraction")
    @Expose
    private Attraction attraction;



    public final static Parcelable.Creator<Gallery> CREATOR = new Creator<Gallery>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Gallery createFromParcel(Parcel in) {
            return new Gallery(in);
        }

        public Gallery[] newArray(int size) {
            return (new Gallery[size]);
        }

    };

    protected Gallery(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.touristAttractionId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.attraction = ((Attraction) in.readValue((Attraction.class.getClassLoader())));
    }

    public Gallery() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getTouristAttractionId() {
        return touristAttractionId;
    }

    public void setTouristAttractionId(Integer touristAttractionId) {
        this.touristAttractionId = touristAttractionId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDirectionFile() {
        return directionFile;
    }

    public void setDirectionFile(String directionFile) {
        this.directionFile = directionFile;
    }

    public String getOverviewFile() {
        return overviewFile;
    }

    public void setOverviewFile(String overviewFile) {
        this.overviewFile = overviewFile;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(thumbnail);
        dest.writeValue(touristAttractionId);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(attraction);
    }

    public int describeContents() {
        return 0;
    }

}