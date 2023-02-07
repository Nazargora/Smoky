package com.hookahShop.HookahShop.controller;

import com.hookahShop.HookahShop.dto.UserDto;
import com.hookahShop.HookahShop.model.User;
import com.hookahShop.HookahShop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String singUpPage(final Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("user") final UserDto userDto) {
        userService.saveUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/user_info")
    public String getUserInfo(final Model model) {
        final long userId = userService.getUserId();
        final User user = userService.getUserById(userId);
        final UserDto userDto = UserDto.toDto(user);

        model.addAttribute("user", userDto);
        return "user_info";
    }


}
