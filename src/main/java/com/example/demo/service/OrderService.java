package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.exception.BookStoreException;
import com.example.demo.model.BookModel;
import com.example.demo.model.CartBooksData;
import com.example.demo.model.OrderModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartBooksRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.EmailService;
import com.example.demo.utility.JwtUtils;

@Service
public class OrderService implements IOrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CartBooksRepository cartBooksRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailService emailService;

	@Override
	public OrderModel placeOrder(String token, OrderDTO orderDTO) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
		UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
		if (userRepository.findByEmail(user.getEmail()).isLogin()) {
			List<CartBooksData> cart = cartBooksRepository.findByCart_CartId(user.getCartModel().getCartId());
			List<BookModel> orderedBooks = new ArrayList<>();

			double totalOrderPrice = 0;
			int totalOrderQty = 0;

			for (int i = 0; i < cart.size(); i++) {
				totalOrderPrice = totalOrderPrice + cart.get(i).getTotalPrice();
				totalOrderQty = totalOrderQty + cart.get(i).getQuantity();
				orderedBooks.add(cart.get(i).getBooks());
			}

			// for new Address
			OrderModel orderModel = modelMapper.map(orderDTO, OrderModel.class);

			orderModel.setUserId(user.getId());
			orderModel.setBook(orderedBooks);
			orderModel.setOrderQuantity(totalOrderQty);
			orderModel.setPrice(totalOrderPrice);

			// for getting default address
			if (orderModel.getFirstName() == null) {
				orderModel.setFirstName(user.getFirstName());
			}
			if (orderModel.getLastName() == null) {
				orderModel.setLastName(user.getLastName());
			}
			if (orderModel.getPhoneNo() == null) {
				orderModel.setPhoneNo(user.getPhoneNo());
			}
			if (orderModel.getPinCode() == null) {
				orderModel.setPinCode(user.getPinCode());
			}
			if (orderModel.getLocality() == null) {
				orderModel.setLocality(user.getLocality());
			}
			if (orderModel.getAddress() == null) {
				orderModel.setAddress(user.getAddress());
			}
			if (orderModel.getCity() == null) {
				orderModel.setCity(user.getCity());
			}
			if (orderModel.getLandMark() == null) {
				orderModel.setLandMark(user.getLandMark());
			}
			if (orderModel.getAddressType() == null) {
				orderModel.setAddressType(user.getAddressType());
			}

			orderRepository.save(orderModel);

			/*
			 * // sending order confirmation mail to user
			 * emailService.sendMail(user.getEmail(), "Hi " + user.getFirstName() + " " +
			 * user.getLastName() + "," + "\n\nYour Order Placed Successful" +
			 * "\nOrder Id: " + orderModel.getOrderId() +
			 * "\nThe Order will delivered to you with in two days." +
			 * "\n\nWe have attached your invoice should you need it in the future." +
			 * "\n\n\nThank you for shopping!" + "\nBookStore");
			 */

			for (int i = 0; i < cart.size(); i++) {
				BookModel book = cart.get(i).getBooks();
				int updatedQty = updateBookQty(book.getBookQuantity(), cart.get(i).getQuantity());
				book.setBookQuantity(updatedQty);
				bookRepository.save(book);

				// delete from cart
				cartBooksRepository.deleteById(cart.get(i).getCartBookId());
			}
			return orderModel;
		}
		throw new BookStoreException("Please sign in your account");
	}

	private int updateBookQty(int bookQty, int bookQtyInCart) {
		int updatedQty = bookQty - bookQtyInCart;
		if (updatedQty <= 0) {
			return 0;
		} else {
			return updatedQty;
		}
	}

	@Override
	public List<OrderModel> showUserAllOrders(String token) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            List<OrderModel> userOrders = orderRepository.findAllByUserId(user.getId());
            if (userOrders.isEmpty()) {
                throw new BookStoreException("Empty Order List");
            }
            return userOrders;
        }
        throw new BookStoreException("Please sign in your account");
    }


	@Override
	public String cancelOrder(String token, int orderID) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (orderRepository.findById(orderID).isPresent()) {
                OrderModel order = orderRepository.findById(orderID).get();
                if (order.getUserId() == user.getId()) {
                    if (order.getIsCancel() == false) {
                        order.setIsCancel(true);
                        orderRepository.save(order);
                        emailService.sendMail(user.getEmail(),"Hi " + user.getFirstName() + " " + user.getLastName()+ ","+
                                "\n\nYou've successfully cancelled your order " +
                                "\n\nAt your request, we canceled your order.Here's" +
                                "\nyour Order info:" +
                                "\n\nOrder ID: "+orderID);
                        return "Order Cancel Successful!";
                    }
                    throw new BookStoreException("Order is already canceled!");
                }
                throw new BookStoreException("""
                        Invalid Order ID
                        please Enter only You Order ID
                        You can Cancel only your Orders""");
            }
            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
        }
        throw new BookStoreException("Please sign in your account");
	}

	@Override
	public OrderModel changeMobileNo(String token, int orderId, String mobNo) {
		 LoginDTO loginDTO = jwtUtils.decodeToken(token);
	        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
	        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
	            if (orderRepository.findById(orderId).isPresent()) {
	                OrderModel order = orderRepository.findById(orderId).get();
	                if (order.getUserId() == user.getId()) {
	                    order.setPhoneNo(mobNo);
	                    orderRepository.save(order);
	                    return order;
	                }
	                throw new BookStoreException("please Enter only You Order ID");
	            }
	            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
	        }
	        throw new BookStoreException("Please sign in your account");
	}

	@Override
	public OrderModel getOrderDetailsByOrderIdForUSer(String token, int orderId) {
		 LoginDTO loginDTO = jwtUtils.decodeToken(token);
	        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
	        if (userRepository.findByEmail(user.getEmail()).isLogin()) {
	            if (orderRepository.findById(orderId).isPresent()) {
	                OrderModel order = orderRepository.findById(orderId).get();
	                if (order.getUserId() == user.getId()) {
	                    return order;
	                }
	                throw new BookStoreException("""
	                        Invalid Order ID
	                        please Enter only You Order ID
	                        You can see only your Orders""");
	            }
	            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
	        }
	        throw new BookStoreException("Please sign in your account");
	}

	@Override
	public OrderModel getOrderDetailsByOrderIdForAdmin(String token, int orderId) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
            if (orderRepository.findById(orderId).isPresent()) {
                return orderRepository.findById(orderId).get();
            }
            throw new BookStoreException("Order Record Not Found" + "\nInvalid Order_Id");
        }
        throw new BookStoreException("Please sign in your account as Admin");
	}

	@Override
	public List<OrderModel> adminGetAllOrderDetails(String token) {
		LoginDTO loginDTO = jwtUtils.decodeToken(token);
        UserModel user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRepository.findByEmail(user.getEmail()).getRole().equals("Admin") && userRepository.findByEmail(user.getEmail()).isLogin()) {
            List<OrderModel> userOrders = orderRepository.findAll();
            if (userOrders.isEmpty()) {
                throw new BookStoreException("Empty Order List");
            }
            return userOrders;
        }
        throw new BookStoreException("Please sign in your account as Admin");
    }
	}
