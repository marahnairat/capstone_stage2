package com.example.library.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Later_on")
public class Book  implements Parcelable {

    @PrimaryKey
    private int id;
    private String name;
    private String authorize;
    private String desc;
    private String cover_url;
    private String pdf_url;




    @Ignore
    public Book(){ }

    public Book(int id, String name, String authorize,String desc, String cover_url, String pdf_url ) {
        this.id = id;
        this.name = name;
        this.authorize = authorize;
        this.cover_url = cover_url;
        this.pdf_url = pdf_url;
        this.desc = desc;

    }

    @Ignore
    protected Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
        authorize = in.readString();
        cover_url = in.readString();
        pdf_url = in.readString();
        desc = in.readString();
    }
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.pdf_url = pdf_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(authorize);
        parcel.writeString(name);
        parcel.writeString(cover_url);
        parcel.writeString(pdf_url);
        parcel.writeString(desc);
    }
}