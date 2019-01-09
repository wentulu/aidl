// IBookManager.aidl
package cn.stu.cusview.ruiz.aidlservicedemo;

// Declare any non-default types here with import statements
import cn.stu.cusview.ruiz.aidlservicedemo.Book;

interface IBookManager {
    boolean addBook(in Book book);
    List<Book> getBookList();
}
