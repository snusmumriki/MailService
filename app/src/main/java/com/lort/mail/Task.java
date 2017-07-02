package com.lort.mail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.noodle.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikita on 19.06.17.
 */

public class Task implements Parcelable {

    @Id
    long id;
    @Expose
    public String name = "ТОО Тамур";
    @Expose
    private String address = "ул. Абая, д. 2, ВП-2";
    @Expose
    private String time = "19:54";
    @Expose
    private String status = "wait";
    @Expose
    private String phone = "+7 (909) 777-77-77";
    @Expose
    private String contact = "Иванов Василий Иванович";

    private List<Form> forms;

    public Task() {

    }

    public Task(String name, String address, String time, String status, String phone, String contact) {
        this.name = name;
        this.address = address;
        this.time = time;
        this.status = status;
    }

    public Task(String name, String address, String time, String status, String phone, String contact, List<Form> forms) {
        this.name = name;
        this.address = address;
        this.time = time;
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.time);
        dest.writeString(this.status);
        dest.writeList(this.forms);
        dest.writeString(this.phone);
        dest.writeString(this.contact);
    }

    protected Task(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.time = in.readString();
        this.status = in.readString();
        this.forms = new ArrayList<>();
        in.readList(this.forms, Form.class.getClassLoader());
        this.phone = in.readString();
        this.contact = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
