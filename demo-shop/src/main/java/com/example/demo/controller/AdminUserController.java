package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AdminUserController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       Model model) {
        Page<User> result = repo.search(keyword.trim(),
                PageRequest.of(Math.max(page, 0), size, Sort.by("id").descending()));
        model.addAttribute("items", result.getContent());
        model.addAttribute("currentPage", result.getNumber());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalItems", result.getTotalElements());
        model.addAttribute("keyword", keyword);
        return "admin/user/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isNew", true);
        return "admin/user/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return repo.findById(id).map(u -> {
            User copy = new User();
            copy.setId(u.getId());
            copy.setUsername(u.getUsername());
            copy.setFullName(u.getFullName());
            copy.setEmail(u.getEmail());
            copy.setRole(u.getRole());
            copy.setEnabled(u.isEnabled());
            copy.setPassword("");        // không đẩy hash ra form
            model.addAttribute("user", copy);
            model.addAttribute("isNew", false);
            return "admin/user/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "Không tìm thấy người dùng");
            return "redirect:/admin/users";
        });
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") User form, Model model, RedirectAttributes ra) {
        boolean isNew = form.getId() == null;
        model.addAttribute("isNew", isNew);

        String error = validate(form, isNew);
        if (error != null) {
            model.addAttribute("error", error);
            return "admin/user/form";
        }

        User target;
        if (isNew) {
            target = new User();
            target.setPassword(encoder.encode(form.getPassword()));
        } else {
            target = repo.findById(form.getId()).orElse(null);
            if (target == null) {
                ra.addFlashAttribute("error", "Không tìm thấy người dùng");
                return "redirect:/admin/users";
            }
            if (form.getPassword() != null && !form.getPassword().isBlank()) {
                target.setPassword(encoder.encode(form.getPassword()));
            }
        }
        target.setUsername(form.getUsername().trim());
        target.setFullName(form.getFullName());
        target.setEmail(form.getEmail().trim());
        target.setRole("ADMIN".equals(form.getRole()) ? "ADMIN" : "USER");
        target.setEnabled(form.isEnabled());
        repo.save(target);

        ra.addFlashAttribute("message", "Lưu người dùng thành công");
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal, RedirectAttributes ra) {
        User u = repo.findById(id).orElse(null);
        if (u == null) {
            ra.addFlashAttribute("error", "Không tìm thấy người dùng");
        } else if (u.getUsername().equals(principal.getName())) {
            ra.addFlashAttribute("error", "Không thể tự xóa tài khoản đang đăng nhập");
        } else {
            repo.delete(u);
            ra.addFlashAttribute("message", "Đã xóa người dùng");
        }
        return "redirect:/admin/users";
    }

    private String validate(User f, boolean isNew) {
        if (f.getUsername() == null || f.getUsername().trim().length() < 3) return "Username tối thiểu 3 ký tự";
        if (f.getEmail() == null || !f.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) return "Email không hợp lệ";
        if (isNew && (f.getPassword() == null || f.getPassword().length() < 6)) return "Mật khẩu tối thiểu 6 ký tự";
        if (!isNew && f.getPassword() != null && !f.getPassword().isBlank() && f.getPassword().length() < 6)
            return "Mật khẩu mới tối thiểu 6 ký tự";

        String username = f.getUsername().trim();
        String email = f.getEmail().trim();
        var byName = repo.findByUsername(username);
        if (byName.isPresent() && !byName.get().getId().equals(f.getId())) return "Username đã tồn tại";
        boolean emailTaken = repo.findAll().stream()
                .anyMatch(x -> x.getEmail().equalsIgnoreCase(email) && !x.getId().equals(f.getId()));
        if (emailTaken) return "Email đã được sử dụng";
        return null;
    }
}
