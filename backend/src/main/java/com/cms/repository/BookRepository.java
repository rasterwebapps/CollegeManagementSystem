package com.cms.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByCategory(String category);
    List<Book> findByTitleContainingIgnoreCase(String title);
    boolean existsByIsbn(String isbn);
}
