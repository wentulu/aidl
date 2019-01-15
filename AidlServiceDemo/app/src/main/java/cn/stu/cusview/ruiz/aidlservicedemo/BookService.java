package cn.stu.cusview.ruiz.aidlservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookService extends Service {

    private static final String TAG = "BookService";

    private AtomicBoolean mServiceIsDestroy = new AtomicBoolean(false);

    private ArrayList<Book> bookList = new ArrayList<>();

    private ArrayList<INewBookArrivedInterface> listeners = new ArrayList<>();

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

        @Override
        public void registerNewBookListener(INewBookArrivedInterface listener) {
            if (listeners.contains(listener)) {

            } else {
                listeners.add(listener);
            }
        }

        @Override
        public void unregisterNewBookListener(INewBookArrivedInterface listener) {
            if (listeners.containsAll(listeners))
                listeners.remove(listener);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book("JAVA Rumen", "Liu", 123.0d));
        bookList.add(new Book("JAVA Rumen2", "Liu2", 124.0d));
        new Thread(addBookTask).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listeners.clear();
        mServiceIsDestroy.set(true);
    }


    Runnable addBookTask = new Runnable() {
        @Override
        public void run() {
            int index = 0;
            while (!mServiceIsDestroy.get()){
                Book book = new Book("bookName"+index,"Author"+index,index);
                bookList.add(book);
                for (INewBookArrivedInterface l :listeners){
                    try {
                        l.onNewBookArrive(book);
                    } catch (RemoteException e) {
                        Log.e(TAG,l.toString()+" already disconnected!");
                        listeners.remove(l);
                    }
                }
                try {
                    Thread.sleep(10000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    index++;
                }
            }

        }
    };


}
