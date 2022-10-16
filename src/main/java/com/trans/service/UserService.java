package com.trans.service;


import com.trans.dto.UserDTO;
import com.trans.model.User;

import java.util.List;

public interface UserService {
  UserDTO findByEmailAndPassword(String e, String p);
  int save(User user);
  int save(UserDTO user);
  void deleteById(int id);
  void update(int id);
  boolean update(UserDTO userDTO);
  boolean update(UserDTO userDTO,String password);
  UserDTO findDTOById(int id);
  User findById(int id);
  List<UserDTO> getAll();
  User findByEmail(String email);
  UserDTO findByNumber(String number);
  UserDTO findByEmailOrNumber(String email, String number);
}
