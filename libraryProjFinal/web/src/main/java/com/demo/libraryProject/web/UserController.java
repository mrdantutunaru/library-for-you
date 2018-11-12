package com.demo.libraryProject.web;

import com.demo.libraryProject.domain.User;
import com.demo.libraryProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/users/edit/{id}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("user", userService.findOne(id));
        return "editUser";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(path = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(Model model, @PathVariable(required = true, name = "id") int id) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

}
