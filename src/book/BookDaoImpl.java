package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import connection.ConnectionManager;

public class BookDaoImpl implements BookDao {

	private Connection connection = null;

	@Override
	public void establishConnection() throws ClassNotFoundException, SQLException {

		if (connection == null) {
			connection = ConnectionManager.getConnection();
		}
	}

	@Override
	public void closeConnection() throws SQLException {

		connection.close();
	}

	@Override
	public List<Book> getAllBooks() {

		List<Book> listOfBooks = new ArrayList<>();

		try (Statement stmt = connection.createStatement()) {

			ResultSet rs = stmt.executeQuery("select * from book");

			while (rs.next()) {

				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("price");
				int pageCount = rs.getInt("pagecount");

				Book book = new Book(id, title, author, genre, price, pageCount);
				listOfBooks.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfBooks;
	}

	@Override
	public Optional<Book> findBookById(int id) {

		try (PreparedStatement pstmt = connection.prepareStatement("select * from book where book_id = ?")) {

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("price");
				int pageCount = rs.getInt("pagecount");

				rs.close();

				Book book = new Book(bookId, title, author, genre, price, pageCount);

				Optional<Book> bookFound = Optional.of(book);
				return bookFound;

			} else {

				rs.close();
				return Optional.empty();
			}

		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean update(Book book) {

		try (PreparedStatement pstmt = connection.prepareStatement(
				"update book set title = ?, author = ?, genre = ?, price = ?, pagecount = ? where book_id = ?")) {

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getGenre());
			pstmt.setDouble(4, book.getPrice());
			pstmt.setInt(5, book.getPageCount());
			pstmt.setInt(6, book.getId());

			int count = pstmt.executeUpdate();

			if (count > 0) {
				return true;
			}

		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	@Override
	public boolean delete(int id) {

		try (PreparedStatement pstmt = connection.prepareStatement("delete from book where book_id = ?")) {

			pstmt.setInt(1, id);

			int count = pstmt.executeUpdate();

			if (count > 0) {
				return true;
			}

		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	@Override
	public Book addBook(Book book) {

		try (PreparedStatement pstmt = connection.prepareStatement("insert into book(title, author, genre, price, pagecount) values(?, ?, ?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getGenre());
			pstmt.setDouble(4, book.getPrice());
			pstmt.setInt(5, book.getPageCount());

			int count = pstmt.executeUpdate();
			
			if(count > 0) {
				ResultSet rs = pstmt.getGeneratedKeys();
				
				if(rs.next()) {
					
					int id = (int) rs.getInt(1);
					book.setId(id);
					return book;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public double averageBookPrice() {
		
		double avg = 0;
		
		try(PreparedStatement pstmt = connection.prepareStatement("select AVG(price) from book")) {
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			avg = rs.getDouble(1);
			
		} catch(SQLException e) {
			return avg;
		}
		
		return avg;
	}

}
