package com.example.demo.controller;

import com.example.demo.dto.RegisterForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") RegisterForm form, Model model, RedirectAttributes ra) {
        String error = validate(form);
        if (error != null) {
            model.addAttribute("error", error);
            return "register";
        }
        User u = new User();
        u.setUsername(form.getUsername().trim());
        u.setFullName(form.getFullName() == null ? null : form.getFullName().trim());
        u.setEmail(form.getEmail().trim());
        u.setPassword(encoder.encode(form.getPassword()));
        u.setRole("USER");
        userRepository.save(u);
        ra.addFlashAttribute("message", "Đăng ký thành công, hãy đăng nhập!");
        return "redirect:/login";
    }

    private String validate(RegisterForm f) {
        if (f.getUsername() == null || f.getUsername().trim().length() < 3) return "Username tối thiểu 3 ký tự";
        if (f.getEmail() == null || !f.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) return "Email không hợp lệ";
        if (f.getPassword() == null || f.getPassword().length() < 6) return "Mật khẩu tối thiểu 6 ký tự";
        if (!f.getPassword().equals(f.getConfirmPassword())) return "Mật khẩu xác nhận không khớp";
        if (userRepository.existsByUsername(f.getUsername().trim())) return "Username đã tồn tại";
        if (userRepository.existsByEmail(f.getEmail().trim())) return "Email đã được sử dụng";
        return null;
    }
}
