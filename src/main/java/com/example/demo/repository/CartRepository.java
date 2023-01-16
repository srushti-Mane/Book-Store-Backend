package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CartModel;

@Repository
public interface CartRepository extends JpaRepository<CartModel,Integer>{

}
