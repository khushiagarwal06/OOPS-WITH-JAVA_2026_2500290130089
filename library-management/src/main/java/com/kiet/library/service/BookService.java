package com.kiet.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kiet.library.model.Book;

@Service
public class BookService {

    
    private final List<Book> books = new ArrayList<>();

    public BookService() {
        books.add(new Book(201, "Effective Java", "Joshua Bloch", "Programming", 2018, true));
        books.add(new Book(202, "Head First Java", "Kathy Sierra", "Programming", 2005, true));
        books.add(new Book(203, "Wings of Fire", "A.P.J. Abdul Kalam", "Autobiography", 1999, false));
        books.add(new Book(204, "A Brief History of Time", "Stephen Hawking", "Science", 1988, true));
        books.add(new Book(205, "Introduction to Algorithms", "Thomas H. Cormen", "Computer Science", 2022, true));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    
    public Book getBookById(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    // returns null if a book with the same ID already exists
    public Book addBook(Book book) {
        if (getBookById(book.getBookId()) != null) {
            return null;
        }
        books.add(book);
        return book;
    }

    // returns null if the book does not exist
    public Book updateBook(int bookId, Book updatedBook) {
        Book existing = getBookById(bookId);
        if (existing == null) {
            return null;
        }
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setCategory(updatedBook.getCategory());
        existing.setPublicationYear(updatedBook.getPublicationYear());
        existing.setAvailable(updatedBook.isAvailable());
        return existing;
    }

    // returns true if deleted, false if not found
    public boolean deleteBook(int bookId) {
        Book existing = getBookById(bookId);
        if (existing == null) {
            return false;
        }
        books.remove(existing);
        return true;
    }

    public List<Book> searchByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory() != null && book.getCategory().equalsIgnoreCase(category)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findPublishedFromYear(int fromYear) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getPublicationYear() >= fromYear) {
                result.add(book);
            }
        }
        return result;
    }
}
