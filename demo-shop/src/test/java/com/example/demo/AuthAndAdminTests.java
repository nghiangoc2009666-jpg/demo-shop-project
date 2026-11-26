package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class AuthAndAdminTests {
    @Autowired WebApplicationContext ctx;
    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();
    }

    @Test
    void anonymousIsRedirectedToLogin() throws Exception {
        mvc.perform(get("/admin/categories")).andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void registerSuccessRedirectsToLogin() throws Exception {
        mvc.perform(post("/register").with(csrf())
                .param("username", "newuser1").param("email", "newuser1@test.com")
                .param("password", "123456").param("confirmPassword", "123456"))
           .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    void registerWrongConfirmStaysOnForm() throws Exception {
        mvc.perform(post("/register").with(csrf())
                .param("username", "newuser2").param("email", "newuser2@test.com")
                .param("password", "123456").param("confirmPassword", "999999"))
           .andExpect(status().isOk()).andExpect(view().name("register"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void normalUserCannotAccessAdmin() throws Exception {
        mvc.perform(get("/admin/users")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanListCategoriesWithSearchAndPaging() throws Exception {
        mvc.perform(get("/admin/categories").param("keyword", "Danh").param("page", "0"))
           .andExpect(status().isOk()).andExpect(view().name("admin/category/list"))
           .andExpect(model().attributeExists("items", "totalPages"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateCategory() throws Exception {
        mvc.perform(post("/admin/categories/save").with(csrf()).param("name", "Test CRUD"))
           .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/admin/categories"));
    }
}
