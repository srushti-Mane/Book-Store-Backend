package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Response;
import com.example.demo.dto.OrderDTO;
import com.example.demo.model.OrderModel;
import com.example.demo.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	IOrderService iorderService;
	
	
    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@RequestHeader String token, @RequestBody OrderDTO orderDTO) {
        OrderModel orderModel = iorderService.placeOrder(token, orderDTO);
        Response response = new Response( "Order Placed Successfully",orderModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   
    @GetMapping("/Show_All_Orders/User")
    public ResponseEntity<Response> showUserOrders(@RequestHeader String token) {
        List<OrderModel> orders = iorderService.showUserAllOrders(token);
        Response response = new Response( "Record found successfully",orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @GetMapping("/get_Order_Details_by_OrderId/User")
    public ResponseEntity<Response> getOrderDetailsByOrderIdForUser(@RequestHeader String token, @RequestParam int orderId) {
        OrderModel orderDetails = iorderService.getOrderDetailsByOrderIdForUSer(token, orderId);
        Response response = new Response( "Order Details Found for Order ID:" + orderId,orderDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PutMapping("/Change_Order_PhoneNo")
    public ResponseEntity<Response> changeOrderAddressOrPhoneNo(@RequestHeader String token, @RequestParam int orderId, @RequestParam String phoneNo) {
        OrderModel updateOrder = iorderService.changeMobileNo(token, orderId, phoneNo);
        Response response = new Response( "Order Phone Number Change Successfully",updateOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PutMapping("/Cancel_Order")
    public ResponseEntity<Response> cancelOrder(@RequestHeader String token, @RequestParam int orderId) {
        iorderService.cancelOrder(token, orderId);
        Response response = new Response("Cancel order for Order id: " + orderId, "You've successfully cancelled your order");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @GetMapping("/Show_All_Orders/Admin")
    public ResponseEntity<Response> GetAllOrdersDataAsAdmin(@RequestHeader String token) {
        List<OrderModel> allOrders = iorderService.adminGetAllOrderDetails(token);
        Response response = new Response( "Record found successfully",allOrders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @GetMapping("/get_Order_Details_by_OrderId/Admin")
    public ResponseEntity<Response> getOrderDetailsByOrderIdForAdmin(@RequestHeader String token, @RequestParam int orderId) {
        OrderModel orderDetails = iorderService.getOrderDetailsByOrderIdForAdmin(token, orderId);
        Response response = new Response( "Order Details Found for Order ID:" + orderId,orderDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
