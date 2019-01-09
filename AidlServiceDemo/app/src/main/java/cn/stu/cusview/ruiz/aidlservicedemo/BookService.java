package cn.stu.cusview.ruiz.aidlservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {

    private ArrayList<Book> bookList = new ArrayList<>();

    Binder mBinder = new IBookManager.Stub() {
        @Override
        public boolean addBook(Book book) throws RemoteException {
            bookList.add(book);
            return false;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book("JAVA Rumen","Liu",123.0d));
        bookList.add(new Book("JAVA Rumen2","Liu2",124.0d));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
