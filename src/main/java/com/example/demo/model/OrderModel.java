package com.example.demo.model;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class OrderModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String pinCode;
	private String locality;
	private String address;
	private String city;
	private String landMark;
	private String AddressType;
	private LocalDate orderDate;
	private int orderQuantity;
	private double price;
	private int userId;
	private Boolean isCancel = false;

	@ManyToMany
	private List<BookModel> book;

}
