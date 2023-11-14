package com.cg.mts.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.Customer;
import com.cg.mts.entity.User;
import com.cg.mts.exception.UserCreationError;
import com.cg.mts.repoImpl.QueryClass;
import com.cg.mts.repository.CustomerRepository;
import com.cg.mts.repository.UserRepository;
import com.cg.mts.validator.InputValidator;

@Service
public class IUserServiceImpl implements IUserService {

	@Autowired
	UserRepository userrepo;

	@Autowired
	InputValidator validate;

	@Autowired
	QueryClass qcp;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CustomerRepository custrepo;

	@Override
	public UserDTO addUser(UserDTO userDTO) throws UserCreationError {
		User user = modelMapper.map(userDTO, User.class);

        if (!validate.usernameValidator(user.getUsername()))
            throw new UserCreationError("Check Username !!!!");
        if (!validate.passwordValidator(user.getPassword()))
            throw new UserCreationError("Cannot register this User with this password");

        User savedUser = userrepo.saveAndFlush(user);
        return modelMapper.map(savedUser, UserDTO.class);
	}

	@Override
	public UserDTO removeUser(UserDTO userDTO) {
		 User user = modelMapper.map(userDTO, User.class);
	        userrepo.delete(user);
	        if(userDTO.getCustomer()!=null) {
	        	custrepo.delete(modelMapper.map(userDTO.getCustomer(), Customer.class));
	        }
	        return userDTO;
	}

}
