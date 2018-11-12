//package com.demo.libraryProject.controller;
//
//import javax.annotation.security.RolesAllowed;
//import javax.validation.Valid;
//
//import com.demo.libraryProject.entities.User;
//import com.demo.libraryProject.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Optional;
//
//@Controller
//public class LoginController {
//
//    private static final int weak_Strength = 1;
//    private static final int fear_Strength = 5;
//    private static final int strong_Strength = 7;
//
//    @Autowired
//    private UserService userService;
//
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
//    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
//    public ModelAndView login(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }
//
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
//    @RequestMapping(value="/registration", method = RequestMethod.GET)
//    public ModelAndView registration(){
//        ModelAndView modelAndView = new ModelAndView();
//        User user = new User();
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("registration");
//        return modelAndView;
//    }
//
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        Optional<User> userExists = userService.findUserByEmail(user.getEmail());
//        if (userExists != null) {
//            bindingResult
//                    .rejectValue("email", "error.user",
//                            "There is already a user registered with the email provided");
//        }
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("registration");
//        } else {
//            userService.saveUser(user);
//            modelAndView.addObject("successMessage", "User has been registered successfully");
//            modelAndView.addObject("user", new User());
//            modelAndView.setViewName("registration");
//
//        }
//        return modelAndView;
//    }
//
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
//    @RequestMapping(value="/admin/index", method = RequestMethod.GET)
//    public ModelAndView home(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("admin/index");
//        return modelAndView;
//    }
//
//    @RequestMapping(value="/checkStrength", method = RequestMethod.GET)
//    public @ResponseBody
//    String checkStrength(@RequestParam String password){
//        if (password.length() >=weak_Strength & password.length() < fear_Strength){
//            return "Weak";
//        } else if (password.length() >=fear_Strength & password.length() < strong_Strength){
//            return "Fear";
//        } else if (password.length() >=strong_Strength){
//            return "Strong";
//        }
//        return "";
//    }
//
//   }
//
