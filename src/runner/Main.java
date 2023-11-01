package runner;

import java.sql.SQLException;
import java.util.Optional;

import book.Book;
import book.BookDao;
import book.BookDaoImpl;

public class Main {

	public static void main(String[] args) {
		
		BookDao bookDao = new BookDaoImpl();
		
		try {
			bookDao.establishConnection();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("\nCould not connect with the Library Database");
		} 
		
		for(Book bk : bookDao.getAllBooks()) {
			System.out.println(bk);
		}
		
		Book book = null;
		
		Optional<Book> foundBook = bookDao.findBookById(3);
		
		if(foundBook.isEmpty()) {
			System.out.println("There is not a book with that id");
		} else {
			book = foundBook.get();
			System.out.println(book);
		}
		
		book.setTitle("New Title");
		book.setPrice(75.75);
		
		boolean b = bookDao.update(book);
		
		if(b) {
			System.out.println("Book was updated");
		} else {
			System.out.println("Book was not updated");
		}
		
		boolean c = bookDao.delete(3);
		
		if(c) {
			System.out.println("Book was deleted");
		} else {
			System.out.println("Book was not deleted");
		}
		
		System.out.println(bookDao.addBook(new Book("Book4", "Author3", "Fantasy", 45.50, 50)));
		
		System.out.println("---------------------------");
		
		for(Book bk : bookDao.getAllBooks()) {
			System.out.println(bk);
		}
		
		System.out.println("This is the average cost of books in the library: " + bookDao.averageBookPrice());
		
		// Remember to close the connection
		try {
			bookDao.closeConnection();
		} catch (SQLException e) {
			System.out.println("Could not close connection properly");
		}
	}
}
