package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.OrderModel;

public interface IOrderService {

	OrderModel placeOrder(String token, OrderDTO orderDTO);
	
	List<OrderModel> showUserAllOrders(String token);
	
	String cancelOrder(String token,int orderID);
	
	OrderModel changeMobileNo(String token,int orderId,String mobNo);
	
	OrderModel getOrderDetailsByOrderIdForUSer(String token,int orderId);
	
	OrderModel getOrderDetailsByOrderIdForAdmin(String token,int orderId);
	
	List<OrderModel> adminGetAllOrderDetails(String token);
	
}

