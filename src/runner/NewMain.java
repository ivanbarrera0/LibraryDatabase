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
	private JTextArea textArea;
	private JScrollPane sp;
	static BookDao bookDao;
	private JTextField textField;

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
		//frame.getContentPane().add(textArea);
		sp = new JScrollPane(textArea);
		
		sp.setBounds(309, 224, 397, 89);
		frame.getContentPane().add(sp);
		
		btnNewButton = new JButton("Show all Books");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";
				
				for(Book bk : bookDao.getAllBooks()) {
					str += bk + "\n";
				}
				
				textArea.setText(str);
			}
		});
		
		btnNewButton.setBounds(736, 227, 127, 21);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(308, 139, 398, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton_1 = new JButton("Get Book by ID");
		btnNewButton_1.setBounds(736, 137, 127, 21);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";
				
				try { 
					
					int num = Integer.parseInt(textField.getText());
					
					Book book = null;
					
					Optional<Book> foundBook = bookDao.findBookById(num);
					
					if(foundBook.isEmpty()) {
						str = "There is not a book with that id";
					} else {
						book = foundBook.get();
						str = book.toString();
					}
					
					
				} catch(NumberFormatException numE) {
					str = "Input is not a valid number";
				}
				
				textArea.setText(str);
			}
		});
		
	}
}
