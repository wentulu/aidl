package cn.stu.cusview.ruiz.aidlservicedemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{


    private String bookName;
    private String auther;
    private double price;

    public Book(String bookName, String auther, double price) {
        this.bookName = bookName;
        this.auther = auther;
        this.price = price;
    }


    @Override
    public String toString() {
        return "BookName:"+bookName+" Author"+auther;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(auther);
        dest.writeDouble(price);
    }


    @Override
    public int describeContents() {
        return 0;
    }


    public static Creator<Book> CREATOR  =  new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    private Book(Parcel in){
        bookName = in.readString();
        auther = in.readString();
        price = in.readDouble();
    }

}
