package com.hcl.summit.login.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hcl.summit.login.model.ResponseObject;
import com.hcl.summit.login.model.User;
import com.hcl.summit.login.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public @ResponseBody ResponseObject saveUser(@RequestBody User user) {
		System.out.println("Request entered saveUser endpoint of login controller");
		ResponseObject r = new ResponseObject();
		r.setObj(user);
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			r.setRespcode("F");
			r.setMessege("User Exists");
		} else {
			try {
				user = userService.saveUser(user);
				r.setRespcode("S");
				r.setMessege("User Registration successful");
				r.setObj(user);
			} catch (NoSuchAlgorithmException e) {
				r.setRespcode("F");
				r.setMessege("User Registration Failed.Please use different password.");
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Response " + r + " reverted from saveUser endpoint of login controller");
		return r;
	}

//	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
//	public ModelAndView home() {
//		ModelAndView modelAndView = new ModelAndView();
////		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findUserByEmail("");
//		modelAndView.addObject("userName",
//				"Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
//		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
//		modelAndView.setViewName("admin/home");
//		return modelAndView;
//	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public @ResponseBody ResponseObject authenticate(@RequestBody User user) {
		System.out.println("Request entered authenticate endpoint of login controller");
		ResponseObject r = new ResponseObject();
		try {
			if (userService.authenticateUser(user)) {
				r.setRespcode("S");
				r.setMessege("Authenticated User.");
				r.setObj(user);
			} else {
				r.setRespcode("F");
				r.setMessege("Not Authenticated User.");
				r.setObj(null);
			}
		} catch (Exception e) {
			r.setRespcode("ERR");
			r.setMessege("Error Occurred. Please come back Later");
			r.setObj(null);
			System.out.println("Exception occurred in user autrhentication");
		}
		System.out.println("Response " + r + " reverted from authenticate endpoint of login controller");
		return r;
	}
}
