package book;

public class Book {
	
	private int id;
	private String title;
	private String author;
	private String genre;
	private double price;
	private int pageCount;
	
	public Book(int id, String title, String author, String genre, double price, int pageCount) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.price = price;
		this.pageCount = pageCount;
	}
	
	public Book(String title, String author, String genre, double price, int pageCount) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.price = price;
		this.pageCount = pageCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre + ", price=" + price
				+ ", pageCount=" + pageCount + "]";
	}
}
