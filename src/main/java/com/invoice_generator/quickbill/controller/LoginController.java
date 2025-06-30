package com.invoice_generator.quickbill.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.invoice_generator.quickbill.entity.User;
// import com.invoice_generator.quickbill.services.UserService;


@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    // private UserService userService;

    // private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This returns login.html from templates
    }

    @GetMapping("/dashboard")
    public String defaultAfterLogin() {
        // Redirect to dashboard or home after login, can be role-based
        return "dashboard";
    }
    // @PostMapping("/login")
    // public String processLogin(@ModelAttribute @RequestParam String contact, @RequestParam String password){
    //     logger.info("APi received "+contact);
    //     userService.loadUserByUsername(contact);
    //     return "redirect:/dashboard?loginSuccess";
    // }
    
}