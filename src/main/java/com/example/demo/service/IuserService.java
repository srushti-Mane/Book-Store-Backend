package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UpdateDTO;
import com.example.demo.model.UserModel;

public interface IuserService {

	String registerNewUser(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);
    String logout(String token);
    String forgotPassword(String token, String password);
    String delete(String token);
    String deleteUserAsAdmin(String token, int id);

    UserModel update(UpdateDTO updateDTO, String token);

    UserModel getUserData(String token);
    List<UserModel> showAllUsers(String token);

}

