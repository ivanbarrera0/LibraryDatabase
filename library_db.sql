drop database if exists library_db;
create database library_db;

use library_db;

CREATE TABLE book(

	book_id INT PRIMARY KEY
		AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL, 
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    price DECIMAL(5,2) NOT NULL,
    pagecount INT NOT NULL
);

insert into book(title, author, genre, price, pagecount)
	values('To Kill a Mockingbird', 'Harper Lee', 'Fiction', 20, 281);
    
insert into book(title, author, genre, price, pagecount)
	values('1984', 'George Orwell', 'Dystopian Fiction', 15, 328);
    
insert into book(title, author, genre, price, pagecount)
	values('Pride and Prejudice', 'Jane Austen', 'Classic Literature', 12, 279);
