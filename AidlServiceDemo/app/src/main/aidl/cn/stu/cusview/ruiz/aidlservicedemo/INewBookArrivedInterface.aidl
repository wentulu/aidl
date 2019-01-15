// INewBookArrivedInterface.aidl
package cn.stu.cusview.ruiz.aidlservicedemo;

// Declare any non-default types here with import statements

import cn.stu.cusview.ruiz.aidlservicedemo.Book;

interface INewBookArrivedInterface {
    void onNewBookArrive(in Book book);
}
