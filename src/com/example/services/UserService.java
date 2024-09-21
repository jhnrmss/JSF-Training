package com.example.services;


import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.example.dto.UserDto;
import com.example.entities.UserEntity;
import com.example.model.User;
import com.example.util.PasswordUtil;

@ManagedBean(name="userService")
@ApplicationScoped
public class UserService {

	private UserDto userDto;

	public UserService() {
		userDto = new UserDto();
	}
	
	public void save(User user) {
		try {
			PasswordUtil utils = new PasswordUtil();
			byte[] salt = utils.generateSalt();
			String hashedPassword = utils.hashPassword(user.getPassword(), salt);
			
			if(user!=null) {
				UserEntity userEntity = new UserEntity();
				userEntity.setName(user.getName());
				userEntity.setEmail(user.getEmail());
				userEntity.setPassword(hashedPassword);
				userEntity.setSalt(salt);
				userDto.save(userEntity);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	public User login(User user) {
		try {
			PasswordUtil utils = new PasswordUtil();
			if(user.getEmail()!=null && user.getPassword() != null) {
				UserEntity userResult = userDto.findByEmailId(user.getEmail());
				if(userResult != null) {
					boolean isExists = utils.verifyPassword(user.getPassword(), userResult.getPassword(), userResult.getSalt());
					if(isExists) {
						return new User(
								userResult.getId(),
								userResult.getName(),
								userResult.getEmail());						
					}	
				}
			}
			return null;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
	
	}
	
	

}
