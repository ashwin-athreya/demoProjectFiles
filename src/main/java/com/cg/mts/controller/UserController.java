package com.cg.mts.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.exception.CustomerNotFoundException;
import com.cg.mts.dto.CustomerDTO;
import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.Customer;
import com.cg.mts.exception.AccessForbiddenException;
import com.cg.mts.exception.UserCreationError;
import com.cg.mts.repository.CustomerRepository;
import com.cg.mts.service.IUserService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@Api(value = "Swagger2DemoRestController")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	IUserService userService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
    private ModelMapper modelMapper;


	@PostMapping("/adduser")
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO)
			throws AccessForbiddenException, CustomerNotFoundException, UserCreationError {
		
		if (userDTO.getRole().equalsIgnoreCase("CUSTOMER")) {
			Customer customer = new Customer(userDTO.getUsername(), null, null, null, userDTO.getPassword());
			logger.info("-----------------Customer Added---------------------");
			customerRepository.save(customer);
			userDTO.setCustomer(modelMapper.map(customer, CustomerDTO.class));
		}
		logger.info("-----------------User Added---------------------");
		UserDTO savedUser = userService.addUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	
	@DeleteMapping("/removeuser")
	public UserDTO removeUser(@RequestBody UserDTO userDTO) throws AccessForbiddenException {
		
		logger.info("--------------------User Deleted------------------");
		return userService.removeUser(userDTO);
	}
}