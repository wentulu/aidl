package cn.stu.cusview.ruiz.aidlservicedemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    IBookManager iBookManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService(new Intent(this,BookService.class),mServiceConnection, Service.BIND_AUTO_CREATE);
    }


    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            if (iBookManager!=null){
                try {
                    List<Book> books = iBookManager.getBookList();
                    Log.e(TAG,"Books have "+books.toString());

                    iBookManager.registerNewBookListener(mINewBookArrivedInterface);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            iBookManager=null;
        }
    };

    INewBookArrivedInterface.Stub mINewBookArrivedInterface = new INewBookArrivedInterface.Stub() {
        @Override
        public void onNewBookArrive(Book book) throws RemoteException {
            Log.e(TAG,"New Book Arrived :"+book.toString());
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iBookManager!=null && iBookManager.asBinder().isBinderAlive()){
            try {
                iBookManager.unregisterNewBookListener(mINewBookArrivedInterface);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
    }
}
