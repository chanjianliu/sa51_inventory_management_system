package com.team9.motors.controller;

import com.team9.motors.model.LoginModel;
import com.team9.motors.model.Role;
import com.team9.motors.model.User;
import com.team9.motors.service.UserImplementation;
import com.team9.motors.springSecurity.MainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userdetails")
public class SessionsController {

    @Autowired
    private UserImplementation userImplementation;

    @RequestMapping("/index")
    public String getIndexPage(Model model, SessionStatus sessionStatus) {
        return "Index";
    }

    @RequestMapping("/login")
    public String login(Model model, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        model.addAttribute("user", new LoginModel());
        return "Login";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();

        return "Index";
    }

    @GetMapping("/error")
    public String forbidden() {
        return "forbidden";
    }



    @RequestMapping("/")
    public String getdashboard(@AuthenticationPrincipal MainUser mainUser, HttpServletRequest request, Model model, HttpSession httpSession) {

        if(mainUser != null) {

            User user = userImplementation.getUserByUsername(mainUser.getUsername());

            httpSession.setAttribute("userdetails", user);
            if (user.getRole() == Role.ADMIN) {
                return "redirect:admin/dashboard";
            } else if (user.getRole() == Role.MECHANIC) {
                return "redirect:mechanic/dashboard";
            }
        }
        return "redirect:/index";

}

}

