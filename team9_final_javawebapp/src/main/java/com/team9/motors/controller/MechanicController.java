package com.team9.motors.controller;

import com.team9.motors.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@RequestMapping("/mechanic")
@SessionAttributes("userdetails") //name of the model we need to store in the session
@Controller
public class MechanicController {

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("userdetails");
        model.addAttribute("user", user);
        return "MechanicDashboard";
    }

    //not impacting Users as mechanics do not need to maintain CRUD for users
    //autowire to the product service to read the products
    //autowire to inventory to CRUD inventory

}
