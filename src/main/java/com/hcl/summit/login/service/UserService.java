package com.hcl.summit.login.service;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.summit.login.model.Role;
import com.hcl.summit.login.model.User;
import com.hcl.summit.login.repository.RoleRepository;
import com.hcl.summit.login.repository.UserRepository;
import com.hcl.summit.login.utils.EncryptWithMD5;

@Service("userService")
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User saveUser(User user) throws NoSuchAlgorithmException {
		user.setPassword(EncryptWithMD5.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

	public boolean authenticateUser(User userInArgument) throws NoSuchAlgorithmException {
		boolean result = false;
		userInArgument.setPassword(EncryptWithMD5.encode(userInArgument.getPassword()));
		User userFromBackend = findUserByEmail(userInArgument.getEmail());
		if (userFromBackend!=null && StringUtils.equals(userInArgument.getPassword(), userFromBackend.getPassword())) {
			result = true;
		}
		return result;
	}

}