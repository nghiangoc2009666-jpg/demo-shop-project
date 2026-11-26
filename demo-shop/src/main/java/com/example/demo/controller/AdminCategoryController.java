package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryRepository repo;

    public AdminCategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       Model model) {
        Page<Category> result = repo.findByNameContainingIgnoreCase(keyword.trim(),
                PageRequest.of(Math.max(page, 0), size, Sort.by("id").descending()));
        model.addAttribute("items", result.getContent());
        model.addAttribute("currentPage", result.getNumber());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalItems", result.getTotalElements());
        model.addAttribute("keyword", keyword);
        return "admin/category/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return repo.findById(id).map(c -> {
            model.addAttribute("category", c);
            return "admin/category/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "Không tìm thấy danh mục");
            return "redirect:/admin/categories";
        });
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("category") Category c, Model model, RedirectAttributes ra) {
        String name = c.getName() == null ? "" : c.getName().trim();
        if (name.isEmpty()) {
            model.addAttribute("error", "Tên danh mục không được để trống");
            return "admin/category/form";
        }
        if (name.length() > 100) {
            model.addAttribute("error", "Tên danh mục tối đa 100 ký tự");
            return "admin/category/form";
        }
        c.setName(name);
        repo.save(c);
        ra.addFlashAttribute("message", "Lưu danh mục thành công");
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            ra.addFlashAttribute("message", "Đã xóa danh mục");
        } else {
            ra.addFlashAttribute("error", "Không tìm thấy danh mục");
        }
        return "redirect:/admin/categories";
    }
}
