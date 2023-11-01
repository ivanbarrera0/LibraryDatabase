package book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookDao {
	
	public void establishConnection() throws ClassNotFoundException, SQLException;
	
	public void closeConnection() throws SQLException;
	
	public List<Book> getAllBooks();
	
	public Optional<Book> findBookById(int id);
	
	public boolean update(Book book);
	
	public boolean delete(int id);
	
	public Book addBook(Book book);
	
	public double averageBookPrice();
}
