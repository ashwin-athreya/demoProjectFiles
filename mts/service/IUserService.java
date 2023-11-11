package com.cg.mts.service;

import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.User;
import com.cg.mts.exception.UserCreationError;

public interface IUserService {

	public UserDTO addUser(UserDTO userDTO) throws UserCreationError;

	public UserDTO removeUser(UserDTO userDTO);
}
