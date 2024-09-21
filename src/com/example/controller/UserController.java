package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.example.model.User;
import com.example.services.UserService;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController {

	@ManagedProperty("#{userService}")
	private UserService userService;

	private User user = new User();
	private List<User> users = new ArrayList<User>();
	private long userId;
	private String confirmPassword;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// actions
	public String login() {
		if(user.getEmail() != null && user.getPassword() != null) {
			User userResult = userService.login(user);
			if(userResult != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", userResult);
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Success", "Welcome!" + user.getEmail()));
				return "home?faces-redirect=true";
			}		
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Login Failure Try again!"));
		return null;
	}

	public String register() {
		if (user != null) {
			if (user.getPassword() != null && confirmPassword != null) {
				if (user.getPassword().equals(confirmPassword)) {
					userService.save(user);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Register Success", "Welcome!" + user.getEmail()));
					return "login?faces-redirect=true";
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register Failed", "Registration Failure Try again!"));
		return null;
	}

}
