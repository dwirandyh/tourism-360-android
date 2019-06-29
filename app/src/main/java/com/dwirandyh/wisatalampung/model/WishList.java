package com.dwirandyh.wisatalampung.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.dwirandyh.wisatalampung.BR;

@Entity(tableName = "wish_list")
public class WishList extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "attraction_id")
    private int attractionId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "short_description")
    private String shortDescription;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @Ignore
    public WishList() {

    }

    public WishList(int id, int attractionId, String name, String shortDescription, String address, String thumbnail) {
        this.id = id;
        this.attractionId = attractionId;
        this.name = name;
        this.shortDescription = shortDescription;
        this.address = address;
        this.thumbnail = thumbnail;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
        notifyPropertyChanged(BR.attractionId);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        notifyPropertyChanged(BR.shortDescription);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        notifyPropertyChanged(BR.thumbnail);
    }
}
