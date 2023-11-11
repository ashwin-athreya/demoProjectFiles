package com.cg.mts.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dto.LoginDTO;
import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.Login;
import com.cg.mts.entity.User;
import com.cg.mts.repoImpl.QueryClass;

@Service
public class LoginService {
	@Autowired
	private QueryClass qcp;
	
	@Autowired
    private ModelMapper modelMapper;

	private LoginDTO logData = new LoginDTO();

	public LoginDTO loginWithData(String username, String password) throws Exception {
		User user = qcp.findByUserName(username);
        if (!user.getPassword().equals(password))
            throw new Exception("Login Data Invalid");
        logData.setLoginStatus(true);
        logData.setUser(modelMapper.map(user, UserDTO.class));
        return logData;
	}

	public LoginDTO logoutPresentUser() {
		if (logData.isLoginStatus()) {
            logData.setLoginStatus(false);
        }
        return logData;
	}

	public boolean loginStatus() {
		return logData.isLoginStatus();
	}

	public String getRole() {
		return logData.getRole();
	}

}
