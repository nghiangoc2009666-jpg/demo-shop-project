package com.example.demo.config;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(UserRepository users, CategoryRepository categories, PasswordEncoder encoder) {
        return args -> {
            if (!users.existsByUsername("admin")) {
                User a = new User();
                a.setUsername("admin");
                a.setPassword(encoder.encode("admin123"));
                a.setFullName("Quản trị viên");
                a.setEmail("admin@example.com");
                a.setRole("ADMIN");
                users.save(a);
            }
            if (!users.existsByUsername("user")) {
                User u = new User();
                u.setUsername("user");
                u.setPassword(encoder.encode("user123"));
                u.setFullName("Người dùng mẫu");
                u.setEmail("user@example.com");
                u.setRole("USER");
                users.save(u);
            }
            if (categories.count() == 0) {
                for (int i = 1; i <= 12; i++) {
                    Category c = new Category();
                    c.setName("Danh mục " + i);
                    c.setDescription("Mô tả cho danh mục " + i);
                    categories.save(c);
                }
            }
        };
    }
}
