package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Book;
import com.cms.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book", id));
    }

    public List<Book> findByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> search(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public Book create(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN '" + book.getIsbn() + "' already exists");
        }
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    public Book update(Long id, Book bookData) {
        Book book = findById(id);
        book.setTitle(bookData.getTitle());
        book.setAuthor(bookData.getAuthor());
        book.setPublisher(bookData.getPublisher());
        book.setEdition(bookData.getEdition());
        book.setCategory(bookData.getCategory());
        book.setSubCategory(bookData.getSubCategory());
        book.setShelfLocation(bookData.getShelfLocation());
        book.setTotalCopies(bookData.getTotalCopies());
        book.setDescription(bookData.getDescription());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", id);
        }
        bookRepository.deleteById(id);
    }
}
