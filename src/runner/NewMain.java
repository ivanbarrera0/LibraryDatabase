package runner;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextArea;

import book.BookDao;
import book.BookDaoImpl;
import book.Book;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class NewMain {

	private JFrame frame;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JTextArea textArea;
	private JScrollPane sp;
	static BookDao bookDao;
	private JTextField textField;
	private JButton btnNewButton_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMain window = new NewMain();

					bookDao = new BookDaoImpl();

					try {
						bookDao.establishConnection();
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println("\nCould not connect with the Library Database");
					}

//					try {
//						bookDao.closeConnection();
//					} catch (SQLException e) {
//						System.out.println("Could not close connection properly");
//					}

					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 10, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Library Database");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblNewLabel.setBounds(309, 10, 486, 148);
		frame.getContentPane().add(lblNewLabel);

		textArea = new JTextArea();
		textArea.setBounds(309, 224, 397, 89);
		// frame.getContentPane().add(textArea);
		sp = new JScrollPane(textArea);

		sp.setBounds(309, 224, 397, 89);
		frame.getContentPane().add(sp);

		btnNewButton = new JButton("Show all Books");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";

				for (Book bk : bookDao.getAllBooks()) {
					str += bk + "\n";
				}

				textArea.setText(str);
			}
		});

		btnNewButton.setBounds(736, 261, 150, 21);
		frame.getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(308, 139, 398, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton_1 = new JButton("Get Book by ID");
		btnNewButton_1.setBounds(736, 137, 150, 21);
		frame.getContentPane().add(btnNewButton_1);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";

				try {

					int num = Integer.parseInt(textField.getText());

					Book book = null;

					Optional<Book> foundBook = bookDao.findBookById(num);

					if (foundBook.isEmpty()) {
						str = "There is not a book with that id";
					} else {
						book = foundBook.get();
						str = book.toString();
					}

				} catch (NumberFormatException numE) {
					str = "Input is not a valid number";
				}

				textArea.setText(str);
			}
		});

		btnNewButton_2 = new JButton("Delete Book by ID");
		btnNewButton_2.setBounds(736, 168, 150, 21);
		frame.getContentPane().add(btnNewButton_2);

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					int num = Integer.parseInt(textField.getText());
					boolean b = bookDao.delete(num);
					
					if(b) {
						textArea.setText("Book deleted successfully.");
					}
					else {
						textArea.setText("Book was not deleted successfully");
					}

				} catch (NumberFormatException numE) {
					textArea.setText("Input is not a valid number");
				}

			}
		});

		btnNewButton_3 = new JButton("Add Book");
		btnNewButton_3.setBounds(736, 230, 150, 21);
		frame.getContentPane().add(btnNewButton_3);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String newBook = textField.getText();

				try {

					String[] arr = newBook.split(",");
					
					String title = arr[0];
					String author = arr[1];
					String genre = arr[2];
					double price = Double.parseDouble(arr[3]);
					int pageCount = Integer.parseInt(arr[4]);
					
					Book addedBook = bookDao.addBook(new Book(title, author, genre, price, pageCount));
					
					textArea.setText("This is the newly added book" + "\n" + addedBook);

				} catch (NumberFormatException numE) {
					textArea.setText("Input is not valid");
				}

			}
		});
		
		btnNewButton_4 = new JButton("Update Book by ID");
		btnNewButton_4.setBounds(736, 199, 150, 21);
		frame.getContentPane().add(btnNewButton_4);
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String updatedBook = textField.getText();

				try {

					String[] arr = updatedBook.split(",");
					
					int id = Integer.parseInt(arr[0]);
					String title = arr[1];
					String author = arr[2];
					String genre = arr[3];
					double price = Double.parseDouble(arr[4]);
					int pageCount = Integer.parseInt(arr[5]);
					
					boolean b = bookDao.update(new Book(id, title, author, genre, price, pageCount));
					
					if(b) {
						textArea.setText("Book was updated successfully");
					}
					else {
						textArea.setText("Book was not updated");
					}

				} catch (NumberFormatException numE) {
					textArea.setText("Input is not valid");
				}

			}
		});
	}
}
