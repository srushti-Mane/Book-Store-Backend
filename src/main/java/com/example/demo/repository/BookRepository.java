package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer> {
    List<BookModel> findByBookName(String bookName);
    List<BookModel> findAllByAuthorName(String authorName);

	
}