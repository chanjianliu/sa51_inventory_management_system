package com.team9.motors.controller;

import com.team9.motors.model.User;
import com.team9.motors.service.UserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//userdetails as session model name
//user as attributename for employee
//Remember to use Controller not RESTController...

@RequestMapping("/admin")
@SessionAttributes("userdetails") // name of the model we need to store in the session
@Controller
public class AdminController {

	@Autowired
	private UserImplementation userImplementation;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Allow ADMIN to view their personal dashboard using httpSession
	@GetMapping("/dashboard")
	public String getDashboard(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "AdminDashboard";
	}

	// Implement Pagination for Admin to view ALL the users

	@RequestMapping(value = "/list")
	public String listusers(Model model) {

		return listByPage(model, 1,"id","asc");
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir) {
		Page<User> page = userImplementation.findAll(currentPage, sortField, sortDir);

		long total = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<User> list = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("total", total);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("list", list);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("users", userImplementation.findAll(currentPage, sortField, sortDir));
		return "ViewAllUsers";
	}

	// View individual user detail based on employee id
	@RequestMapping(value = "/showuser/{id}", method = RequestMethod.GET)
	public String showdetail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("user", userImplementation.findById(id));

		return "ShowUserDetail";
	}

	@GetMapping(value = "/adduser")
	public String getUserCreationForm(Model model) {
		model.addAttribute("user", new User());
		return "UserCreation";
	}

	@PostMapping(value = "/saveuser")
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Unsuccessful! Please try again.");
			return "UserCreation";
		}

		String pwd = user.getPassword();
		String encryptPwd = bCryptPasswordEncoder.encode(pwd);
		user.setPassword(encryptPwd);
		userImplementation.createUser(user);
		return "forward:/admin/list";
	}

	@PostMapping(value = "/updateuser")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "Unsuccessful! Please try again.");
			return "UserCreation";
		}

		userImplementation.updateUser(user);
		return "forward:/admin/list";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("user", userImplementation.findById(id));
		return "EditUserDetails";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteUser(Model model, @PathVariable("id") Integer id) {
		userImplementation.deleteUser(userImplementation.findById(id));
		return "forward:/admin/list";
	}

}
