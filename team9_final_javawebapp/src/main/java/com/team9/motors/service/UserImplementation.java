package com.team9.motors.service;

import com.team9.motors.interfacemethods.UserInterface;
import com.team9.motors.model.Role;
import com.team9.motors.model.User;
import com.team9.motors.repository.UserRepository;
import javassist.compiler.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserImplementation implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	// Create a user
	@Transactional
	public void createUser(User user) {
		userRepository.save(user);
	}

	// Read individual user
	@Transactional
	public User getUser(Integer id) {
		return userRepository.getOne(id);
	}

	// Update User
	@Transactional
	public void updateUser(User user) {
		userRepository.save(user);
	};

	// Delete User
	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	// Find by UserID
	@Transactional
	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	// Find by UserName
	@Transactional
	public User getUserByUsername(String name) {
		User user = userRepository.findByUsername(name);
		return user;
	}

	// Pagination
	public Page<User> findAll(int pageNumber, String sortField, String sortDir) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);

		return userRepository.findAll(pageable);
	}

	@Override
	public List<User> findAdmin() {
		return userRepository.findUsersByRole(Role.ADMIN);
	}

	@Override
	public String[] findAdminEmail() {
		List<User> uList = userRepository.findUsersByRole(Role.ADMIN);
		String[] mList = new String[uList.size()];
		int counter = 0;
		for (User user : uList) {
			mList[counter] = user.getEmail();
			counter++;
		}
		return mList;
	}

	
}
