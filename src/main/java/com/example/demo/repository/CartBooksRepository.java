package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartBooksData;

@Repository
public interface CartBooksRepository extends JpaRepository<CartBooksData, Integer> {

    @Query(value="SELECT * FROM  boooksstore.cart-books_data where cart_id = :cartId and book_id= :bookId", nativeQuery=true)
    CartBooksData findByCartIdAndBookId(int cartId, int bookId);

    List<CartBooksData> findByCart_CartId(int cartId);

}
