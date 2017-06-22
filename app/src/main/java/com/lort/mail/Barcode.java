package com.lort.mail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikita on 22.06.17.
 */

public class Barcode implements Parcelable {

    public String name = "ТОО Новелла";
    public String bar = "01213790622";
    public String address = "ул. Пролетариата, д. 27";
    public String contact = "Новиков Илья Владимирович";

    public Barcode() {
    }

    public Barcode(String name, String bar, String address, String contact) {
        this.name = name;
        this.bar = bar;
        this.address = address;
        this.contact = contact;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.bar);
        dest.writeString(this.address);
        dest.writeString(this.contact);
    }

    protected Barcode(Parcel in) {
        this.name = in.readString();
        this.bar = in.readString();
        this.address = in.readString();
        this.contact = in.readString();
    }

    public static final Parcelable.Creator<Barcode> CREATOR = new Parcelable.Creator<Barcode>() {
        @Override
        public Barcode createFromParcel(Parcel source) {
            return new Barcode(source);
        }

        @Override
        public Barcode[] newArray(int size) {
            return new Barcode[size];
        }
    };
}
