package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Response;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UpdateDTO;
import com.example.demo.model.UserModel;
import com.example.demo.service.IuserService;
@RestController
@RequestMapping("/user")
public class UserController {

	 @Autowired
	    IuserService iuserService;

	  
	    @PostMapping("/Register_New_User")
	    public ResponseEntity<Response> registerNewUser(@RequestBody RegisterDTO registerDTO) {
	        iuserService.registerNewUser(registerDTO);
	        Response response = new Response( "User Registered Successful",registerDTO);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }


	    @PostMapping("/Login")
	    public ResponseEntity<Response> loginPage(@RequestBody LoginDTO loginDTO) {
	        String token = iuserService.login(loginDTO);
	        Response response = new Response(token, "Login Successful");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @PutMapping("/Logout")
	    public ResponseEntity<Response> logOutUser(@RequestHeader String token) {
	        iuserService.logout(token);
	        Response response = new Response("User Logout", "SuccessFully Logout");
	        return  new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @PutMapping("/Forgot_Password")
	    public ResponseEntity<Response> forgotPassword(@RequestHeader String token, @RequestParam String password) {
	        iuserService.forgotPassword(token, password);
	        Response response = new Response("Please Login again with new password", "Password changed Successful");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @DeleteMapping("/DeleteUser/user")
	    public ResponseEntity<Response> delete(@RequestHeader String token) {
	        iuserService.delete(token);
	        Response response = new Response("Deleted for User: ", "Deleted Successfully");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	
	    @DeleteMapping("/DeleteUser/Admin")
	    public ResponseEntity<Response> deleteUserAsAdmin(@RequestHeader String token, @RequestParam int id) {
	        iuserService.deleteUserAsAdmin(token, id);
	        Response response = new Response("Deleted for User: " +id, "Deleted Successfully");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @GetMapping("/Get_Data/user")
	    public ResponseEntity<Response> getUserData(@RequestHeader String token) {
	        UserModel getUserData = iuserService.getUserData(token);
	        Response response = new Response( "User Data",getUserData);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	   

	    @PutMapping("/UpdateData/user")
	    public ResponseEntity<Response> updateData(@RequestBody UpdateDTO updateDTO, @RequestHeader String token)  {
	        UserModel update = iuserService.update(updateDTO, token);
	        Response response = new Response( "User Updated Successfully",update);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }



	    @GetMapping("/Show_All_User/Admin")
	    public ResponseEntity<Response> getAllUser(@RequestHeader String token){
	        List<UserModel> userModelList = iuserService.showAllUsers(token);
	        Response response = new Response( "All users Data" ,userModelList);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

}
